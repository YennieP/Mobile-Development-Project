package edu.northeastern.numad26sp_yanxipan;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuicCalcActivity extends AppCompatActivity {

    private TextView displayText;
    private String currentDisplay = "CALC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quic_calc);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            var systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        displayText = findViewById(R.id.displayText);
        displayText.setText(currentDisplay);

        setupButtons();
    }

    private void setupButtons() {
        int[] numberIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };
        for (int i = 0; i < numberIds.length; i++) {
            final String num = String.valueOf(i);
            findViewById(numberIds[i]).setOnClickListener(v -> onNumberClick(num));
        }

        findViewById(R.id.btnPlus).setOnClickListener(v -> onOperatorClick("+"));
        findViewById(R.id.btnMinus).setOnClickListener(v -> onOperatorClick("-"));
        findViewById(R.id.btnEquals).setOnClickListener(v -> onEqualsClick());
        findViewById(R.id.btnDelete).setOnClickListener(v -> onDeleteClick());
    }

    private void onNumberClick(String number) {
        if (currentDisplay.equals("CALC")) {
            currentDisplay = number;
        } else {
            currentDisplay += number;
        }
        updateDisplay();
    }

    private void onOperatorClick(String operator) {
        if (currentDisplay.equals("CALC")) return;
        char last = currentDisplay.charAt(currentDisplay.length() - 1);
        if (last == '+' || last == '-') {
            currentDisplay = currentDisplay.substring(0, currentDisplay.length() - 1) + operator;
        } else {
            currentDisplay += operator;
        }
        updateDisplay();
    }

    private void onDeleteClick() {
        if (!currentDisplay.isEmpty() && !currentDisplay.equals("CALC")) {
            currentDisplay = currentDisplay.length() == 1
                    ? "CALC"
                    : currentDisplay.substring(0, currentDisplay.length() - 1);
        }
        updateDisplay();
    }

    private void onEqualsClick() {
        if (currentDisplay.equals("CALC")) return;
        try {
            int result = evaluateExpression(currentDisplay);
            currentDisplay = String.valueOf(result);
        } catch (Exception e) {
            currentDisplay = "Error";
        }
        updateDisplay();
    }

    private void updateDisplay() {
        displayText.setText(currentDisplay);
    }

    private int evaluateExpression(String expression) {
        String clean = expression.replace(" ", "");
        java.util.List<String> tokens = new java.util.ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();

        for (char c : clean.toCharArray()) {
            if (c == '+' || c == '-') {
                if (currentNumber.length() > 0) {
                    tokens.add(currentNumber.toString());
                    currentNumber.setLength(0);
                }
                tokens.add(String.valueOf(c));
            } else if (c >= '0' && c <= '9') {
                currentNumber.append(c);
            } else {
                throw new IllegalArgumentException("Invalid character: " + c);
            }
        }
        if (currentNumber.length() > 0) tokens.add(currentNumber.toString());
        if (tokens.isEmpty()) throw new IllegalArgumentException("Empty expression");

        int result = Integer.parseInt(tokens.get(0));
        int i = 1;
        while (i < tokens.size()) {
            if (i + 1 >= tokens.size()) throw new IllegalArgumentException("Invalid format");
            String op = tokens.get(i);
            int next = Integer.parseInt(tokens.get(i + 1));
            if (op.equals("+")) result += next;
            else if (op.equals("-")) result -= next;
            else throw new IllegalArgumentException("Unknown operator: " + op);
            i += 2;
        }
        return result;
    }
}