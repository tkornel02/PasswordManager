package hu.bme.aut.android.passwordapp.password_list

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.passwordapp.databinding.ActivityMyPasswordsBinding
import hu.bme.aut.android.passwordapp.fragments.NewPasswordDialogFragment
import hu.bme.aut.android.passwordapp.password_list.PasswordDatabase
import hu.bme.aut.android.passwordapp.password_list.adapter.PasswordAdapter
import hu.bme.aut.android.passwordapp.password_list.data.PasswordItem
import kotlin.concurrent.thread

class MyPasswordsActivity : AppCompatActivity(), PasswordAdapter.PasswordItemClickListener,
    NewPasswordDialogFragment.NewPasswordDialogListener {

    private lateinit var binding: ActivityMyPasswordsBinding


    private lateinit var passwordDatabase: PasswordDatabase
    private lateinit var passwordAdapter: PasswordAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPasswordsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        passwordDatabase = PasswordDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener{
            NewPasswordDialogFragment().show(
                supportFragmentManager,
                NewPasswordDialogFragment.TAG
            )
        }
        initRecyclerView()

    }

    private fun initRecyclerView() {
        passwordAdapter = PasswordAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = passwordAdapter

        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = passwordDatabase.passwordDao().getAll()
            runOnUiThread {
                passwordAdapter.update(items)
            }
        }
    }

    override fun onItemDeleted(item: PasswordItem) {
        thread {
            passwordDatabase.passwordDao().deleteItem(item)
            Log.d("MyPasswordActivity", "Successful deletion of PasswordItem")
            runOnUiThread {
                passwordAdapter.deleteItem(item)
            }
        }
    }

    override fun onPasswordItemCreated(newItem: PasswordItem) {
        thread {
            val insertId = passwordDatabase.passwordDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                passwordAdapter.addItem(newItem)
            }
        }
    }



}