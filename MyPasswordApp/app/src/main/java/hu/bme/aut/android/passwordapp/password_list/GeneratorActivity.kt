package hu.bme.aut.android.passwordapp.password_list

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import hu.bme.aut.android.passwordapp.R
import hu.bme.aut.android.passwordapp.databinding.ActivityGeneratorBinding
import hu.bme.aut.android.passwordapp.databinding.ActivityMainBinding
import hu.bme.aut.android.passwordapp.generator.PasswordGenerator

class GeneratorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGeneratorBinding
    private var pwLength: Int = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneratorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.etGeneratedPassword.setOnKeyListener{v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                binding.etGeneratedPassword.onEditorAction(EditorInfo.IME_ACTION_DONE)
                return@setOnKeyListener true
            }
            false
        }

        binding.btnCopyToClipboard.setOnClickListener {
            val clipboardManager = ContextCompat.getSystemService(
                binding.etGeneratedPassword.context,
                ClipboardManager::class.java
            ) as ClipboardManager

            val text = binding.etGeneratedPassword.text.toString()
            val clipData = ClipData.newPlainText("Copied Text", text)
            clipboardManager.setPrimaryClip(clipData)

            Toast.makeText(binding.etGeneratedPassword.context,"Password copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        binding.seekBarLength.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                pwLength = progress
                binding.tvPasswordLength.text = "Password Length: $progress"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        binding.btnGeneratePassword.setOnClickListener{
            if( isValidToGenerate() ){
                binding.etGeneratedPassword.setText(
                    PasswordGenerator().generatePassword(pwLength,
                    booleanArrayOf(binding.chkLowercase.isChecked,
                        binding.chkUppercase.isChecked,
                        binding.chkNumbers.isChecked,
                        binding.chkSpecialChars.isChecked)))
            }
            else
                Toast.makeText(this, "Invalid Parameters", Toast.LENGTH_LONG).show()
        }

    }

    private fun isValidToGenerate() =
                (binding.chkLowercase.isChecked ||
                        binding.chkUppercase.isChecked ||
                        binding.chkNumbers.isChecked ||
                        binding.chkSpecialChars.isChecked) &&
                        pwLength in (6..48)

}
