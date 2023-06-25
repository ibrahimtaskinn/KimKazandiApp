package com.example.kimkazandiapp.ui.Telefontabletkazan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kimkazandiapp.adapter.CekilislerAdapter
import com.example.kimkazandiapp.data.entity.Data
import com.example.kimkazandiapp.databinding.FragmentTelefontabletkazanBinding
import com.example.kimkazandiapp.services.Jsoupservice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TelefontabletkazanFragment : Fragment(), CekilislerAdapter.OnItemClickListener {
    private var _binding: FragmentTelefontabletkazanBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var jsoupservice: Jsoupservice

    private lateinit var cekilislerAdapter: CekilislerAdapter

    private val viewModel: TelefontabletkazanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTelefontabletkazanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeData()

        CoroutineScope(Dispatchers.IO).launch {
            jsoupservice.updateDataIfNecessary()
        }

    }

    private fun setupRecyclerView() {
        cekilislerAdapter = CekilislerAdapter(this)
        binding.telefontabletkazanRecyclerview.apply {
            adapter = cekilislerAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeData() {
        viewModel.getFilteredData("telefon-tablet-kazan").observe(viewLifecycleOwner) { dataList ->
            cekilislerAdapter.submitList(dataList)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(data: Data) {
        val action = TelefontabletkazanFragmentDirections.actionNavTelefontabletkazanToDetailFragment(data.id!!)
        findNavController().navigate(action)
    }
}