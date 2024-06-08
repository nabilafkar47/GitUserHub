package com.nabil.githubusers.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nabil.githubusers.data.model.User
import com.nabil.githubusers.databinding.FragmentFollowBinding
import com.nabil.githubusers.ui.adapter.FollowAdapter
import com.nabil.githubusers.ui.viewmodel.FollowViewModel

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var followViewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = requireArguments().getInt(ARG_POSITION)
        val username = requireArguments().getString(ARG_USERNAME)

        followViewModel = ViewModelProvider(this)[FollowViewModel::class.java]

        followViewModel.isLoading.observe(requireActivity()) { isLoading ->
            showLoading(isLoading)
        }

        if (position == 1) {
            followViewModel.getUserFollowers(username!!)
            followViewModel.followers.observe(viewLifecycleOwner) { followers ->
                setFollowers(followers)
            }
        } else {
            followViewModel.getUserFollowing(username!!)
            followViewModel.following.observe(viewLifecycleOwner) { following ->
                setFollowing(following)
            }
        }
    }

    private fun setFollowers(followers: List<User>) {
        val adapter = FollowAdapter(followers)
        binding.rvUserFollow.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUserFollow.adapter = adapter
    }

    private fun setFollowing(following: List<User>) {
        val adapter = FollowAdapter(following)
        binding.rvUserFollow.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUserFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFollow.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}
