package com.demoss.idp.presentation.main.dialog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.demoss.idp.R
import com.demoss.idp.util.EmptyConstants
import kotlinx.android.synthetic.main.dialog_password_verification.*

class PasswordVerificationFragment : DialogFragment() {

    companion object {
        const val TAG = "PasswordVerificationDialogFragment"
        fun newInstance() = PasswordVerificationFragment()
    }

    private var onSuccessfulVerificationAction: (() -> Unit)? = null
    private var onVerificationErrorAction: (() -> Unit)? = null
    private var password = EmptyConstants.EMPTY_STRING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_password_verification, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnVerify.setOnClickListener {
            verifyPassword()
        }
        etPassword.showSoftInputOnFocus = true
        etPassword.requestFocus()
        etPassword.setOnEditorActionListener { textView, i, keyEvent ->
            verifyPassword()
            true
        }
    }

    fun setupFragment(onSuccess: () -> Unit, onError: () -> Unit, password: String): PasswordVerificationFragment = this.apply {
        onSuccessfulVerificationAction = onSuccess
        onVerificationErrorAction = onError
        this.password = password
    }

    private fun verifyPassword() {
        if (password == etPassword.text.toString()) {
            onSuccessfulVerificationAction?.invoke()
            dismiss()
        } else onVerificationErrorAction?.invoke()
    }
}