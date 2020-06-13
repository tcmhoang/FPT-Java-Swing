package GUI;

import Entity.IPuzzle;
import Model.ContronPanel;
import Model.Puzzle;

import javax.swing.*;
import java.awt.*;

public class Canvas
{
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException
    {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeLater(() -> {
            JFrame test = new JFrame();
            test.setLayout(new BorderLayout());
            test.setSize(600, 750);
            test.setFocusTraversalKeysEnabled(false);
            IPuzzle p;
            test.add((Component) (p = new Puzzle(new Dimension(3, 3))), BorderLayout.CENTER);
            ContronPanel c;
            test.add(c = new ContronPanel(p), BorderLayout.SOUTH);
            p.setControlPanel(c);
            test.setVisible(true);
            test.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            test.setLocationRelativeTo(null);
            test.setFocusable(true);
        });

    }
}
