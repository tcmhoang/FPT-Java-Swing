package GUI;

import Entity.View.ICalcBtn;
import Entity.View.ICalcUI;

import javax.swing.*;

public class CalcButton extends JButton implements ICalcBtn
{
    private boolean isOperand;

    public CalcButton(String mc)
    {
        super(mc);

        if (mc.length() > 1) isOperand = false;
        else if (Character.isDigit(mc.charAt(0))) isOperand = true;

        setFont(DEFAULT_FONT);

        setForeground(DEFAULT_BACKGROUND_DISPLAY);

        setContentAreaFilled(false);
        setOpaque(true);
    }

    @Override
    public boolean isOperand()
    {
        return isOperand;
    }
}
