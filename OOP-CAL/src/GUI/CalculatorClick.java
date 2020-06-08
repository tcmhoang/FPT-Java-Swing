package GUI;

import Entity.View.ICharInterpreter;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;

public class CalculatorClick extends JPanel implements ICharInterpreter
{
    private CalcButton[] topFuncButtons;
    private CalcButton[] bottomFuncButtons;

    private GridBagConstraints gridLayout;


    public CalculatorClick()
    {
        super(new GridBagLayout());

        gridLayout = new GridBagConstraints();
        gridLayout.fill = GridBagConstraints.HORIZONTAL;

        setSize(500,500);

        __init__buttons();
        __config__loc__buttons();

    }


    private void __init__buttons()
    {
        topFuncButtons = new CalcButton[]{
                new CalcButton("MC"),
                new CalcButton("MR"),
                new CalcButton("M+"),
                new CalcButton("M-"),
                new CalcButton("%"),
                new CalcButton("√")};
        bottomFuncButtons = new CalcButton[19];
        IntStream.rangeClosed(0, 9).forEachOrdered(i -> bottomFuncButtons[i] = new CalcButton(String.valueOf(i)));

        bottomFuncButtons[10] = new CalcButton("±");
        bottomFuncButtons[11] = new CalcButton(".");
        bottomFuncButtons[12] = new CalcButton("=");
        bottomFuncButtons[13] = new CalcButton("+");
        bottomFuncButtons[14] = new CalcButton("-");
        bottomFuncButtons[15] = new CalcButton("×");
        bottomFuncButtons[16] = new CalcButton("÷");
        bottomFuncButtons[17] = new CalcButton("1⁄x");
        bottomFuncButtons[18] = new CalcButton("Clear");

    }


    private void __config__loc__buttons()
    {
        //header (row 0)
        gridLayout.gridwidth = 1;
        IntStream.iterate(0, i -> i < topFuncButtons.length, i -> ++i).forEachOrdered(i ->
        {
            gridLayout.gridx = 5 * i; // 7 slots in row
            gridLayout.gridy = 0; // put in all in a row
            add(topFuncButtons[i], gridLayout);
        });
        //remain parts
        gridLayout.gridwidth = 7;
        IntStream.iterate(0, i -> i < 9, i -> ++i).forEachOrdered(i ->
        {
            int alpha = i % 3; // inorder to spread all over
            gridLayout.gridx = 7 * 2 - (7 * alpha); // 5 slots in row
            gridLayout.gridy = i / 3 + 2; // auto break a line after 3 element put in create a space for row 1
            System.out.println(gridLayout.gridx + " " +  gridLayout.gridy);
            add(bottomFuncButtons[9 - i], gridLayout); //roll down number
        });
        //After this statements
        //we has 5 row 0 -> 4
        //row 1 not initialized

        gridLayout.gridy = 5;
        gridLayout.gridx = 0;
        add(bottomFuncButtons[10], gridLayout);

        gridLayout.gridy = 5;
        gridLayout.gridx = 7;
        add(bottomFuncButtons[0], gridLayout);

        gridLayout.gridy = 5;
        gridLayout.gridx = 7 * 2;
        add(bottomFuncButtons[11], gridLayout);

        //init row 1
        gridLayout.gridy = 1;
        gridLayout.gridx = 7 * 2;
        add(bottomFuncButtons[bottomFuncButtons.length - 2], gridLayout);


        gridLayout.gridy = 1;
        gridLayout.gridx = 0;
        gridLayout.gridwidth = 14;
        add(bottomFuncButtons[bottomFuncButtons.length -1], gridLayout);

        gridLayout.gridwidth = 7; //reset default value

        IntStream.iterate(12,i -> i < 17, i -> ++i).forEachOrdered(i ->
        {
            gridLayout.gridx = 7 * 3 ;
            gridLayout.gridy = 17 - i; // populate from bottom to top
            System.out.println(bottomFuncButtons[i].getText());
            add(bottomFuncButtons[i],gridLayout);
        });
    }


    @Override
    public String getMessage()
    {
        return null;
    }

    public static void main(String[] args)
    {
        JFrame blah = new JFrame();

        blah.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        blah.setLayout(null);
//        test.setBackground(DEFAULT_DISPLAY);
//        test.setForeground(Color.DARK_GRAY);
//        CalculatorDisplay a = new CalculatorDisplay(test.getSize());
        blah.add(new CalculatorClick());
//        test.pack();
        blah.pack();
        blah.setVisible(true);
        blah.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
