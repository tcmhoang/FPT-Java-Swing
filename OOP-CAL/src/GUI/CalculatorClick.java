package GUI;

import Controller.Calculator;
import Entity.Controller.ICalculatorOperator;
import Entity.View.ICalcBtn;
import Entity.View.ICalcDisplay;
import Entity.View.ICalcUI;
import Entity.View.ICharInterpreter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.stream.IntStream;

public class CalculatorClick extends JPanel implements ICharInterpreter, ICalcUI, ActionListener
{
    private CalcButton[] topFuncButtons;
    private CalcButton[] bottomFuncButtons;

    private JPanel pnl_topFunc;
    private JPanel pnl_botFunc;

    private String message;

    private GridBagConstraints gridLayout;

    private ICalcDisplay display;

    private final ICalculatorOperator calc;

    private String operator;


    public CalculatorClick(Dimension x)
    {
        super(new BorderLayout());

        setPreferredSize(new Dimension(x.width, x.height / 6 * 5));

        message = "0";
        calc = new Calculator();

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
            pnl_botFunc.add(bottomFuncButtons[i], gridLayout);
        });

        //init colors
        //12 start at '=' character
        IntStream.range(0, 12).forEach(i -> bottomFuncButtons[i].setBackground(DEFAULT_BACKGROUND_OPERATOR_BUTTON));
        IntStream.range(12, bottomFuncButtons.length).forEach(i -> bottomFuncButtons[i].setBackground(DEFAULT_BACKGROUND_NUMERIC_BUTTON));

        //Add EventListener
        Arrays.stream(topFuncButtons).forEach(e -> e.addActionListener(this));
        Arrays.stream(bottomFuncButtons).forEach(e -> e.addActionListener(this));
    }


    @Override
    public String getMessage()
    {
        if (display == null) throw new NullPointerException("Do not set value reference to ICalDisplay");
        return message;
    }

    @Override
    public void setDisplay(ICalcDisplay display)
    {
        this.display = display;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() instanceof ICalcBtn)
        {
            ICalcBtn btn = (ICalcBtn) e.getSource();
            if (btn.isOperand())
                handleNumber(btn.getText());
            else
                handleSymbol(btn.getText());
        }
        display.update(getMessage());
    }

    private void handleSymbol(String text)
    {
        switch (text)
        {
            case ".":
                handleDot();
                break;
            case "=":
                if (operator != null)
                {
                    calc.append(message);
                    mathUp();
                    resetState();
                }
                break;
            case "Clear":
                message = "0";
                resetState();
                break;
            default:
                calc.append(message);

                if (!text.matches("-|[+×÷]"))
                {
                    operator = text;
                    utilsFunc();
                    break;
                }
                
                if (operator != null)
                    if (operator.matches("-|[+×÷]"))
                    {
                        mathUp();
                        calc.append(message);
                    }

                message = "";

                operator = text;
                break;
        }
    }

    private void resetState()
    {
        operator = null;
        calc.reset();
    }


    private void mathUp()
    {
        switch (operator)
        {
            case "+":
                message = calc.add();
                break;
            case "-":
                message = calc.subtract();
                break;
            case "×":
                message = calc.multiply();
                break;
            case "÷":
                message = calc.divide();
        }
        operator = null;
    }

    private void utilsFunc()
    {
        switch (operator)
        {
            case "±":
                message = calc.negate();
                break;
            case "1⁄x":
                message = calc.flip();
                break;
            case "MR":
                message = calc.readMem();
                break;
            case "MC":
                calc.clrMem();
                break;
            case "M+":
                calc.memAdd();
                break;
            case "M-":
                calc.memSub();
                break;
            case "%":
                message = calc.percent();
                break;
            case "√":
                message = calc.sqrt();

        }
        operator = null;
    }

    private void handleDot()
    {
        if (message.indexOf(".") == -1 && message.length() > 0)
            message = message + ".";
    }

    private void handleNumber(String text)
    {
        if (message.toString().equals("0"))
            if (!text.equals("0"))
                message = "";
            else return;

        message = message + text;
    }
}
