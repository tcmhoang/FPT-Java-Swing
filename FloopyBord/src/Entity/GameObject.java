package Entity;

import java.awt.*;

public abstract class GameObject
{
    protected static double WIDTH, HEIGHT;

    public static void isInit()
    {
        if (WIDTH == 0 || WIDTH == 0) throw new NullPointerException("DID NOT SET ABSOLUTE SIZE FOR GAME OBJECT");
    }

    public static void setSize(Dimension s)
    {
        WIDTH = s.width;
        HEIGHT = s.height;
    }

    protected abstract void draw(Graphics2D g2d);

    protected abstract void updatePos(Graphics2D g2d);

}
