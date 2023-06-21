package com.example.kimkazandiapp.ui.Cekilisler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kimkazandiapp.adapter.CekilislerAdapter
import com.example.kimkazandiapp.databinding.FragmentCekilislerBinding
import com.example.kimkazandiapp.services.Jsoupservice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CekilislerFragment : Fragment() {
    private var _binding: FragmentCekilislerBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var jsoupservice: Jsoupservice

    private lateinit var cekilislerAdapter: CekilislerAdapter
    private lateinit var cekilislerViewModel: CekilislerViewModel

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
        cekilislerViewModel = ViewModelProvider(this).get(CekilislerViewModel::class.java)

        setupRecyclerView()
        observeData()

        CoroutineScope(Dispatchers.Main).launch {
            jsoupservice.cekilisler()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        cekilislerAdapter = CekilislerAdapter()
        binding.cekilisRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.cekilisRecyclerview.adapter = cekilislerAdapter
        binding.cekilisRecyclerview.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
    }

    private fun observeData() {
        cekilislerViewModel.getFilteredData("cekilisler/ilk8").observe(viewLifecycleOwner) { dataList ->
            cekilislerAdapter.submitList(dataList)
        }
    }
}