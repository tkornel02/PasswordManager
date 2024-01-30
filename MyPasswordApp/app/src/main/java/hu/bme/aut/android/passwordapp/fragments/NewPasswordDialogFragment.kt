package hu.bme.aut.android.passwordapp.fragments

import hu.bme.aut.android.passwordapp.databinding.DialogNewPasswordBinding
import hu.bme.aut.android.passwordapp.password_list.data.PasswordItem
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.passwordapp.generator.PasswordGenerator


class NewPasswordDialogFragment : DialogFragment() {
    interface NewPasswordDialogListener {
        fun onPasswordItemCreated(newItem: PasswordItem)
    }

    private lateinit var listener: NewPasswordDialogListener

    private lateinit var binding: DialogNewPasswordBinding



    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewPasswordDialogListener
            ?: throw RuntimeException("Activity must implement the NewPasswordDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewPasswordBinding.inflate(LayoutInflater.from(context))

        binding.etPassword.setOnKeyListener{v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                binding.etPassword.onEditorAction(EditorInfo.IME_ACTION_DONE)
                return@setOnKeyListener true
            }
            false
        }
        binding.etDescription.setOnKeyListener{v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                binding.etDescription.onEditorAction(EditorInfo.IME_ACTION_DONE)
                return@setOnKeyListener true
            }
            false
        }
        binding.btNewPw.setOnClickListener{
            if( isValidToGenerate() ){
                binding.etPassword.setText(PasswordGenerator().generatePassword(binding.etLength.text.toString().toInt(),
                    booleanArrayOf(binding.cbLowers.isChecked,
                        binding.cbUppers.isChecked,
                        binding.cbNumbers.isChecked,
                        binding.cbSpecials.isChecked)))
            }
            else
                Toast.makeText(context, "Invalid Parameters", Toast.LENGTH_LONG).show()
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(hu.bme.aut.android.passwordapp.R.string.new_password)
            .setView(binding.root)
            .setPositiveButton(hu.bme.aut.android.passwordapp.R.string.button_ok) { _, _ ->
                if (isValidToSave()) {
                    listener.onPasswordItemCreated(getPasswordItem())
                }

            }
            .setNegativeButton(hu.bme.aut.android.passwordapp.R.string.button_cancel, null)
            .create()

    }
    private fun isValidToSave() = binding.etPassword.text.toString().isNotEmpty() && binding.etDescription.text.toString().isNotEmpty()

    private fun isValidToGenerate() =
                (binding.etLength.text.toString().isNotEmpty() && isValidLength(binding.etLength.text.toString())) &&
                (binding.cbLowers.isChecked ||
                        binding.cbUppers.isChecked ||
                        binding.cbNumbers.isChecked ||
                        binding.cbSpecials.isChecked)


    private fun isValidLength(s : String): Boolean{
        return s.toInt() in 4..48
    }
    private fun getPasswordItem() = PasswordItem(
        password = binding.etPassword.text.toString(),
        description = binding.etDescription.text.toString()

    )
    companion object {
        const val TAG = "NewPasswordDialogFragment"
    }
}