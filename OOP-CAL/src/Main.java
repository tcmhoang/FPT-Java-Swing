import Entity.View.ICalcDisplay;
import Entity.View.ICharInterpreter;
import GUI.CalculatorClick;
import GUI.CalculatorDisplay;

import Entity.View.ICalcUI;

import javax.swing.*;
import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame calcCanvas = new JFrame("Calculator");
                calcCanvas.setLayout(new BorderLayout());
                calcCanvas.setSize(500,750);

                ICalcDisplay tmp;
                ICharInterpreter btnPanel;
                calcCanvas.add((Component) (tmp = new CalculatorDisplay(calcCanvas.getSize())),BorderLayout.NORTH);
                calcCanvas.add((Component) (btnPanel = new CalculatorClick(calcCanvas.getSize())),BorderLayout.CENTER);
                btnPanel.setDisplay(tmp);

                calcCanvas.getContentPane().setBackground(ICalcUI.DEFAULT_BACKGROUND_DISPLAY);

                calcCanvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                //Must to set undecorated before making it opaque
                calcCanvas.pack();
                calcCanvas.setLocationRelativeTo(null);

                calcCanvas.setVisible(true);
            }
        });
    }
}
