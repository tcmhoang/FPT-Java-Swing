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
        setFont(DEFAULT_MONO_FONT.deriveFont(Font.BOLD, d.height /17));
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        setPreferredSize(new Dimension(d.width, d.height / 6));
        setBorder(new EmptyBorder(0, 0, 0, d.width / 70));

        setForeground(DEFAULT_DISPLAY_TEXT_COLOR);

        setOpaque(false);

    }


    @Override
    public void update(String value)
    {
        setText(value);
    }

}
