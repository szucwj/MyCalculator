package com.byd.mycalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvInputRecords;
    private TextView mTvInputCurrent;
    private Button mBtnNumber1;
    private Button mBtnNumber2;
    private Button mBtnNumber3;
    private Button mBtnNumber4;
    private Button mBtnNumber5;
    private Button mBtnNumber6;
    private Button mBtnNumber7;
    private Button mBtnNumber8;
    private Button mBtnNumber9;
    private Button mBtnNumber0;
    private Button mBtnNumberPoint;
    private Button mBtnOperatorPlus;
    private Button mBtnOperatorSub;
    private Button mBtnOperatorMul;
    private Button mBtnOperatorDiv;
    private Button mBtnOperatorEquals;
    private Button mBtnOperatorSqrt;
    private Button mBtnOperatorInverse;
    private Button mBtnOperatorMod;
    private Button mBtnBracketLeft;
    private Button mBtnBracketRight;
    private Button mBtnClear;
    private Button mBtnClearEntry;
    private Button mBtnDelete;
    private Button mBtnOpposite;

    private static String mRecords = "";
    private static String mCurrent = "";
    private static boolean mHasPoint = false;
    private static boolean mPressEquals = false;

    private final int LENGTH = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        click();

    }

    protected String[] dealWithRecords() {
        int index = 0;
        String[] string = new String[LENGTH];

        for (int i = 0; i < mRecords.length(); i++) {
            if (mRecords.substring(i, i + 1).charAt(0) == '.'
                    || (mRecords.substring(i, i + 1).charAt(0) >= '0' && mRecords.substring(i, i + 1).charAt(0) <= '9')) {

                int j = i;
                String temp = "";
                while (mRecords.substring(j, j + 1).charAt(0) == '.'
                        || (mRecords.substring(j, j + 1).charAt(0) >= '0' && mRecords.substring(j, j + 1).charAt(0) <= '9')) {

                    temp += mRecords.substring(j, j + 1);
                    j++;
                    if (j == mRecords.length()) {
                        break;
                    }
                }

                i = j - 1;
                string[index++] = temp;

            } else {
                string[index++] = mRecords.substring(i, i + 1);
            }
        }
        string[index] = null;
        return string;
    }

    public String calcTemp(String[] t) {
        String[] temp = t;
        int length = 0;
        while (temp[length] != null) length++;

        for (int i = 0; i < length && temp[i] != null; i++) {
            //        Log.d("i",i+"");
            if (temp[i].equals("x") || temp[i].equals("÷") || temp[i].equals("%")) {
                if (i == 0 || i == length - 1) {
                    throw new RuntimeException("输入错误");
                } else {
                    if (temp[i].equals("x")) {
                        double r = Double.parseDouble(temp[i - 1]) * Double.parseDouble(temp[i + 1]);
                        i--;
                        temp[i] = String.valueOf(r);
                        int j = i;
                        while (temp[j + 3] != null) {
                            temp[j + 1] = temp[j + 3];
                            j++;
                        }
                        temp[j + 1] = null;
                    } else if (temp[i].equals("÷")) {
                        double r = Double.parseDouble(temp[i - 1]) / Double.parseDouble(temp[i + 1]);
                        i--;
                        temp[i] = String.valueOf(r);
                        int j = i;
                        while (temp[j + 3] != null) {
                            temp[j + 1] = temp[j + 3];
                            j++;
                        }
                        temp[j + 1] = null;
                    } else {
                        double r = (int) (Double.parseDouble(temp[i - 1]) % Double.parseDouble(temp[i + 1]));
                        i--;
                        temp[i] = String.valueOf(r);
                        int j = i;
                        while (temp[j + 3] != null) {
                            temp[j + 1] = temp[j + 3];
                            j++;
                        }
                        temp[j + 1] = null;
                    }
                }
            }
        }

        for (int i = 0; i < length && temp[i] != null; i++) {

            if (temp[i].equals("+") || temp[i].equals("－")) {
                if (i == 0 || i == length - 1) {
                    throw new RuntimeException("输入错误");
                } else {
                    if (temp[i].equals("+")) {
                        double r = Double.parseDouble(temp[i - 1]) + Double.parseDouble(temp[i + 1]);
                        i--;
                        temp[i] = String.valueOf(r);
                        int j = i;
                        while (temp[j + 3] != null) {
                            temp[j + 1] = temp[j + 3];
                            j++;
                        }
                        temp[j + 1] = null;
                    } else {
                        double r = Double.parseDouble(temp[i - 1]) - Double.parseDouble(temp[i + 1]);
                        i--;
                        temp[i] = String.valueOf(r);
                        int j = i;
                        while (temp[j + 3] != null) {
                            temp[j + 1] = temp[j + 3];
                            j++;
                        }
                        temp[j + 1] = null;
                    }
                }
            }
        }

        String[] result = temp[0].split("\\.");
        int num;
        if (result[1].length() >= 7) {
            num = Integer.parseInt(result[1].substring(0, 7));
        } else {
            num = Integer.parseInt(result[1]);
        }

        if (num == 0) {
            return result[0];
        } else {
            return temp[0];
        }

    }

    public String calcResult(String[] records) {
        //     Log.d("msg:","len:"+records.length);
        MyStack<String> stack = new MyStack<>(LENGTH);
        int index = 0;
        while (records[index] != null) index++;
        index--;
        while (index >= 0 && records[index] != null) {

            if (!records[index].equals("(")) {
                stack.push(records[index]);
            } else {
                int i = 0;
                String[] temp = new String[LENGTH];
                while (!stack.peek().equals(")")) {
                    temp[i++] = stack.pop();
                }
                temp[i] = null;
                stack.pop();    //弹出右括号
                stack.push(calcTemp(temp));
            }
            index--;
        }

        String[] finalTemp = new String[LENGTH];
        int finalIndex = 0;
        while (!stack.isEmpty()) {
            finalTemp[finalIndex++] = stack.pop();
        }
        finalTemp[finalIndex] = null;

        return calcTemp(finalTemp);
    }

    public void click() {
        mBtnNumber1.setOnClickListener(MainActivity.this);
        mBtnNumber2.setOnClickListener(MainActivity.this);
        mBtnNumber3.setOnClickListener(MainActivity.this);
        mBtnNumber4.setOnClickListener(MainActivity.this);
        mBtnNumber5.setOnClickListener(MainActivity.this);
        mBtnNumber6.setOnClickListener(MainActivity.this);
        mBtnNumber7.setOnClickListener(MainActivity.this);
        mBtnNumber8.setOnClickListener(MainActivity.this);
        mBtnNumber9.setOnClickListener(MainActivity.this);
        mBtnNumber0.setOnClickListener(MainActivity.this);
        mBtnNumberPoint.setOnClickListener(MainActivity.this);
        mBtnOperatorPlus.setOnClickListener(MainActivity.this);
        mBtnOperatorSub.setOnClickListener(MainActivity.this);
        mBtnOperatorMul.setOnClickListener(MainActivity.this);
        mBtnOperatorDiv.setOnClickListener(MainActivity.this);
        mBtnOperatorEquals.setOnClickListener(MainActivity.this);
        mBtnOperatorSqrt.setOnClickListener(MainActivity.this);
        mBtnOperatorMod.setOnClickListener(MainActivity.this);
        mBtnOperatorInverse.setOnClickListener(MainActivity.this);
        mBtnBracketLeft.setOnClickListener(MainActivity.this);
        mBtnBracketRight.setOnClickListener(MainActivity.this);
        mBtnClearEntry.setOnClickListener(MainActivity.this);
        mBtnClear.setOnClickListener(MainActivity.this);
        mBtnDelete.setOnClickListener(MainActivity.this);
        mBtnOpposite.setOnClickListener(MainActivity.this);
    }

    public void initView() {
        mTvInputRecords = (TextView) findViewById(R.id.input_records);
        mTvInputCurrent = (TextView) findViewById(R.id.input_current);
        mBtnNumber1 = (Button) findViewById(R.id.number_1);
        mBtnNumber2 = (Button) findViewById(R.id.number_2);
        mBtnNumber3 = (Button) findViewById(R.id.number_3);
        mBtnNumber4 = (Button) findViewById(R.id.number_4);
        mBtnNumber5 = (Button) findViewById(R.id.number_5);
        mBtnNumber6 = (Button) findViewById(R.id.number_6);
        mBtnNumber7 = (Button) findViewById(R.id.number_7);
        mBtnNumber8 = (Button) findViewById(R.id.number_8);
        mBtnNumber9 = (Button) findViewById(R.id.number_9);
        mBtnNumber0 = (Button) findViewById(R.id.number_0);
        mBtnNumberPoint = (Button) findViewById(R.id.number_point);
        mBtnOperatorPlus = (Button) findViewById(R.id.operator_plus);
        mBtnOperatorSub = (Button) findViewById(R.id.operator_sub);
        mBtnOperatorMul = (Button) findViewById(R.id.operator_mul);
        mBtnOperatorDiv = (Button) findViewById(R.id.operator_div);
        mBtnOperatorEquals = (Button) findViewById(R.id.equals);
        mBtnOperatorSqrt = (Button) findViewById(R.id.operator_sqrt);
        mBtnOperatorMod = (Button) findViewById(R.id.operator_mod);
        mBtnOperatorInverse = (Button) findViewById(R.id.operator_inverse);
        mBtnBracketLeft = (Button) findViewById(R.id.bracket_left);
        mBtnBracketRight = (Button) findViewById(R.id.bracket_right);
        mBtnDelete = (Button) findViewById(R.id.delete);
        mBtnClearEntry = (Button) findViewById(R.id.clear_entry);
        mBtnClear = (Button) findViewById(R.id.clear);
        mBtnOpposite = (Button) findViewById(R.id.opposite);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_entry:
                mRecords = mRecords.substring(0, mRecords.length() - mCurrent.length());
                mTvInputRecords.setText(mRecords);
                mCurrent = "";
                mTvInputCurrent.setText("0");
                mHasPoint = false;
                mPressEquals = false;
                break;
            case R.id.clear:
                mRecords = "";
                mCurrent = "";
                mTvInputCurrent.setText("0");
                mTvInputRecords.setText("");
                mHasPoint = false;
                mPressEquals = false;
                break;
            case R.id.delete:
                if (!mCurrent.equals("")) {
                    mCurrent = mCurrent.substring(0, mCurrent.length() - 1);
                    mTvInputCurrent.setText(mCurrent);
                    mRecords = mRecords.substring(0, mRecords.length() - 1);
                    mTvInputRecords.setText(mRecords);
                } else {
                    mRecords = mRecords.substring(0, mRecords.length() - 1);
                    mTvInputRecords.setText(mRecords);
                }
                mPressEquals = false;
                break;
            case R.id.operator_sqrt:
                double num = Double.parseDouble(mTvInputCurrent.getText().toString());
                mTvInputCurrent.setText(Math.sqrt(num) + "");
                break;
            case R.id.operator_inverse:
                double num1 = Double.parseDouble(mTvInputCurrent.getText().toString());
                if (num1 != 0) {
                    mTvInputCurrent.setText((1 / num1) + "");
                } else {

                }
                break;
            case R.id.opposite:
                break;
            case R.id.equals:
                String result = calcResult(dealWithRecords());
                mTvInputCurrent.setText(result);
                //      mRecords = "";
                mCurrent = "";
                mPressEquals = true;
                break;
            default:
                if (mPressEquals) {
                    mRecords = "";
                }
                switch (v.getId()) {
                    case R.id.operator_plus:
                    case R.id.operator_sub:
                    case R.id.operator_mul:
                    case R.id.operator_div:
                    case R.id.operator_mod:
                        if (mPressEquals) {
                            mRecords = mTvInputCurrent.getText().toString()
                                    + ((Button) findViewById(v.getId())).getText().toString();
                            mTvInputRecords.setText(mRecords);
                        } else {
                            mRecords = mRecords + ((Button) findViewById(v.getId())).getText().toString();
                            mTvInputRecords.setText(mRecords);
                        }
                        mTvInputCurrent.setText("0");
                        mCurrent = "";
                        mHasPoint = false;
                        break;
                    case R.id.bracket_left:
                    case R.id.bracket_right:
                        mRecords = mRecords + ((Button) findViewById(v.getId())).getText().toString();
                        mTvInputRecords.setText(mRecords);
                        break;
                    default:
                        if ((v.getId() == R.id.number_point && !mHasPoint) || (v.getId() != R.id.number_point)) {
                            if (v.getId() == R.id.number_point && !mHasPoint) {
                                if (mCurrent.equals("")) {
                                    mCurrent = "0";
                                    mRecords += "0";
                                }
                            } else {

                            }
                            mCurrent += ((Button) findViewById(v.getId())).getText().toString();
                            mTvInputCurrent.setText(mCurrent);
                            mRecords = mRecords + ((Button) findViewById(v.getId())).getText().toString();
                            mTvInputRecords.setText(mRecords);
                        }
                        if (v.getId() == R.id.number_point) {
                            mHasPoint = true;
                        }
                        break;
                }
                mPressEquals = false;
                break;
        }
    }


}
