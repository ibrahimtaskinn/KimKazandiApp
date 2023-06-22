package com.example.kimkazandiapp.ui.Bedavakatilim


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kimkazandiapp.adapter.CekilislerAdapter
import com.example.kimkazandiapp.data.entity.Data
import com.example.kimkazandiapp.databinding.FragmentBedavakatilimBinding
import com.example.kimkazandiapp.services.Jsoupservice
import com.example.kimkazandiapp.ui.Arabakazan.ArabakazanFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class BedavakatilimFragment : Fragment(), CekilislerAdapter.OnItemClickListener  {
    private var _binding: FragmentBedavakatilimBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var jsoupservice: Jsoupservice

    private lateinit var cekilislerAdapter: CekilislerAdapter

    private val viewModel: BedavakatilimViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBedavakatilimBinding.inflate(inflater, container, false)
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
        cekilislerAdapter = CekilislerAdapter(this)
        binding.bedavakatilimRecyclerview.apply {
            adapter = cekilislerAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeData() {
        viewModel.getFilteredData("bedava-katilim").observe(viewLifecycleOwner) { dataList ->
            cekilislerAdapter.submitList(dataList)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(data: Data) {
        val action = BedavakatilimFragmentDirections.actionNavBedavakatilimToDetailFragment(data.id!!)
        findNavController().navigate(action)
    }
}