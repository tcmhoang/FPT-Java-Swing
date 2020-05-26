package Utils;

import Entity.IBird;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Control extends KeyAdapter
{
    private IBird bird;

    public Control(IBird bird)
    {
        this.bird = bird;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) bird.flap();
    }
}
