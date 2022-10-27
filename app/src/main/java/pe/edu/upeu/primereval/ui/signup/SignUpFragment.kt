package pe.edu.upeu.primereval.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import pe.edu.upeu.primereval.R
import pe.edu.upeu.primereval.databinding.FragmentSignUpBinding
import pe.edu.upeu.primereval.domain.model.User
import pe.edu.upeu.primereval.utils.Constants.EMAIL_ALREADY_EXISTS
import pe.edu.upeu.primereval.utils.DataState
import pe.edu.upeu.primereval.utils.isInputEmpty
import pe.edu.upeu.primereval.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding : FragmentSignUpBinding? = null
    private val binding get() =_binding!!

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }

    private fun initObservers(){

        viewModel.signUpState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<User> -> {
                    viewModel.saveUser(user = dataState.data)
                }
                is DataState.Error -> {
                    hideProgressDialog()
                    manageRegisterErrorMessages(dataState.exception)
                }
                is DataState.Loading ->{
                    showProgressBar()
                }
                else -> Unit
            }
        })
        viewModel.saveUserState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<Boolean> -> {
                    activity?.toast(getString(R.string.signup__signup_successfully))
                    activity?.onBackPressed()
                }
                is DataState.Error -> {
                    hideProgressDialog()
                    manageRegisterErrorMessages(dataState.exception)
                }
                is DataState.Loading ->{
                    showProgressBar()
                }
                else -> { true }
            }
        })

    }

    private fun initListeners(){
        binding.btnBack.setOnClickListener { activity?.onBackPressed() }
        binding.btnSignUp.setOnClickListener {
            if (isUserDataOk()){
                viewModel.signUp(createUser(), binding.etPassword.text.toString())
            }
        }
    }

    private fun createUser(): User {

        val email = binding.etEmail.text.toString()

        return User(
            email = email
        )
    }

    private fun isUserDataOk() : Boolean{
        return when {

            requireActivity().isInputEmpty(binding.etEmail, true) -> false

            isPasswordInsecure() -> {
                activity?.toast(getString(R.string.signup__error_passwords_match))
                false
            }

            else -> true

        }
    }

    private fun isPasswordInsecure(): Boolean{

        return if (binding.etPassword.text.toString().length <= 6){
            activity?.toast(getString(R.string.signup__error_password_insecure))
            true
        } else {
            binding.etPassword.text.toString() != binding.etConfirmPassword.text.toString()
        }
    }

    private fun manageRegisterErrorMessages(exception: Exception) {
        if (exception.toString() == EMAIL_ALREADY_EXISTS) {
            activity?.toast(getString(R.string.signup__error_email_already_registered))
        } else {
            activity?.toast(getString(R.string.signup__error_unknown_error))
        }
    }

    private fun hideProgressDialog() {
        binding.pbSignUp.visibility = View.GONE
        binding.btnSignUp.text = getString(R.string.signup__signup_button)
        binding.btnSignUp.isEnabled = true
    }

    private fun showProgressBar() {
        binding.btnSignUp.text = ""
        binding.btnSignUp.isEnabled = false
        binding.pbSignUp.visibility = View.VISIBLE
    }

}