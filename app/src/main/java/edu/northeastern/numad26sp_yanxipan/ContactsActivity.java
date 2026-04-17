package edu.northeastern.numad26sp_yanxipan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    // Simple Contact data class
    static class Contact {
        int id;
        String name, phone;
        Contact(int id, String name, String phone) {
            this.id = id; this.name = name; this.phone = phone;
        }
    }

    private RecyclerView recyclerView;
    private ContactsAdapter adapter;
    private FloatingActionButton fab;
    private TextView tvEmpty;
    private final List<Contact> contacts = new ArrayList<>();
    private int nextId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            var systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        fab          = findViewById(R.id.fab);
        tvEmpty      = findViewById(R.id.tvEmpty);

        adapter = new ContactsAdapter(contacts,
                contact -> dialPhone(contact.phone),
                contact -> showEditDialog(contact),
                contact -> deleteContact(contact));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> showAddDialog());
        updateEmptyView();
    }

    private void updateEmptyView() {
        tvEmpty.setVisibility(contacts.isEmpty() ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(contacts.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private void dialPhone(String phone) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)));
    }

    private void showAddDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_contact, null);
        EditText etName  = dialogView.findViewById(R.id.etName);
        EditText etPhone = dialogView.findViewById(R.id.etPhone);

        new AlertDialog.Builder(this)
                .setTitle("Add Contact")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String name  = etName.getText().toString().trim();
                    String phone = etPhone.getText().toString().trim();
                    if (!name.isEmpty() && !phone.isEmpty()) {
                        Contact c = new Contact(nextId++, name, phone);
                        contacts.add(c);
                        adapter.notifyItemInserted(contacts.size() - 1);
                        updateEmptyView();
                        Snackbar.make(fab, "Contact \"" + name + "\" added", Snackbar.LENGTH_LONG)
                                .setAction("Undo", v -> {
                                    contacts.remove(c);
                                    adapter.notifyDataSetChanged();
                                    updateEmptyView();
                                }).show();
                    } else {
                        Snackbar.make(fab, "Failed: Name and phone cannot be empty", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showEditDialog(Contact contact) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_contact, null);
        EditText etName  = dialogView.findViewById(R.id.etName);
        EditText etPhone = dialogView.findViewById(R.id.etPhone);
        etName.setText(contact.name);
        etPhone.setText(contact.phone);

        new AlertDialog.Builder(this)
                .setTitle("Edit Contact")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String name  = etName.getText().toString().trim();
                    String phone = etPhone.getText().toString().trim();
                    if (!name.isEmpty() && !phone.isEmpty()) {
                        contact.name  = name;
                        contact.phone = phone;
                        adapter.notifyDataSetChanged();
                        Snackbar.make(fab, "Contact updated", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(fab, "Failed: Name and phone cannot be empty", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteContact(Contact contact) {
        int idx = contacts.indexOf(contact);
        new AlertDialog.Builder(this)
                .setTitle("Delete Contact")
                .setMessage("Delete \"" + contact.name + "\"?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    contacts.remove(contact);
                    adapter.notifyItemRemoved(idx);
                    updateEmptyView();
                    Snackbar.make(fab, "Contact deleted", Snackbar.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // ── Adapter ───────────────────────────────────────────────────────────────

    interface ContactAction { void run(Contact c); }

    static class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

        private final List<Contact> contacts;
        private final ContactAction onClick, onEdit, onDelete;

        ContactsAdapter(List<Contact> contacts,
                        ContactAction onClick,
                        ContactAction onEdit,
                        ContactAction onDelete) {
            this.contacts = contacts;
            this.onClick  = onClick;
            this.onEdit   = onEdit;
            this.onDelete = onDelete;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvPhone, btnEdit, btnDelete;
            ViewHolder(View view) {
                super(view);
                tvName   = view.findViewById(R.id.tvContactName);
                tvPhone  = view.findViewById(R.id.tvContactPhone);
                btnEdit  = view.findViewById(R.id.btnEdit);
                btnDelete = view.findViewById(R.id.btnDelete);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_contact, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Contact c = contacts.get(position);
            holder.tvName.setText(c.name);
            holder.tvPhone.setText(c.phone);
            holder.itemView.setOnClickListener(v -> onClick.run(c));
            holder.btnEdit.setOnClickListener(v -> onEdit.run(c));
            holder.btnDelete.setOnClickListener(v -> onDelete.run(c));
        }

        @Override
        public int getItemCount() { return contacts.size(); }
    }
}