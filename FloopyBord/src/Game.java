import Utils.Canvas;

import javax.swing.*;
import java.awt.*;

public class Game
{
    public static void main(String[] args)
    {
        var gameFrame = new JFrame("Nah");
        gameFrame.setLayout(null);
        gameFrame.setSize(500, 500);
        gameFrame.setResizable(false);


        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
        gameFrame.setFocusable(true);

        Dimension tmp = gameFrame.getSize();
        int w = tmp.width - gameFrame.getInsets().right - gameFrame.getInsets().left;
        int h = tmp.height - gameFrame.getInsets().top - gameFrame.getInsets().bottom;
        Utils.Canvas canvas;
        gameFrame.add(canvas = new Canvas(new Dimension(w, h)));
        gameFrame.addKeyListener(canvas);
    }
}
