package edu.northeastern.numad26sp_yanxipan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView

class QuicCalcActivity : AppCompatActivity() {

    // TextView to show text
    private lateinit var displayText: TextView

    // store current text
    private var currentDisplay = "CALC"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quic_calc)

        // handle insets window
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the display area
        displayText = findViewById(R.id.displayText)
        displayText.text = currentDisplay

        // Initialize all buttons
        setupButtons()
    }

    private fun setupButtons() {
        // Buttons 0-9
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        numberButtons.forEachIndexed { index, buttonId ->
            findViewById<Button>(buttonId).setOnClickListener {
                onNumberClick(index.toString())
            }
        }

        // Operator buttons
        findViewById<Button>(R.id.btnPlus).setOnClickListener { onOperatorClick("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { onOperatorClick("-") }

        // '=' button
        findViewById<Button>(R.id.btnEquals).setOnClickListener { onEqualsClick() }

        // Delete button
        findViewById<Button>(R.id.btnDelete).setOnClickListener { onDeleteClick() }
    }

    /**
     * Handle number button clicks
     */
    private fun onNumberClick(number: String) {
        if (currentDisplay == "CALC") {
            currentDisplay = number
        } else {
            currentDisplay += number
        }
        updateDisplay()
    }

    /**
     * Handle operator button clicks
     */
    private fun onOperatorClick(operator: String) {
        if (currentDisplay == "CALC") {
            currentDisplay = operator
        } else {
            currentDisplay += operator
        }
        updateDisplay()
    }

    /**
     * Handling the delete button click ('x')
     */
    private fun onDeleteClick() {
        if (currentDisplay.isNotEmpty() && currentDisplay != "CALC") {
            currentDisplay = if (currentDisplay.length == 1) {
                "CALC"
            } else {
                currentDisplay.dropLast(1)
            }
        }
        updateDisplay()
    }

    /**
     * Handle equals button click
     */
    private fun onEqualsClick() {
        if (currentDisplay == "CALC") {
            return
        }

        try {
            val result = evaluateExpression(currentDisplay)
            currentDisplay = result.toString()
            updateDisplay()
        } catch (e: Exception) {
            // If the expression is invalid, keep it as is or display an error
            currentDisplay = "Error"
            updateDisplay()
        }
    }

    /**
     * Update the displayed content
     */
    private fun updateDisplay() {
        displayText.text = currentDisplay
    }

    /**
     * Calculate simple mathematical expressions
     * Supports addition and subtraction, calculated from left to right
     */
    private fun evaluateExpression(expression: String): Int {
        // Remove all spaces
        val cleanExpression = expression.replace(" ", "")

        // Use regular expressions to separate numbers and operators
        val tokens = mutableListOf<String>()
        var currentNumber = StringBuilder()

        for (char in cleanExpression) {
            when (char) {
                '+', '-' -> {
                    if (currentNumber.isNotEmpty()) {
                        tokens.add(currentNumber.toString())
                        currentNumber.clear()
                    }
                    tokens.add(char.toString())
                }
                in '0'..'9' -> {
                    currentNumber.append(char)
                }
                else -> throw IllegalArgumentException("Invalid character: $char")
            }
        }

        // Add the last number
        if (currentNumber.isNotEmpty()) {
            tokens.add(currentNumber.toString())
        }

        // Validate the expression format.
        if (tokens.isEmpty()) {
            throw IllegalArgumentException("Empty expression")
        }

        // Calculate from left to right
        var result = tokens[0].toInt()
        var i = 1

        while (i < tokens.size) {
            if (i + 1 >= tokens.size) {
                throw IllegalArgumentException("Invalid expression format")
            }

            val operator = tokens[i]
            val nextNumber = tokens[i + 1].toInt()

            result = when (operator) {
                "+" -> result + nextNumber
                "-" -> result - nextNumber
                else -> throw IllegalArgumentException("Unknown operator: $operator")
            }

            i += 2
        }

        return result
    }
}