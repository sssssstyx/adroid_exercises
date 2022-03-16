package com.example.employeesapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.employee_item.view.*
import org.json.JSONArray
import org.json.JSONObject

// Employees Adapter, used in RecyclerView in MainActivity
class EmployeesAdapter(private val employees: JSONArray) : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {
    // Create UI View Holder from XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.employee_item, parent, false)
        return ViewHolder(view)
    }

    // View Holder class to hold UI views
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // add a item click listener
        init {
            itemView.setOnClickListener {
                // create an explicit intent
                val intent = Intent(view.context, EmployeeActivity::class.java)
                // add selected employee JSON as a string data
                intent.putExtra("employee", employees[adapterPosition].toString())
                // start a new activity
                view.context.startActivity(intent)
            }
        }

        val nameTextView: TextView = view.nameTextView
        var genderTextView: TextView = view.genderTextView
        var emailTextView: TextView = view.emailTextView
        var phoneTextView: TextView = view.phoneTextView
        var addressTextView: TextView = view.addressTextView
        var avatarImageView: ImageView = view.avatarImageView
    }

    // Bind data to UI View Holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // employee to bind UI
        val employee: JSONObject = employees.getJSONObject(position)
        // employee lastname and firstname
        holder.nameTextView.text = employee["last_name"].toString() + " " + employee["first_name"].toString()
        holder.genderTextView.text = employee["gender"].toString()
        holder.emailTextView.text = employee["email"].toString()
        holder.phoneTextView.text = employee["phone"].toString()
        holder.addressTextView.text = employee["address"].toString()
        // image
        Glide.with(holder.avatarImageView.context).load(employee["image"]).transform(CircleCrop()).into(holder.avatarImageView)
    }

    // Return item count in employees
    override fun getItemCount(): Int = employees.length()

}
