package com.example.kimkazandiapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.kimkazandiapp.R
import com.example.kimkazandiapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        val dataId = args.dataId
        viewModel.getData(dataId)

        viewModel.data.observe(viewLifecycleOwner, Observer { data ->
            binding.textTitle.text = data.title
            binding.textDetail.text = data.detailData?.detail ?: "No Detail Available"
            binding.basTarih.text = data.detailData?.basTarih ?: "No Data"
            binding.sonTarih.text = data.detailData?.sonTarih ?: "No Data"
            binding.cekTarih.text = data.detailData?.cekTarih ?: "No Data"
            binding.ilnTarih.text = data.detailData?.ilnTarih ?: "No Data"
            binding.minharcama.text = data.detailData?.minharcama ?: "No Data"
            binding.hediyeDeger.text = data.detailData?.hediyeDeger ?: "No Data"
            binding.hediyeSayi.text = data.detailData?.hediyeSayi ?: "No Data"

            Glide.with(requireContext())
                .load(data.imgUrl)
                .into(binding.imageDetail)


            var isFollowing = data.isFollowing
            updateFollowButtonState(isFollowing)


            binding.detailbutton.setOnClickListener {
                if (isFollowing) {
                    isFollowing = false
                    viewModel.unfollowData(data)
                } else {
                    isFollowing = true
                    viewModel.followData(data)
                }
                updateFollowButtonState(isFollowing)
            }
        })
    }

    private fun updateFollowButtonState(isFollowing: Boolean) {
        if (isFollowing) {
            binding.detailbutton.text = "Takipten Çıkar"
            binding.detailbutton.setBackgroundColor(resources.getColor(R.color.red))
        } else {
            binding.detailbutton.text = "Takip Et"
            binding.detailbutton.setBackgroundColor(resources.getColor(R.color.green))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}