package de.gemuesehasser.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import de.gemuesehasser.test.constant.CalcInputButtonType;
import de.gemuesehasser.test.handler.MathHandler;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    @Override
    @SuppressLint("SetTextI18n")
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final TextView calcView = findViewById(R.id.calcView);
        calcView.setPadding(15, 0, 15, 0);

        for (@NotNull final CalcInputButtonType buttonType : CalcInputButtonType.values()) {
            final Button button = findViewById(buttonType.getId());

            if (!button.getText().toString().equals("x^y")) {
                button.setOnClickListener(view -> calcView.setText(calcView.getText() + button.getText().toString()));
                continue;
            }

            button.setOnClickListener(view -> calcView.setText(calcView.getText() + "^"));
        }

        final Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(view -> {
            if (calcView.getText() == null || calcView.getText().toString().isEmpty()) return;

            calcView.setText(calcView.getText().subSequence(0, calcView.getText().length() - 1));
        });

        final Button buttonAC = findViewById(R.id.buttonAC);
        buttonAC.setOnClickListener(view -> calcView.setText(""));

        final Button buttonEquals = findViewById(R.id.buttonEquals);
        buttonEquals.setOnClickListener(view -> {
            final String term = calcView.getText().toString()
                    .replaceAll("√", "sqrt")
                    .replaceAll("÷", "/")
                    .replaceAll("×", "*")
                    .replaceAll(",", ".");

            final double result = MathHandler.eval(term, findViewById(R.id.main));

            calcView.setText(String.valueOf(result).replaceAll("\\.", ","));
        });

        final Button buttonDrawFunction = findViewById(R.id.buttonDrawFunction);
        buttonDrawFunction.setOnClickListener(view -> {
            Intent functionDrawIntent = new Intent(MainActivity.this, FunctionDrawActivity.class);
            functionDrawIntent.putExtra("functionDraw", "gui");
            MainActivity.this.startActivity(functionDrawIntent);
        });
    }
}