package com.example.employeesfragmentsapp


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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.employee_item.view.*
import org.json.JSONArray
import org.json.JSONObject

// Employees Adapter, used in RecyclerView in MainActivity
class EmployeesAdapter(
    private val parentActivity: MainActivity,
    private val twoPane: Boolean
) : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {
    private val onClickListener: View.OnClickListener

    companion object {
        var position: Int = 0
    }

    init {

        // initialize click listener code
        onClickListener = View.OnClickListener { v ->
            // get the item position - look also onBindViewHolder code below
            position = v.tag as Int
            // if landscape -> show detail fragment
            if (twoPane) {
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, DetailFragment())
                    .commit()
                // if portrait -> use explicit intent to show employee
            } else {
                val intent = Intent(v.context, DetailActivity::class.java)
                v.context.startActivity(intent)
            }
        }
        // show list and detail side by side (on startup)
        if (twoPane) {
            parentActivity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, DetailFragment())
                .commit()
        }
    }


    // Create UI View Holder from XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.employee_item, parent, false)
        return ViewHolder(view)
    }

    // return item count in employees
    override fun getItemCount(): Int = MainActivity.employees.length()

    // bind data to UI View Holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // employee to bind UI
        val employee: JSONObject = MainActivity.employees.getJSONObject(position)

        // employee lastname and firstname
        holder.nameTextView.text = employee["last_name"].toString() + " " + employee["first_name"].toString()
        holder.genderTextView.text = employee["gender"].toString()
        holder.emailTextView.text = employee["email"].toString()
        holder.phoneTextView.text = employee["phone"].toString()
        holder.addressTextView.text = employee["address"].toString()
        // image
        // Glide.with(holder.avatarImageView.context).load(employee["image"]).transform(CircleCrop()).into(holder.avatarImageView)

        // image - res/dimens.xml - <dimen name="corner_radius">4dp</dimen>
        Glide.with(holder.avatarImageView.context)
            .load(employee["image"].toString())
            .apply(
                RequestOptions()
                .override(300,300))
            .centerCrop()
            .transform(RoundedCorners(R.dimen.corner_radius))
            .into(holder.avatarImageView)

        // add position tag and click listener to holder view
        with(holder.itemView) {
            tag = position
            setOnClickListener(onClickListener)
        }
    }

    // View Holder class to hold UI views
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.nameTextView
        var genderTextView: TextView = view.genderTextView
        var emailTextView: TextView = view.emailTextView
        var phoneTextView: TextView = view.phoneTextView
        var addressTextView: TextView = view.addressTextView
        var avatarImageView: ImageView = view.avatarImageView
    }


}
