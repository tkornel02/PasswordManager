package hu.bme.aut.android.passwordapp.password_list.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.passwordapp.R
import hu.bme.aut.android.passwordapp.password_list.data.PasswordItem
import hu.bme.aut.android.passwordapp.databinding.ItemPasswordsBinding


class PasswordAdapter(private val listener: PasswordItemClickListener) :
    RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder>() {

    private val items = mutableListOf<PasswordItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PasswordViewHolder(
        ItemPasswordsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        val passwordItem = items[position]
        holder.binding.tvPassword.text = passwordItem.password
        holder.binding.tvDescription.text = passwordItem.description

        holder.binding.ibCopy.setOnClickListener {
            val clipboardManager = getSystemService(
                holder.binding.tvPassword.context,
                ClipboardManager::class.java
            ) as ClipboardManager

            // Get the text from the TextView
            val text = holder.binding.tvPassword.text.toString()

            // Create a ClipData object
            val clipData = ClipData.newPlainText("Copied Text", text)

            // Set the data to the clipboard
            clipboardManager.setPrimaryClip(clipData)

            // Display a Toast or perform any action to indicate that the text has been copied
            Toast.makeText(holder.binding.tvPassword.context,"Password copied to clipboard", Toast.LENGTH_SHORT).show()
        }
        holder.binding.ibDelete.setOnClickListener {
            listener.onItemDeleted(passwordItem)
        }
        var isPasswordVisible = false

        holder.binding.ibVisible.setOnClickListener {
            // Toggle password visibility
            isPasswordVisible = !isPasswordVisible

            val iconResId = if (isPasswordVisible) R.drawable.ic_not_visible else R.drawable.ic_visible
            holder.binding.ibVisible.setImageResource(iconResId)
            val transformationMethod =
                if (isPasswordVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()

            holder.binding.tvPassword.transformationMethod = transformationMethod
        }



    }

    override fun getItemCount(): Int = items.size

    interface PasswordItemClickListener {
        fun onItemDeleted(item: PasswordItem)

    }
    fun addItem(item: PasswordItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(passwordItems: List<PasswordItem>) {
        items.clear()
        items.addAll(passwordItems)
        notifyDataSetChanged()
    }
    fun deleteItem(itemToBeDeleted: PasswordItem){
        val idx = items.indexOf(itemToBeDeleted)
        items.removeAt(idx)
        notifyItemRemoved(idx)
    }
    inner class PasswordViewHolder(val binding: ItemPasswordsBinding) : RecyclerView.ViewHolder(binding.root)
}