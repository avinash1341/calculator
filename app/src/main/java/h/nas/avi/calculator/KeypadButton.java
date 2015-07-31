package h.nas.avi.calculator;

public enum KeypadButton {
    MC("MC",bcategory.MEMORYBUFFER)
    , MR("MR",bcategory.MEMORYBUFFER)
    , MS("MS",bcategory.MEMORYBUFFER)
    , M_ADD("M+",bcategory.MEMORYBUFFER)
    , M_REMOVE("M-",bcategory.MEMORYBUFFER)
    , BACKSPACE("<-",bcategory.CLEAR)
    , CE("CE",bcategory.CLEAR)
    , C("C",bcategory.CLEAR)
    , ZERO("0",bcategory.NUMBER)
    , ONE("1",bcategory.NUMBER)
    , TWO("2",bcategory.NUMBER)
    , THREE("3",bcategory.NUMBER)
    , FOUR("4",bcategory.NUMBER)
    , FIVE("5",bcategory.NUMBER)
    , SIX("6",bcategory.NUMBER)
    , SEVEN("7",bcategory.NUMBER)
    , EIGHT("8",bcategory.NUMBER)
    , NINE("9",bcategory.NUMBER)
    , PLUS(" + ",bcategory.OPERATOR)
    , MINUS(" - ",bcategory.OPERATOR)
    , MULTIPLY(" X ",bcategory.OPERATOR)
    , DIV(" / ",bcategory.OPERATOR)
    , RECIPROC("1/x",bcategory.OTHER)
    , DECIMAL_SEP(",",bcategory.OTHER)
    , SIGN("+/-",bcategory.OTHER)
    , LOG("lg10",bcategory.OTHER)
    , SQRT("SQRT",bcategory.OTHER)
    , PERCENT("%",bcategory.OTHER)
    , POW("10^",bcategory.OTHER)
    , SIN("sin(x)",bcategory.OTHER)
    , COS("cos(x)",bcategory.OTHER)
    , TAN("tan(x)",bcategory.OTHER)
    , CALCULATE("=",bcategory.RESULT)
    , DUMMY("",bcategory.DUMMY);

    CharSequence mText; // Display Text
    bcategory mCategory;

    KeypadButton(CharSequence text,bcategory category) {
        mText = text;
        mCategory = category;
    }

    public CharSequence getText() {
        return mText;
    }
}
