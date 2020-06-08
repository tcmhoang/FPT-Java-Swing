package GUI;

import Entity.View.ICalcDisplay;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculatorDisplay extends JLabel implements ICalcDisplay
{

    public CalculatorDisplay(Dimension d)
    {
        super("0");
        setFont(DEFAULT_FONT.deriveFont(Font.BOLD, 30));
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        setSize(d.width, d.height/5);
        setBorder(new EmptyBorder(0,0,0,10));

        setBackground(DEFAULT_DISPLAY);
        setForeground(Color.WHITE);

        setOpaque(true);
    }


    @Override
    public void update(String value)
    {
        setText(value);
    }

}
