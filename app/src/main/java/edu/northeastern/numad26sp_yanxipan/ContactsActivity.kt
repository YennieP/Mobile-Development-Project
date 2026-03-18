package edu.northeastern.numad26sp_yanxipan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

// Data class for a contact
data class Contact(val id: Int, var name: String, var phone: String)

class ContactsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactsAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var tvEmpty: TextView

    private val contacts = mutableListOf<Contact>()
    private var nextId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        fab = findViewById(R.id.fab)
        tvEmpty = findViewById(R.id.tvEmpty)

        // Set up RecyclerView
        adapter = ContactsAdapter(
            contacts,
            onContactClick = { contact -> dialPhone(contact.phone) },
            onEditClick = { contact -> showEditDialog(contact) },
            onDeleteClick = { contact -> deleteContact(contact) }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // FAB click - show add contact dialog
        fab.setOnClickListener { showAddDialog() }

        updateEmptyView()
    }

    // Show empty message when no contacts
    private fun updateEmptyView() {
        if (contacts.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    // Dial phone using intent
    private fun dialPhone(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        startActivity(intent)
    }

    // Show dialog to add a new contact
    private fun showAddDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_contact, null)
        val etName = dialogView.findViewById<EditText>(R.id.etName)
        val etPhone = dialogView.findViewById<EditText>(R.id.etPhone)

        AlertDialog.Builder(this)
            .setTitle("Add Contact")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = etName.text.toString().trim()
                val phone = etPhone.text.toString().trim()

                if (name.isNotEmpty() && phone.isNotEmpty()) {
                    val newContact = Contact(nextId++, name, phone)
                    contacts.add(newContact)
                    adapter.notifyItemInserted(contacts.size - 1)
                    updateEmptyView()

                    // Show success Snackbar with Undo action
                    Snackbar.make(fab, "Contact \"$name\" added", Snackbar.LENGTH_LONG)
                        .setAction("Undo") {
                            contacts.remove(newContact)
                            adapter.notifyDataSetChanged()
                            updateEmptyView()
                        }
                        .show()
                } else {
                    // Show failure Snackbar
                    Snackbar.make(fab, "Failed: Name and phone cannot be empty", Snackbar.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Show dialog to edit an existing contact
    private fun showEditDialog(contact: Contact) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_contact, null)
        val etName = dialogView.findViewById<EditText>(R.id.etName)
        val etPhone = dialogView.findViewById<EditText>(R.id.etPhone)

        // Pre-fill existing values
        etName.setText(contact.name)
        etPhone.setText(contact.phone)

        AlertDialog.Builder(this)
            .setTitle("Edit Contact")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val name = etName.text.toString().trim()
                val phone = etPhone.text.toString().trim()

                if (name.isNotEmpty() && phone.isNotEmpty()) {
                    contact.name = name
                    contact.phone = phone
                    adapter.notifyDataSetChanged()
                    Snackbar.make(fab, "Contact updated", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(fab, "Failed: Name and phone cannot be empty", Snackbar.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Delete a contact with confirmation
    private fun deleteContact(contact: Contact) {
        val idx = contacts.indexOf(contact)
        AlertDialog.Builder(this)
            .setTitle("Delete Contact")
            .setMessage("Delete \"${contact.name}\"?")
            .setPositiveButton("Delete") { _, _ ->
                contacts.remove(contact)
                adapter.notifyItemRemoved(idx)
                updateEmptyView()
                Snackbar.make(fab, "Contact deleted", Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}

// RecyclerView Adapter
class ContactsAdapter(
    private val contacts: MutableList<Contact>,
    private val onContactClick: (Contact) -> Unit,
    private val onEditClick: (Contact) -> Unit,
    private val onDeleteClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvContactName)
        val tvPhone: TextView = view.findViewById(R.id.tvContactPhone)
        val btnEdit: TextView = view.findViewById(R.id.btnEdit)
        val btnDelete: TextView = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]
        holder.tvName.text = contact.name
        holder.tvPhone.text = contact.phone
        holder.itemView.setOnClickListener { onContactClick(contact) }
        holder.btnEdit.setOnClickListener { onEditClick(contact) }
        holder.btnDelete.setOnClickListener { onDeleteClick(contact) }
    }

    override fun getItemCount() = contacts.size
}