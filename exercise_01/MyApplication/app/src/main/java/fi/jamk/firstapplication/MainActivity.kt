package fi.jamk.firstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buttonClicked(view: View) {
        // activity_main layout has textView id in TextView element
        val textView = findViewById(R.id.text) as TextView

        textView.setText(R.string.button_clicked_txt)
    }
}