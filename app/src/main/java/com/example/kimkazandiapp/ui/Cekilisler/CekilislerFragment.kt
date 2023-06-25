package com.example.kimkazandiapp.ui.Cekilisler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kimkazandiapp.adapter.CekilislerAdapter
import com.example.kimkazandiapp.data.entity.Data
import com.example.kimkazandiapp.databinding.FragmentCekilislerBinding
import com.example.kimkazandiapp.services.Jsoupservice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class CekilislerFragment : Fragment(), CekilislerAdapter.OnItemClickListener {

    private var _binding: FragmentCekilislerBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var jsoupservice: Jsoupservice

    private lateinit var cekilislerAdapter: CekilislerAdapter
    private val cekilislerViewModel: CekilislerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCekilislerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        CoroutineScope(Dispatchers.IO).launch {
            jsoupservice.updateDataIfNecessary()
            withContext(Dispatchers.Main) {
                observeData()
                setupRecyclerView()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        cekilislerAdapter = CekilislerAdapter(this)
        binding.cekilisRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.cekilisRecyclerview.adapter = cekilislerAdapter
        binding.cekilisRecyclerview.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
    }

    private fun observeData() {
        cekilislerViewModel.getFilteredData("cekilisler/ilk8").observe(viewLifecycleOwner) { dataList ->
            cekilislerAdapter.submitList(dataList)
        }
    }

    override fun onItemClick(data: Data) {
        val action = CekilislerFragmentDirections.actionNavCekilislerToDetailFragment(data.id!!)
        findNavController().navigate(action)
    }
}