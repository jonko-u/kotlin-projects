package com.jonkoIT.calculator

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var inputNumbersTextView: TextView
    private lateinit var resultTextView: TextView
    private val expressionBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputNumbersTextView = findViewById(R.id.input_numbers)
        resultTextView = findViewById(R.id.result_text_view)

        // Set click listeners for all buttons
        findViewById<TextView>(R.id.button_c).setOnClickListener(this)
        findViewById<TextView>(R.id.button_start_bracket).setOnClickListener(this)
        findViewById<TextView>(R.id.button_end_bracket).setOnClickListener(this)
        findViewById<TextView>(R.id.button_divide).setOnClickListener(this)
        findViewById<TextView>(R.id.button_7).setOnClickListener(this)
        findViewById<TextView>(R.id.button_8).setOnClickListener(this)
        findViewById<TextView>(R.id.button_9).setOnClickListener(this)
        findViewById<TextView>(R.id.button_multiply).setOnClickListener(this)
        findViewById<TextView>(R.id.button_4).setOnClickListener(this)
        findViewById<TextView>(R.id.button_5).setOnClickListener(this)
        findViewById<TextView>(R.id.button_6).setOnClickListener(this)
        findViewById<TextView>(R.id.button_subtract).setOnClickListener(this)
        findViewById<TextView>(R.id.button_1).setOnClickListener(this)
        findViewById<TextView>(R.id.button_2).setOnClickListener(this)
        findViewById<TextView>(R.id.button_3).setOnClickListener(this)
        findViewById<TextView>(R.id.button_add).setOnClickListener(this)
        findViewById<TextView>(R.id.button_0).setOnClickListener(this)
        findViewById<TextView>(R.id.button_dot).setOnClickListener(this)
        findViewById<TextView>(R.id.button_equal).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_c -> {
                expressionBuilder.clear()
                updateInputTextView()
            }
            R.id.button_equal -> evaluateExpression()
            else -> {
                val buttonText = (view as TextView).text.toString()
                expressionBuilder.append(buttonText)
                updateInputTextView()
            }
        }
    }

    private fun evaluateExpression() {
        try {
            val expression = expressionBuilder.toString()
            val result = ExpressionEvaluator.evaluate(expression)
            expressionBuilder.clear()
            expressionBuilder.append(result)
            updateInputTextView()
            updateResultTextView(result)
        } catch (e: Exception) {
            // Handle invalid expression or other errors
            expressionBuilder.clear()
            updateInputTextView()
            updateResultTextView("Error")
        }
    }

    private fun updateInputTextView() {
        inputNumbersTextView.text = expressionBuilder.toString()
    }

    private fun updateResultTextView(result: Any) {
        resultTextView.text = result.toString()
    }
}

object ExpressionEvaluator {
    fun evaluate(expression: String): Double {
        return try {
            val result = ExpressionBuilder(expression).build().evaluate()
            // Round the result to avoid precision issues
            result.toBigDecimal().setScale(8, java.math.RoundingMode.HALF_UP).toDouble()
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid expression")
        }
    }
}
