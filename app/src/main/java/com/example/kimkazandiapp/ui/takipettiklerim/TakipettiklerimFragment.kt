package com.example.kimkazandiapp.ui.takipettiklerim

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
import com.example.kimkazandiapp.databinding.FragmentTakipettiklerimBinding
import com.example.kimkazandiapp.ui.Cekilisler.CekilislerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TakipettiklerimFragment : Fragment(), CekilislerAdapter.OnItemClickListener {

    private val viewModel: TakipettiklerimViewModel by viewModels()
    private var _binding: FragmentTakipettiklerimBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTakipettiklerimBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CekilislerAdapter(this)
        binding.takipettiklerimRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.takipettiklerimRecyclerview.adapter = adapter

        viewModel.followedData.observe(viewLifecycleOwner, { data ->
            adapter.submitList(data)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(data: Data) {
        val action = TakipettiklerimFragmentDirections.actionNavTakipettiklerimToDetailFragment(data.id!!)
        findNavController().navigate(action)
    }
}