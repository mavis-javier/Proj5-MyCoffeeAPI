package com.example.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 0.15

class MainActivity : AppCompatActivity() {
    private lateinit var baseAmount: EditText
    private lateinit var tipAmount: EditText
    private lateinit var tipDisplay: TextView
    private lateinit var totalDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        baseAmount = findViewById<EditText>(R.id.baseAmount)
        tipAmount = findViewById<EditText>(R.id.tipAmount)
        tipDisplay = findViewById(R.id.tipDisplay)
        totalDisplay = findViewById(R.id.totalDisplay)

        // baseAmount event listener
        baseAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                computeTotalBill()
            }
        })

        tipAmount.setText(INITIAL_TIP_PERCENT.toString())
        // tipAmount event listener
        tipAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                computeTotalBill()
            }
        })
    }

    private fun computeTotalBill() {
        if(baseAmount.text.isEmpty()) {
            tipDisplay.text = ""
            totalDisplay.text = ""
        }
        // 1. Get the value of the base and tip percent
        val baseAmt = baseAmount.text.toString().toDouble()
        val tipPercent = tipAmount.text.toString().toDouble()
        //2. Compute the tip and total
        val tipAmt = baseAmt * tipPercent
        val totalAmt = baseAmt + tipAmt
        // 3. Update the UI
        tipDisplay.text = "%.2f".format(tipAmt)
        totalDisplay.text = "%.2f".format(totalAmt)
    }
}