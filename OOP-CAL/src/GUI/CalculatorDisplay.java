package GUI;

import Entity.GUI.ICalcDisplay;

import javax.swing.*;
import java.awt.*;

public class CalculatorDisplay extends JLabel implements ICalcDisplay
{

    public CalculatorDisplay(Dimension d)
    {
        super("0");
        setFont(DEFAULT_FONT.deriveFont(Font.BOLD, d.height -7));
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        setSize(d.width, d.height/5);
        setOpaque(true);
    }


    @Override
    public void update(String value)
    {
        setText(value);
    }

    public static void main(String[] args)
    {
        JFrame blah = new JFrame();
        JPanel test = new JPanel();
        test.setSize(200,1000);
        blah.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        test.setLayout(null);
        test.setBackground(DEFAULT_DISPLAY);
        test.setForeground(Color.DARK_GRAY);
        CalculatorDisplay a = new CalculatorDisplay(test.getSize());
        test.add(a);
//        test.pack();
        test.setVisible(true);
        blah.add(test);
        blah.pack();
        blah.setVisible(true);
        blah.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
