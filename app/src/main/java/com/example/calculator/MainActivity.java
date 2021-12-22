package com.example.calculator;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculator.calculator.Calculator;

public class MainActivity extends BaseActivity {
    private final String CALC_EXTRA = "calc";
    private final String VALUE_EXTRA = "value";

    private TextView tvWorkField;

    private String plusSymbol;
    private String minusSymbol;
    private String multiSymbol;
    private String divideSymbol;
    private String lastOperation;

    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
        initSymbols();

        if (savedInstanceState != null) {
            calculator = savedInstanceState.getParcelable(CALC_EXTRA);
            tvWorkField.setText(savedInstanceState.getString(VALUE_EXTRA));
        } else {
            calculator = new Calculator();
        }
    }

    /**
     * Устанавливает главное поле
     * Устанавливает onClickListener для кнопок
     */
    private void setupViews() {
        tvWorkField = findViewById(R.id.tv_work_field);

        findViewById(R.id.btn_1).setOnClickListener(onClickNumber());
        findViewById(R.id.btn_2).setOnClickListener(onClickNumber());
        findViewById(R.id.btn_3).setOnClickListener(onClickNumber());
        findViewById(R.id.btn_4).setOnClickListener(onClickNumber());
        findViewById(R.id.btn_5).setOnClickListener(onClickNumber());
        findViewById(R.id.btn_6).setOnClickListener(onClickNumber());
        findViewById(R.id.btn_7).setOnClickListener(onClickNumber());
        findViewById(R.id.btn_8).setOnClickListener(onClickNumber());
        findViewById(R.id.btn_9).setOnClickListener(onClickNumber());

        findViewById(R.id.btn_0).setOnClickListener(onClickZero());

        findViewById(R.id.btn_dot).setOnClickListener(onClickDot());

        findViewById(R.id.btn_plus).setOnClickListener(onClickOperationButton());
        findViewById(R.id.btn_minus).setOnClickListener(onClickOperationButton());
        findViewById(R.id.btn_multi).setOnClickListener(onClickOperationButton());
        findViewById(R.id.btn_divide).setOnClickListener(onClickOperationButton());

        findViewById(R.id.btn_c).setOnClickListener(onClickClear());
        findViewById(R.id.btn_backspace).setOnClickListener(onClickDelete());
        findViewById(R.id.btn_percent).setOnClickListener(onClickPercent());

        findViewById(R.id.btn_sqrt).setOnClickListener(onClickSqrt());

        findViewById(R.id.btn_result).setOnClickListener(onClickResult());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(VALUE_EXTRA, tvWorkField.getText().toString());
        outState.putParcelable(CALC_EXTRA, calculator);
    }

    /**
     * Устанавливает первоначальные значение для строк операций
     */
    private void initSymbols() {
        plusSymbol = getString(R.string.button_plus);
        minusSymbol = getString(R.string.button_minus);
        multiSymbol = getString(R.string.button_multi);
        divideSymbol = getString(R.string.button_divide);

        lastOperation = "";
    }

    /**
     * @param sb - Текущее выражение
     * @return String - последний символ в выражении
     */
    private String getLastSymbol(StringBuilder sb) {
        if (sb.length() == 0) {
            return "";
        }

        return sb.substring(sb.length() - 1);
    }

    /**
     * @param lastSymbol - символ из выражеия
     * @return true, если symbol является знаком операции (+, -, /, *)
     */
    private boolean isOperationSymbol(String lastSymbol) {
        return lastSymbol.equals(plusSymbol) || lastSymbol.equals(minusSymbol) || lastSymbol.equals(multiSymbol) || lastSymbol.equals(divideSymbol);
    }

    /**
     * @param sb - Текущее выражение
     * @return true если в выражении еще нет знака операции и число содержит точку
     * ИЛИ
     * если число после последнего знака операции содержит точку
     */
    private boolean isNumberAlreadyWithDot(StringBuilder sb) {
        final String dot = getString(R.string.button_dot);
        final String currentText = sb.toString();
        if (lastOperation.isEmpty()) {
            return currentText.contains(dot);
        }

        final int lastOperationSymbolIndex = currentText.lastIndexOf(lastOperation);
        return currentText.substring(lastOperationSymbolIndex).contains(dot);
    }

    /**
     *
     * @param sb - Текущее выражение без знаков операции
     * @return true если sb содержит другие символы кроме 0
     */
    private boolean isContainsOtherSymbolsBesidesZero(StringBuilder sb) {
        char[] symbols  = sb.toString().toCharArray();

        for (char c : symbols) {
            if (c != '0') {
                return true;
            }
        }

        return false;
    }

    /**
     * @return OnClickListener
     *
     * onClickListener для кнопок 1-9
     */
    private View.OnClickListener onClickNumber() {
        return view -> {
            String currentText = tvWorkField.getText().toString();
            Button btn = (Button) view;
            if (currentText.length() == 1 && currentText.startsWith(getString(R.string.button_0))) {
                currentText = btn.getText().toString();
            } else {
                currentText += btn.getText().toString();
            }
            tvWorkField.setText(currentText);
        };
    }

    /**
     * @return OnClickListener
     *
     * onClickListener для кнопки 0
     */
    private View.OnClickListener onClickZero() {
        return view -> {
            final String zero = getString(R.string.button_0);
            StringBuilder sb = new StringBuilder(tvWorkField.getText().toString());

            if (sb.length() == 0) {
                sb.append(zero);
            } else {
                /*
                    Проверка на отсутствие знаков операций
                    если их нет - значит мы все еще вводим первое число.
                    Данная проверка нужна чтобы не получить первое число 0000000000
                 */
                if (lastOperation.isEmpty()) {
                    if (isContainsOtherSymbolsBesidesZero(sb)) {
                        sb.append(zero);
                    }
                } else {
                    String last = getLastSymbol(sb); // последний символ в выражении
                    String penultimate;              // предпоследний символ в выражении

                    if (sb.length() > 2) {
                        penultimate = sb.substring(sb.length() - 2, sb.length() - 1);
                    } else {
                        penultimate = sb.substring(0, 1);
                    }

                    /*
                        Данная проверка нужна чтобы после знака операции
                        вводимое число не содержало лишних нулей
                        Пример такого выражения: +0000000123
                     */
                    if (!isOperationSymbol(penultimate)) {
                        sb.append(zero);
                    } else {
                        if (!last.equals(zero)) {
                            sb.append(zero);
                        }
                    }
                }
            }
            tvWorkField.setText(sb.toString());
        };
    }

    /**
     * @return OnClickListener
     *
     * onClickListener для кнопки символа точки
     */
    private View.OnClickListener onClickDot() {
        return view -> {
            final StringBuilder sb = new StringBuilder(tvWorkField.getText().toString());

            String textWithDot = "";
            if (isOperationSymbol(getLastSymbol(sb)) || sb.length() == 0) {
                textWithDot = "0.";
            } else if (!isNumberAlreadyWithDot(sb)) {
                textWithDot = ".";
            }
            sb.append(textWithDot);

            tvWorkField.setText(sb.toString());
        };
    }

    private View.OnClickListener onClickOperationButton() {
        return view -> {
            StringBuilder sb = new StringBuilder(tvWorkField.getText().toString());
            if (sb.length() > 0) {
                String lastSymbol = sb.substring(sb.length() - 1);
                if (lastSymbol.equals(".") || isOperationSymbol(lastSymbol)) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                Button btn = (Button) view;
                String btnText = btn.getText().toString();
                lastOperation = btnText;
                sb.append(btnText);

                tvWorkField.setText(sb.toString());
            }
        };
    }

    /**
     * @return OnClickListener
     *
     * onClickListener для кнопки очистки поля
     */
    private View.OnClickListener onClickClear() {
        return view -> {
            StringBuilder sb = new StringBuilder(tvWorkField.getText().toString());
            sb.delete(0, sb.length());
            lastOperation = "";

            tvWorkField.setText(sb.toString());
        };
    }

    /**
     * @return OnClickListener
     *
     * onClickListener для кнопки удаления последнего символа
     */
    private View.OnClickListener onClickDelete() {
        return view -> {
            StringBuilder sb = new StringBuilder(tvWorkField.getText().toString());
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                if (sb.length() == 0) {
                    lastOperation = "";
                }
            }

            tvWorkField.setText(sb.toString());
        };
    }

    /**
     * @return OnClickListener
     *
     * onClickListener для кнопки процента
     */
    private View.OnClickListener onClickPercent() {
        return view -> {
            StringBuilder sb = new StringBuilder(tvWorkField.getText().toString());
            if (sb.length() > 0 && lastOperation.isEmpty()) {
                int number = Integer.parseInt(sb.toString());
                double result = (double) number / 100;

                tvWorkField.setText(String.valueOf(result));
            } else {
                Toast.makeText(this, R.string.can_not_calc_percent, Toast.LENGTH_SHORT).show();
            }
        };
    }

    /**
     * @return OnClickListener
     *
     * onClickListener для кнопки вычисления корня
     */
    private View.OnClickListener onClickSqrt() {
        return view -> {
            StringBuilder sb = new StringBuilder(tvWorkField.getText().toString());
            if (sb.length() > 0 && lastOperation.isEmpty()) {
                double number = Double.parseDouble(sb.toString());
                double result = Math.sqrt(number);

                tvWorkField.setText(String.valueOf(result));
            } else {
                Toast.makeText(this, R.string.can_not_calc_percent, Toast.LENGTH_SHORT).show();
            }
        };
    }

    /**
     * @return OnClickListener
     *
     * onClickListener для кнопки результата
     */
    private View.OnClickListener onClickResult() {
        return view -> Toast.makeText(this, R.string.do_nothing, Toast.LENGTH_SHORT).show();
    }

}