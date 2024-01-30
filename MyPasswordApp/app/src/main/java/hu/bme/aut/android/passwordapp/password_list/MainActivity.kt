package hu.bme.aut.android.passwordapp.password_list


import android.content.Intent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import hu.bme.aut.android.passwordapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btMyPasswords.setOnClickListener{
            val intent = Intent(this, MyPasswordsActivity::class.java)
            startActivity(intent)
        }
        binding.btGenerator.setOnClickListener{
            val intent = Intent(this, GeneratorActivity::class.java)
            startActivity(intent)
        }


    }







}
