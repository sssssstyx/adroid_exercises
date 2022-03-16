package com.example.employeesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.activity_employee.*
import org.json.JSONObject

class EmployeeActivity : AppCompatActivity() {

    // EmployeeActivity's onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        // get data from intent
        val bundle: Bundle? = intent.extras;
        if (bundle != null) {
            val employeeString = bundle!!.getString("employee")
            val employee = JSONObject(employeeString)
            // val name = employee["first_name"]
            // modify here to display other employee's data too!
            // Toast.makeText(this, "Name is $name",Toast.LENGTH_LONG).show()
            nameTextView.text = employee["last_name"].toString() + " " + employee["first_name"].toString()
            genderTextView.text = employee["gender"].toString()
            emailTextView.text = employee["email"].toString()
            phoneTextView.text = employee["phone"].toString()
            addressTextView.text = employee["address"].toString()
            Glide.with(avatarImageView.context).load(employee["image"]).transform(CircleCrop()).into(avatarImageView)
        }
    }

}
