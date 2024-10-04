package com.example.trabajopractico1.fragments

import androidx.core.util.PatternsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThirdViewModel : ViewModel() {

    //private var _viewState = MutableLiveData<ThirdEnums>()
    //val viewState: MutableLiveData<ThirdEnums> get() = _viewState
    private var email: String = ""
    private var password: String = ""

    var viewState = MutableLiveData<ThirdStates>()


    fun validateEmail(email: String) {
        this.email = email
        if (email.isNotBlank() && PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            viewState.value = ThirdStates.SuccessEmail

        } else {
            viewState.value = ThirdStates.ErrorEmail
        }

        validateButtons()
    }

    fun validatePassword(password: String) {
        this.password = password
        if (password.isNotBlank() && password.length > 3) {
            viewState.value = ThirdStates.SuccessPassword

        } else {
            viewState.value = ThirdStates.ErrorPassword(password = password)
        }

        validateButtons()
    }


    private fun validateButtons() {
        if ((email.isNotBlank() && PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) &&
            password.isNotBlank() && password.length > 3) {

            viewState.value = ThirdStates.SuccessButton

        } else {
            viewState.value = ThirdStates.ErrorButton
        }
    }
}

enum class ThirdEnums {
    SUCCESS_EMAIL, ERROR_EMAIL, SUCCESS_PASSWORD, ERROR_PASSWORD
}


sealed class ThirdStates() {
    object SuccessEmail: ThirdStates()
    object ErrorEmail: ThirdStates()
    object SuccessPassword: ThirdStates()
    data class ErrorPassword(val password: String): ThirdStates()
    object SuccessButton: ThirdStates()
    object ErrorButton: ThirdStates()
}
