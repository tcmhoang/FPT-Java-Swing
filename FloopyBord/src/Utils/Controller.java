package Utils;

import Entity.IBird;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter
{
    private final IBird bird;

    public Controller(IBird bird)
    {
        this.bird = bird;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) bird.flap();
    }
}
