package GUI;

import Entity.View.ICalcUI;
import Entity.View.ICharInterpreter;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;

public class CalculatorClick extends JPanel implements ICharInterpreter, ICalcUI
{
    private CalcButton[] topFuncButtons;
    private CalcButton[] bottomFuncButtons;

    private JPanel pnl_topFunc;
    private JPanel pnl_botFunc;

    private GridBagConstraints gridLayout;


    public CalculatorClick(Dimension x)
    {
        super(new BorderLayout());

        setPreferredSize(new Dimension(x.width, x.height / 6 * 5));

        __init__buttons();

        __init__panels();

        __config__loc__buttons();

        __add__all__and__config();
    }

    private void __add__all__and__config()
    {
        add(pnl_topFunc, BorderLayout.NORTH);
        add(pnl_botFunc, BorderLayout.CENTER);


        pnl_topFunc.setOpaque(false);

        pnl_botFunc.setOpaque(false);

        setOpaque(false);
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

    private void __init__panels()
    {
        pnl_topFunc = new JPanel(new GridLayout(1, 6));


        Arrays.stream(topFuncButtons).forEachOrdered(b ->
        {
            pnl_topFunc.add(b);
            b.setForeground(DEFAULT_BACKGROUND_OPERATOR_BUTTON);
            b.setOpaque(false);
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
        });

        pnl_botFunc = new JPanel(new GridBagLayout());


        gridLayout = new GridBagConstraints();
        gridLayout.fill = GridBagConstraints.BOTH;
        gridLayout.weightx = 0.1;
        gridLayout.weighty = 0.1;



    }


    private void __config__loc__buttons()
    {
        //header
        IntStream.iterate(0, i -> i < topFuncButtons.length, i -> ++i).forEachOrdered(i ->
                pnl_topFunc.add(topFuncButtons[i]));

        //remain parts
        IntStream.iterate(0, i -> i < 9, i -> ++i).forEachOrdered(i ->
        {
            int alpha = i % 3; // inorder to spread all over
            gridLayout.gridx = gridLayout.gridwidth * 2 - (gridLayout.gridwidth * alpha); // 5 slots in row
            gridLayout.gridy = i / 3 + 1; // auto break a line after 3 element put in create a space for row 1
            pnl_botFunc.add(bottomFuncButtons[9 - i], gridLayout); //roll down number
        });
//        After this statements
//        we has 5 row 0 -> 4
//        row 1 not initialized

        gridLayout.gridy = 4;
        gridLayout.gridx = 0;
        pnl_botFunc.add(bottomFuncButtons[10], gridLayout);

        gridLayout.gridy = 4;
        gridLayout.gridx = 1;
        pnl_botFunc.add(bottomFuncButtons[0], gridLayout);

        gridLayout.gridy = 4;
        gridLayout.gridx = 2;
        pnl_botFunc.add(bottomFuncButtons[11], gridLayout);

        //init row 1
        gridLayout.gridy = 0;
        gridLayout.gridx = 2;
        pnl_botFunc.add(bottomFuncButtons[bottomFuncButtons.length - 2], gridLayout);


        gridLayout.gridy = 0;
        gridLayout.gridx = 0;
        gridLayout.gridwidth = 2;
        pnl_botFunc.add(bottomFuncButtons[bottomFuncButtons.length - 1], gridLayout);

        gridLayout.gridwidth = 1; //reset default value

        IntStream.iterate(12, i -> i < 17, i -> ++i).forEachOrdered(i ->
        {
            gridLayout.gridx = 3;
            gridLayout.gridy = 16 - i; // populate from bottom to top
            System.out.println(bottomFuncButtons[i].getText());
            pnl_botFunc.add(bottomFuncButtons[i], gridLayout);
        });

        //init colors
        //12 start at '=' character
        IntStream.range(0,12).forEach(i -> bottomFuncButtons[i].setBackground(DEFAULT_BACKGROUND_OPERATOR_BUTTON));
        IntStream.range(12,bottomFuncButtons.length).forEach(i -> bottomFuncButtons[i].setBackground(DEFAULT_BACKGROUND_NUMERIC_BUTTON));

    }


    @Override
    public String getMessage()
    {
        return null;
    }
}
