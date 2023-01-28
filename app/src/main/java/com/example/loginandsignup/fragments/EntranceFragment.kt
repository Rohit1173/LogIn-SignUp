package com.example.loginandsignup.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.loginandsignup.R
import com.example.loginandsignup.databinding.FragmentEntranceBinding


class EntranceFragment : Fragment() {
    private var _binding: FragmentEntranceBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEntranceBinding.inflate(
            inflater, container, false
        )
        val sharedPreference =  requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE)
        binding.logOutBtn.setOnClickListener {
            val editor = sharedPreference.edit()
            editor.clear()
            editor.apply()
            Toast.makeText(requireContext(),"Logged Out",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_entranceFragment_to_loginFragment)
        }
        return binding.root
    }


}