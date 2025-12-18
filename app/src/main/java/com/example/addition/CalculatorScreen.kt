package com.example.addition

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat

@Composable
fun CalculatorScreen() {
    var display by remember { mutableStateOf("0") }
    var currentInput by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf<String?>(null) }
    var previousInput by remember { mutableStateOf<String?>(null) }
    var isResult by remember { mutableStateOf(false) }

    val df = DecimalFormat("#.##########")

    fun onClearClick() {
        display = "0"
        currentInput = ""
        operator = null
        previousInput = null
        isResult = false
    }

    fun onNumberClick(number: String) {
        if (display == "Error") {
            onClearClick()
        }
        if (isResult) {
            currentInput = ""
            isResult = false
        }
        currentInput += number
        if (previousInput != null && operator != null) {
            display = "$previousInput $operator $currentInput"
        } else {
            display = currentInput
        }
    }

    fun onEqualsClick() {
        if (currentInput.isNotEmpty() && previousInput != null && operator != null) {
            val num1 = previousInput?.toDoubleOrNull() ?: 0.0
            val num2 = currentInput.toDoubleOrNull() ?: 0.0
            val result = when (operator) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> if (num2 != 0.0) num1 / num2 else Double.NaN
                else -> Double.NaN
            }

            if (result.isNaN()) {
                display = "Error"
                currentInput = ""
                previousInput = null
                operator = null
            } else {
                val resultString = df.format(result)
                display = resultString
                currentInput = resultString
                previousInput = null
                operator = null
            }
            isResult = true
        }
    }

    fun onOperatorClick(op: String) {
        if (currentInput.isNotEmpty() && currentInput != ".") {
            isResult = false
            if (previousInput != null && operator != null) {
                onEqualsClick()
                if (display != "Error") {
                    previousInput = display
                    currentInput = ""
                    operator = op
                    display = "$previousInput $op"
                }
            } else {
                previousInput = currentInput
                currentInput = ""
                operator = op
                display = "$previousInput $op"
            }
        } else if (previousInput != null) {
            operator = op
            display = "$previousInput $op"
        }
    }

    fun onDecimalClick() {
        if (isResult) {
            currentInput = "0"
            isResult = false
        }
        if (!currentInput.contains(".")) {
            currentInput += if (currentInput.isEmpty()) "0." else "."
            if (previousInput != null && operator != null) {
                display = "$previousInput $operator $currentInput"
            } else {
                display = currentInput
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = display,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp, horizontal = 8.dp),
                fontSize = 72.sp,
                textAlign = TextAlign.End,
                maxLines = 1
            )

            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CalculatorButton("AC", Modifier.weight(1f)) { onClearClick() }
                    CalculatorButton("C", Modifier.weight(1f)) { onClearClick() }
                    CalculatorButton("/", Modifier.weight(1f)) { onOperatorClick("/") }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CalculatorButton("7", Modifier.weight(1f)) { onNumberClick("7") }
                    CalculatorButton("8", Modifier.weight(1f)) { onNumberClick("8") }
                    CalculatorButton("9", Modifier.weight(1f)) { onNumberClick("9") }
                    CalculatorButton("*", Modifier.weight(1f)) { onOperatorClick("*") }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CalculatorButton("4", Modifier.weight(1f)) { onNumberClick("4") }
                    CalculatorButton("5", Modifier.weight(1f)) { onNumberClick("5") }
                    CalculatorButton("6", Modifier.weight(1f)) { onNumberClick("6") }
                    CalculatorButton("-", Modifier.weight(1f)) { onOperatorClick("-") }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CalculatorButton("1", Modifier.weight(1f)) { onNumberClick("1") }
                    CalculatorButton("2", Modifier.weight(1f)) { onNumberClick("2") }
                    CalculatorButton("3", Modifier.weight(1f)) { onNumberClick("3") }
                    CalculatorButton("+", Modifier.weight(1f)) { onOperatorClick("+") }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CalculatorButton("0", Modifier.weight(2f)) { onNumberClick("0") }
                    CalculatorButton(".", Modifier.weight(1f)) { onDecimalClick() }
                    CalculatorButton("=", Modifier.weight(1f)) { onEqualsClick() }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = text, fontSize = 32.sp)
    }
}
