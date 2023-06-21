package com.example.kimkazandiapp.ui.Tatilkazan


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kimkazandiapp.adapter.CekilislerAdapter
import com.example.kimkazandiapp.databinding.FragmentTatilkazanBinding
import com.example.kimkazandiapp.services.Jsoupservice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TatilkazanFragment : Fragment() {
    private var _binding: FragmentTatilkazanBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var jsoupservice: Jsoupservice

    private lateinit var cekilislerAdapter: CekilislerAdapter

    private val viewModel: TatilkazanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTatilkazanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeData()

        CoroutineScope(Dispatchers.Main).launch {
            jsoupservice.cekilisturler()
        }

    }

    private fun setupRecyclerView() {
        cekilislerAdapter = CekilislerAdapter()
        binding.tatilkazanRecyclerview.apply {
            adapter = cekilislerAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeData() {
        viewModel.getFilteredData("tatil-kazan").observe(viewLifecycleOwner) { dataList ->
            cekilislerAdapter.submitList(dataList)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}