package h.nas.avi.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Stack;

public class main extends Activity {
    GridView mKeypadGrid;
    TextView userInputText;
    TextView memoryStatText;

    Stack<String> mInputStack;
    Stack<String> mOperationStack;

    adapter mKeypadAdapter;
    TextView mStackText;
    boolean resetInput = false;
    boolean hasFinalResult = false;
    String mDecimalSeperator;
    double memoryValue = Double.NaN;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DecimalFormat currencyFormatter = (DecimalFormat) NumberFormat
                .getInstance();
        char decimalSeperator = currencyFormatter.getDecimalFormatSymbols()
                .getDecimalSeparator();
        mDecimalSeperator = Character.toString(decimalSeperator);

        setContentView(R.layout.main);
        mInputStack = new Stack<String>();
        mOperationStack = new Stack<String>();
        mKeypadGrid = (GridView) findViewById(R.id.grdButtons);
        userInputText = (TextView) findViewById(R.id.txtInput);
        userInputText.setText("0");

        memoryStatText = (TextView) findViewById(R.id.txtMemory);
        memoryStatText.setText("");

        mStackText = (TextView) findViewById(R.id.txtStack);
        mKeypadAdapter = new adapter(this);
        mKeypadGrid.setAdapter(mKeypadAdapter);
        mKeypadAdapter.setOnButtonClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                KeypadButton keypadButton = (KeypadButton) btn.getTag();
                ProcessKeypadInput(keypadButton);
            }
        });
        mKeypadGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.action_settings) {
            Toast.makeText(this,"Designed and Created by Avinash",Toast.LENGTH_LONG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void ProcessKeypadInput(KeypadButton keypadButton) {
        String text = keypadButton.getText().toString();
        String currentInput = userInputText.getText().toString();

        int currentInputLen = currentInput.length();
        String evalResult = null;
        double userInputValue = Double.NaN;

        switch (keypadButton) {
            case BACKSPACE:
                if (resetInput)
                    return;

                int endIndex = currentInputLen - 1;
                if (endIndex < 1) {
                    userInputText.setText("0");
                }
                else if (currentInput=="Infinity")
                    userInputText.setText("0");
                else {
                    userInputText.setText(currentInput.subSequence(0, endIndex));
                }
                break;
            case SIGN:
                if (currentInputLen > 0 && currentInput != "0") {
                    if (currentInput.charAt(0) == '-') {
                        userInputText.setText(currentInput.subSequence(1,
                                currentInputLen));
                    }

                    else {
                        userInputText.setText("-" + currentInput.toString());
                    }
                }
                break;

            /*
            case SIN:
                if (currentInputLen > 0 && Double.parseDouble(currentInput) >= 0) {

                    userInputText.setText((doubleToString(Math.sin(Math.toDegrees(Double.parseDouble(currentInput))))));
                }
                break;
            case COS:
                if (currentInputLen > 0 && Double.parseDouble(currentInput) >= 0) {

                    userInputText.setText((doubleToString(Math.cos(Math.toDegrees(Double.parseDouble(currentInput))))));
                }
                break;
            case TAN:
                if (currentInputLen > 0 && Double.parseDouble(currentInput) >= 0) {

                    userInputText.setText((doubleToString(Math.tan(Math.toDegrees(Double.parseDouble(currentInput))))));
                }
                break;
                */
            case POW:
                if (currentInputLen > 0 && Double.parseDouble(currentInput) >= 0) {

                    userInputText.setText(doubleToString( Math.pow(10,Double.parseDouble(currentInput))));
                }
                break;
            case LOG:
                if (currentInputLen > 0 && Double.parseDouble(currentInput) >= 0) {

                    userInputText.setText((doubleToString(Math.log10(Double.parseDouble(currentInput)))));
                }
                break;
            case SQRT:
                if (currentInputLen > 0 && currentInput != "0") {

                    userInputText.setText((doubleToString(Math.sqrt(Double.parseDouble(currentInput)))));
                }
                break;
            case CE:
                userInputText.setText("0");
                break;
            case C:
                userInputText.setText("0");
                clearStacks();
                break;
            case RECIPROC:
                if (currentInputLen > 0 && currentInput != "0") {
                    userInputText.setText((doubleToString(1/(Double.parseDouble(currentInput)))));
                }
                break;
            case DECIMAL_SEP:
                if (hasFinalResult || resetInput) {
                    userInputText.setText("0" + mDecimalSeperator);
                    hasFinalResult = false;
                    resetInput = false;
                } else if (currentInput.contains("."))
                    return;
                else
                    userInputText.append(mDecimalSeperator);
                break;
            case DIV:
            case PLUS:
            case MINUS:
            case MULTIPLY:
                if (resetInput) {
                    mInputStack.pop();
                    mOperationStack.pop();
                } else {
                    if (currentInput.charAt(0) == '-') {
                        mInputStack.add("(" + currentInput + ")");
                    } else {
                        mInputStack.add(currentInput);
                    }
                    mOperationStack.add(currentInput);
                }

                mInputStack.add(text);
                mOperationStack.add(text);

                dumpInputStack();
                evalResult = evaluateResult(false);
                if (evalResult != null)
                    userInputText.setText(evalResult);

                resetInput = true;
                break;
            case CALCULATE:
                if (mOperationStack.size() == 0)
                    break;

                mOperationStack.add(currentInput);
                evalResult = evaluateResult(true);
                if (evalResult != null) {
                    clearStacks();
                    userInputText.setText(evalResult);
                    resetInput = false;
                    hasFinalResult = true;
                }
                break;
            case M_ADD:
                userInputValue = tryParseUserInput();
                if (Double.isNaN(userInputValue))
                    return;
                if (Double.isNaN(memoryValue))
                    memoryValue = 0;
                memoryValue += userInputValue;
                displayMemoryStat();

                hasFinalResult = true;

                break;
            case PERCENT:
                userInputValue = tryParseUserInput();
                if (Double.isNaN(userInputValue))
                    return;
                if (Double.isNaN(memoryValue))
                    memoryValue = 0;
                memoryValue =memoryValue*userInputValue/100;
                displayMemoryStat();

                hasFinalResult = true;

                break;
            case M_REMOVE:
                userInputValue = tryParseUserInput();
                if (Double.isNaN(userInputValue))
                    return;
                if (Double.isNaN(memoryValue))
                    memoryValue = 0;
                memoryValue -= userInputValue;
                displayMemoryStat();
                hasFinalResult = true;
                break;
            case MC:
                memoryValue = Double.NaN;
                displayMemoryStat();
                break;
            case MR:
                if (Double.isNaN(memoryValue))
                    return;
                userInputText.setText(doubleToString(memoryValue));
                displayMemoryStat();
                break;
            case MS:
                userInputValue = tryParseUserInput();
                if (Double.isNaN(userInputValue))
                    return;
                memoryValue = userInputValue;
                displayMemoryStat();
                hasFinalResult = true;
                break;
            default:
                if (Character.isDigit(text.charAt(0))) {
                    if (currentInput.equals("0") || resetInput || hasFinalResult) {
                        userInputText.setText(text);
                        resetInput = false;
                        hasFinalResult = false;
                    } else {
                        userInputText.append(text);
                        resetInput = false;
                    }

                }
                break;

        }

    }

    private void clearStacks() {
        mInputStack.clear();
        mOperationStack.clear();
        mStackText.setText("");
    }

    private void dumpInputStack() {
        Iterator<String> it = mInputStack.iterator();
        StringBuilder sb = new StringBuilder();

        while (it.hasNext()) {
            CharSequence iValue = it.next();
            sb.append(iValue);

        }

        mStackText.setText(sb.toString());
    }

    private String evaluateResult(boolean requestedByUser) {
        if ((!requestedByUser && mOperationStack.size() != 4)
                || (requestedByUser && mOperationStack.size() != 3))
            return null;

        String left = mOperationStack.get(0);
        String operator = mOperationStack.get(1);
        String right = mOperationStack.get(2);
        String tmp = null;
        if (!requestedByUser)
            tmp = mOperationStack.get(3);

        double leftVal = Double.parseDouble(left.toString());
        double rightVal = Double.parseDouble(right.toString());
        double result = Double.NaN;

        if (operator.equals(KeypadButton.DIV.getText())) {
            result = leftVal / rightVal;
        } else if (operator.equals(KeypadButton.MULTIPLY.getText())) {
            result = leftVal * rightVal;

        } else if (operator.equals(KeypadButton.PLUS.getText())) {
            result = leftVal + rightVal;
        } else if (operator.equals(KeypadButton.MINUS.getText())) {
            result = leftVal - rightVal;

        }

        String resultStr = doubleToString(result);
        if (resultStr == null)
            return null;

        mOperationStack.clear();
        if (!requestedByUser) {
            mOperationStack.add(resultStr);
            mOperationStack.add(tmp);
        }

        return resultStr;
    }

    private String doubleToString(double value) {
        if (Double.isNaN(value))
            return null;

        long longVal = (long) value;
        if (longVal == value)
            return Long.toString(longVal);
        else
            return Double.toString(value);

    }

    private double tryParseUserInput() {
        String inputStr = userInputText.getText().toString();
        double result = Double.NaN;
        try {
            result = Double.parseDouble(inputStr);

        } catch (NumberFormatException nfe) {
        }
        return result;

    }

    private void displayMemoryStat() {
        if (Double.isNaN(memoryValue)) {
            memoryStatText.setText("");
        } else {
            memoryStatText.setText("M = " + doubleToString(memoryValue));
        }
    }

}