package com.example.sumcalculator

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var sum : Int = 0;
    private var operand : Int = 0;
    private var state : String? = ""

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("sum", this.sum)
        outState.putInt("operand", this.operand)
        outState.putString("state", this.state)

        outState.putString("expression", calculator_expression.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        this.sum = savedInstanceState.getInt("sum")
        this.operand = savedInstanceState.getInt("operand")
        this.state = savedInstanceState.getString("state")

        calculator_expression.text = savedInstanceState.getString("expression")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun operandClick (view: View) {
        var operand = (view as Button).text.toString().toInt()
        println(operand)

        if (this.state == "equal") {
            this.operand = 0
            this.sum = 0

            calculator_expression.text = "0"
        }

        this.state = ""

        this.operand = (this.operand.toString() + operand.toString()).toInt()

        if (this.operand == 0) return

        if (calculator_expression.text.toString() == "0") {
            calculator_expression.text = operand.toString()
        } else {
            calculator_expression.append(operand.toString())
        }
    }

    fun addClick (view: View) {
        if (this.state == "add") return
        this.state = "add"

        this.sum += this.operand
        this.operand = 0

        calculator_expression.append("+")
    }

    fun equalClick (view: View) {
        if (this.state == "equal") return
        this.state = "equal"

        this.sum += this.operand
        this.operand = 0

        calculator_expression.text = this.sum.toString()
    }

    fun clearClick (view: View) {
        this.operand = 0
        this.sum = 0

        this.state = ""

        calculator_expression.text = "0"
    }
}
