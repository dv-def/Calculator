package com.example.calculator.calculator;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.calculator.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Calculator implements Parcelable {
    private Double operand1;
    private Double operand2;
    private String operator;

    private Context context;

    public Calculator(Context context) {
        this.context = context;
    }

    protected Calculator(Parcel in) {
        if (in.readByte() == 0) {
            operand1 = null;
        } else {
            operand1 = in.readDouble();
        }
        if (in.readByte() == 0) {
            operand2 = null;
        } else {
            operand2 = in.readDouble();
        }
        operator = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (operand1 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(operand1);
        }
        if (operand2 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(operand2);
        }
        dest.writeString(operator);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };

    public String result() throws ArithmeticException {
        Double result;

        if (isPlus(this.operator)) {
            result = operand1 + operand2;
        } else if (isMinus(this.operator)) {
            result = operand1 - operand2;
        } else if (isMulti(this.operator)) {
            result = operand1 * operand2;
        } else {
            result = operand1 / operand2;
        }

        if (result.isInfinite()) {
            throw new ArithmeticException();
        }

        return Double.toString(result);
    }

    public void setOperand1(Double operand1) {
        this.operand1 = operand1;
    }

    public void setOperand2(Double operand2) {
        this.operand2 = operand2;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    private boolean isPlus(String s) {
        return s.equals(context.getString(R.string.button_plus));
    }

    private boolean isMinus(String s) {
        return s.equals(context.getString(R.string.button_minus));
    }

    private boolean isMulti(String s) {
        return s.equals(context.getString(R.string.button_multi));
    }

    private boolean isDivide(String s) {
        return s.equals(context.getString(R.string.button_divide));
    }
}
