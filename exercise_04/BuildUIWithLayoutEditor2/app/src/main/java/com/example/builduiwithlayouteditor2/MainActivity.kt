package com.example.builduiwithlayouteditor2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    val firstnames = arrayOf("Renato", "Rosangela", "Tim", "Bartol", "Jeannette")
    val lastnames = arrayOf("Ksenia", "Metzli", "Asuncion", "Zemfina", "Giang")
    val jobtitles = arrayOf("District Quality Coordinator","International Intranet Representative","District Intranet Administrator","Dynamic Research Manager","Central Infrastructure Consultant")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // show first employee data
        showEmployeeData(0)
    }

    fun showEmployeeData(index: Int) {
        val firstnameTextView = findViewById(R.id.textView) as TextView
        val lastnameTextView = findViewById(R.id.textView2) as TextView
        val jobtitleTextView = findViewById(R.id.textView3) as TextView
        val employeeInfoTextView = findViewById(R.id.textView10) as TextView
        val imageView = findViewById(R.id.imageView) as ImageView
        // show data to UI
        firstnameTextView.text = firstnames[index]
        lastnameTextView.text = lastnames[index]
        jobtitleTextView.text = jobtitles[index]
        employeeInfoTextView.text = lastnames[index] + " " + firstnames[index] + " is ....simply dummy text of the printing and\n" +
                "typesetting industry. Lorem lpsum has been the industrys" +
                "standard dummy text ever since the 1 500s, when an unknown" +
                "printer took a galley of type and scrambled it to make a type" +
                "specimen book. It has survived not only five centuries, but" +
                "also the leap into electronic typesetting, remaining essentially" +
                "unchanged. It was popularised in the 1960s with the release" +
                "of Letraset sheets containing Lorem Ipsum passages, and" +
                "more recently with desktop publishing software like Aldus" +
                "PageMaker including versions of Lorem lpsum."
        // loadn and show image from resourses
        var id = 0
        if (index == 0) id = R.drawable.employee1
        else if (index == 1) id = R.drawable.employee2
        else if (index == 2) id = R.drawable.employee3
        else if (index == 3) id = R.drawable.employee4
        else if (index == 4) id = R.drawable.employee5
        imageView.setImageResource(id)
    }

    fun numberClicked(view: View) {
        showEmployeeData(0)
    }
    fun numberClicked2(view: View) {
        showEmployeeData(1)
    }
    fun numberClicked3(view: View) {
        showEmployeeData(2)
    }
    fun numberClicked4(view: View) {
        showEmployeeData(3)
    }
    fun numberClicked5(view: View) {
        showEmployeeData(4)
    }
}
