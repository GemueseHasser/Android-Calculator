package de.gemuesehasser.test.constant;

import de.gemuesehasser.test.R;

public enum CalcInputButtonType {
    
    BUTTON_0(R.id.button0),
    BUTTON_1(R.id.button1),
    BUTTON_2(R.id.button2),
    BUTTON_3(R.id.button3),
    BUTTON_4(R.id.button4),
    BUTTON_5(R.id.button5),
    BUTTON_6(R.id.button6),
    BUTTON_7(R.id.button7),
    BUTTON_8(R.id.button8),
    BUTTON_9(R.id.button9),
    BUTTON_PLUS(R.id.buttonPlus),
    BUTTON_MINUS(R.id.buttonMinus),
    BUTTON_MULTIPLY(R.id.buttonMultiply),
    BUTTON_DIVIDE(R.id.buttonDivide),
    BUTTON_COMMA(R.id.buttonComma),
    BUTTON_BRACKET_O(R.id.buttonBracketO),
    BUTTON_BRACKET_C(R.id.buttonBracketC),
    BUTTON_SIN(R.id.buttonSin),
    BUTTON_COS(R.id.buttonCos),
    BUTTON_TAN(R.id.buttonTan),
    BUTTON_PI(R.id.buttonPi),
    BUTTON_LOG(R.id.buttonLog),
    BUTTON_LN(R.id.buttonLn),
    BUTTON_SQRT(R.id.buttonSqrt),
    BUTTON_POW(R.id.buttonPow),
    BUTTON_ARCSIN(R.id.buttonArcsin),
    BUTTON_ARCCOS(R.id.buttonArccos),
    BUTTON_ARCTAN(R.id.buttonArctan),
    BUTTON_E(R.id.buttonE);


    private final int id;


    CalcInputButtonType(final int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }
}
