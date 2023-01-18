package com.example.loginandsignup

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.loginandsignup.data.signupData
import com.example.loginandsignup.databinding.FragmentSignUpBinding
import com.example.loginandsignup.viewModel.signupViewModel
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var vm: signupViewModel
    private lateinit var signMsg:String
    private lateinit var signErrorMsg:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(
            inflater, container, false
        )
        vm = ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
            .create(signupViewModel::class.java)
        binding.setEmail.doOnTextChanged { text, _, _, _ ->
            if (!email_check(text.toString())) {
                binding.layoutSetEmail.error = "Invalid E-Mail Format"
            } else {
                binding.layoutSetEmail.error = null
            }
        }
        binding.setName.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                binding.layoutSetName.error = null
            }
        }
        binding.setUsername.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                binding.layoutSetUsername.error = null
            }
        }
        binding.setPassword.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                binding.layoutSetPassword.error = null
            }
        }
        binding.setConfirmPassword.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                binding.layoutSetConfirmPassword.error = null
            }
        }
        binding.changeToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        binding.SignUpBtn.setOnClickListener {
            if (binding.setName.text.toString().trim().isEmpty()) {
                binding.layoutSetName.error = "Name cannot be empty"
            }
            if (binding.setUsername.text.toString().trim().isEmpty()) {
                binding.layoutSetUsername.error = "UserName cannot be empty"
            }
            if (binding.setEmail.text.toString().trim().isEmpty()) {
                binding.layoutSetEmail.error = "E-Mail cannot be empty"
            }
            if (binding.setPassword.text.toString().trim().isEmpty()) {
                binding.layoutSetPassword.error = "Password cannot be empty"
            }
            if (binding.setConfirmPassword.text.toString().trim().isEmpty()) {
                binding.layoutSetConfirmPassword.error = "Confirm Password cannot be empty"
            }
            if (binding.setConfirmPassword.text.toString()
                    .trim() != binding.setPassword.text.toString()
                    .trim()
            ) {
                binding.layoutSetConfirmPassword.error =
                    "Confirm Password is not the same as the Password"
            }

            if (checks()) {
                val signupData = signupData(
                    binding.setUsername.text.toString().trim(),
                    binding.setEmail.text.toString().trim(),
                    binding.setPassword.text.toString().trim()
                )
                vm.addUser(signupData)
            } else {

                Toast.makeText(
                    activity,
                    "Fill all the details",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        vm.signResponse.observe(
            viewLifecycleOwner
        ) {

            if (it.isSuccessful) {
                try {
                    val jsonObject = JSONObject(Gson().toJson(it.body()))
                    signMsg = jsonObject.getString("message")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                Toast.makeText(context, signMsg, Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_signUpFragment_to_entranceFragment)
            } else {
                try {
                    val jObjError = JSONObject(it.errorBody()!!.string())
                    signErrorMsg = jObjError.getString("message")
                    if (signErrorMsg.contains("UserName")) {
                        binding.layoutSetUsername.error = signErrorMsg
                    }
                    if (signErrorMsg.contains("E-mail")) {
                        binding.layoutSetEmail.error = signErrorMsg
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        return binding.root
    }

    private fun checks(): Boolean {
        if (binding.setName.text.toString().trim().isNotEmpty() &&
            binding.setUsername.text.toString().trim().isNotEmpty() &&
            binding.setEmail.text.toString().trim().isNotEmpty() &&
            binding.setPassword.text.toString().trim().isNotEmpty() &&
            binding.setConfirmPassword.text.toString().trim().isNotEmpty() &&
            email_check(binding.setEmail.text.toString().trim()) &&
            binding.setPassword.text.toString()
                .trim() == binding.setConfirmPassword.text.toString().trim()
        ) {
            return true
        }
        return false
    }

    private fun email_check(text: String): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            return true
        }
        return false
    }

}