package h.nas.avi.calculator;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class adapter extends BaseAdapter {
    private Context mContext;
    private OnClickListener mOnButtonClick;
    public adapter(Context c) {
        mContext = c;
    }

    public void setOnButtonClickListener(OnClickListener listener) {
        mOnButtonClick = listener;
    }

    public int getCount() {
        return mButtons.length;
    }

    public Object getItem(int position) {
        return mButtons[position];
    }
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Button btn;
        if (convertView == null) {
            btn = new Button(mContext);
            KeypadButton keypadButton = mButtons[position];

            if (keypadButton != KeypadButton.DUMMY) {
                btn.setOnClickListener(mOnButtonClick);
                btn.setBackgroundResource(R.drawable.keypad);

                if(keypadButton==KeypadButton.CALCULATE)
                    btn.setBackgroundResource(R.drawable.calculate);
            }

            btn.setTag(keypadButton);
        } else {
            btn = (Button) convertView;
        }
        btn.setText(mButtons[position].getText());
        return btn;
    }

    private KeypadButton[] mButtons = { KeypadButton.MC, KeypadButton.MR,KeypadButton.MS, KeypadButton.M_ADD, KeypadButton.M_REMOVE,
            KeypadButton.BACKSPACE, KeypadButton.CE, KeypadButton.C,KeypadButton.SIGN, KeypadButton.SQRT,
            KeypadButton.SEVEN,KeypadButton.EIGHT, KeypadButton.NINE, KeypadButton.DIV,KeypadButton.PERCENT,
            KeypadButton.FOUR, KeypadButton.FIVE,KeypadButton.SIX, KeypadButton.MULTIPLY, KeypadButton.RECIPROC,
            KeypadButton.ONE, KeypadButton.TWO, KeypadButton.THREE,KeypadButton.MINUS, KeypadButton.DECIMAL_SEP,
            KeypadButton.LOG, KeypadButton.ZERO,KeypadButton.POW,KeypadButton.PLUS, KeypadButton.CALCULATE};

}
