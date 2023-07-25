package com.jonkoit.calculator_frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorButtonsFragment : Fragment(), View.OnClickListener {
    private lateinit var inputNumbersTextView: TextView
    private lateinit var resultTextView: TextView
    private val expressionBuilder = StringBuilder()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calculator_buttons, container, false)
        inputNumbersTextView = view.findViewById(R.id.input_numbers)
        resultTextView = view.findViewById(R.id.result_text_view)

        // Set click listeners for all buttons
        view.findViewById<TextView>(R.id.button_c).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_start_bracket).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_end_bracket).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_divide).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_7).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_8).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_9).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_multiply).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_4).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_5).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_6).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_subtract).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_1).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_2).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_3).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_add).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_0).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_dot).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button_equal).setOnClickListener(this)

        return view
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
            val result = ExpressionBuilder(expression).build().evaluate()
            // Round the result to avoid precision issues
            val roundedResult = result.toBigDecimal().setScale(8, java.math.RoundingMode.HALF_UP).toDouble()

            expressionBuilder.clear()
            expressionBuilder.append(roundedResult)
            updateInputTextView()
            updateResultTextView(roundedResult)
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
