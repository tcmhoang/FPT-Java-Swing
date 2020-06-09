package GUI;

import Entity.View.ICalcUI;

import javax.swing.*;

public class CalcButton extends JButton implements ICalcUI
{
    public CalcButton(String mc)
    {
        super(mc);
        setFont(DEFAULT_FONT);

        setForeground(DEFAULT_BACKGROUND_DISPLAY);

        setContentAreaFilled(false);
        setOpaque(true);
    }
}
