package com.example.generatorparoley;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {
    private EditText lengthInput;
    private EditText countInput;
    private CheckBox lowercaseCheckBox;
    private CheckBox uppercaseCheckBox;
    private CheckBox digitsCheckBox;
    private CheckBox specialCheckBox;
    private TextView resultTextView;
    private final PasswordGenerator passwordGenerator = new PasswordGenerator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createContentView());
    }

    private View createContentView() {
        ScrollView scrollView = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(24), dp(32), dp(24), dp(32));
        scrollView.addView(root);

        TextView title = new TextView(this);
        title.setText("Генератор паролей");
        title.setTextSize(26);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        root.addView(title, matchWidthWrapHeight());

        lengthInput = createNumberInput(String.valueOf(PasswordGenerator.DEFAULT_LENGTH));
        addLabeledInput(root, "Длина пароля (6–20)", lengthInput);

        countInput = createNumberInput(String.valueOf(PasswordGenerator.MIN_COUNT));
        addLabeledInput(root, "Количество паролей (1–15)", countInput);

        lowercaseCheckBox = createCheckBox("Строчные буквы (a-z)", true);
        uppercaseCheckBox = createCheckBox("Заглавные буквы (A-Z)", true);
        digitsCheckBox = createCheckBox("Цифры (0-9)", true);
        specialCheckBox = createCheckBox("Специальные символы (!@#$%^&*)", false);
        root.addView(lowercaseCheckBox);
        root.addView(uppercaseCheckBox);
        root.addView(digitsCheckBox);
        root.addView(specialCheckBox);

        Button generateButton = new Button(this);
        generateButton.setText("Сгенерировать");
        generateButton.setOnClickListener(view -> generatePasswords());
        root.addView(generateButton, matchWidthWrapHeight());

        resultTextView = new TextView(this);
        resultTextView.setTextSize(18);
        resultTextView.setTypeface(Typeface.MONOSPACE);
        resultTextView.setPadding(0, dp(20), 0, 0);
        root.addView(resultTextView, matchWidthWrapHeight());
        return scrollView;
    }

    private void generatePasswords() {
        try {
            int length = parseNumber(lengthInput, PasswordGenerator.DEFAULT_LENGTH);
            int count = parseNumber(countInput, PasswordGenerator.MIN_COUNT);
            List<String> passwords = passwordGenerator.generate(
                    length,
                    count,
                    lowercaseCheckBox.isChecked(),
                    uppercaseCheckBox.isChecked(),
                    digitsCheckBox.isChecked(),
                    specialCheckBox.isChecked());
            resultTextView.setText(String.join("\n", passwords));
        } catch (IllegalArgumentException exception) {
            resultTextView.setText("");
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private int parseNumber(EditText input, int fallback) {
        String value = input.getText().toString().trim();
        if (value.isEmpty()) {
            return fallback;
        }
        return Integer.parseInt(value);
    }

    private EditText createNumberInput(String defaultValue) {
        EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setText(defaultValue);
        editText.setSelectAllOnFocus(true);
        return editText;
    }

    private CheckBox createCheckBox(String label, boolean checked) {
        CheckBox checkBox = new CheckBox(this);
        checkBox.setText(label);
        checkBox.setTextSize(16);
        checkBox.setChecked(checked);
        return checkBox;
    }

    private void addLabeledInput(LinearLayout root, String label, EditText input) {
        TextView textView = new TextView(this);
        textView.setText(label);
        textView.setTextSize(16);
        textView.setPadding(0, dp(20), 0, 0);
        root.addView(textView, matchWidthWrapHeight());
        root.addView(input, matchWidthWrapHeight());
    }

    private LinearLayout.LayoutParams matchWidthWrapHeight() {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
