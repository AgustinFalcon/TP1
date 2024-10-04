package com.example.trabajopractico1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.trabajopractico1.R
import com.example.trabajopractico1.databinding.FragmentThirdBinding


class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentThirdBinding
    private val viewModel: ThirdViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(inflater, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val thirdViewModel = ViewModelProvider(this).get(ThirdViewModel::class.java)

        binding.etEmail.addTextChangedListener { email ->
            viewModel.validateEmail(email = email.toString())
        }

        binding.etPassword.addTextChangedListener { password ->
            viewModel.validatePassword(password = password.toString())
        }

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            when(state) {
                is ThirdStates.SuccessEmail -> {
                    binding.layoutEmail.error = null
                }

                is ThirdStates.ErrorEmail -> {
                    binding.layoutEmail.error = "Formato email invalido"
                }

                is ThirdStates.SuccessPassword -> {
                    binding.layoutPassword.error = null
                }

                is ThirdStates.ErrorPassword -> {
                    binding.layoutPassword.error = "Minimo:${state.password.length}/4"
                }

                is ThirdStates.SuccessButton -> {
                    binding.btnNext.isEnabled = true
                }

                is ThirdStates.ErrorButton -> {
                    binding.btnNext.isEnabled = false
                }
            }
        })

        binding.btnNext.setOnClickListener {
            Toast.makeText(requireContext(), "Formulario correcto", Toast.LENGTH_SHORT).show()
        }
    }
}