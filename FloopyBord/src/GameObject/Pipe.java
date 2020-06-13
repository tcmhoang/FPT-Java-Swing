package GameObject;

import Entity.GameObject;
import Entity.IBird;
import Entity.IPipe;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.ThreadLocalRandom;

public class Pipe extends GameObject implements IPipe
{
    private double x;
    private final short top;
    private final short bottom;
    private final byte w;
    private final int velocity;
    private boolean isPassed;

    public Pipe()
    {
        x = WIDTH;
        short span = (short) ThreadLocalRandom.current().nextInt(FlopBird.DIAMETER * 4, (int) (HEIGHT / 2));
        short centery = (short) ThreadLocalRandom.current().nextInt(span, (int) (HEIGHT - span));
        top = (short) (centery - span / 2);
        bottom = (short) (centery + span / 2);
        w = 30;
        velocity = 3;
    }


    @Override
    protected void draw(Graphics2D g2d)
    {
        g2d.setPaint(Color.LIGHT_GRAY);
        Rectangle2D rtgTop = new Rectangle2D.Double(x, 0, w, top);
        Rectangle2D rtgBottom = new Rectangle2D.Double(x, bottom, w, HEIGHT - top - (bottom - top));
        g2d.fill(rtgBottom);
        g2d.fill(rtgTop);
    }

    @Override
    public void updatePos(Graphics2D g2d)
    {
        draw(g2d);
        x -= velocity;
    }

    @Override
    public boolean isOffScreen()
    {
        return x < -w;
    }

    @Override
    public boolean hits(IBird flopBird)
    {
        double xB = flopBird.getX();
        double yB = flopBird.getY();
        return (yB < top || yB > bottom) && xB >= x && xB <= x + w;
    }

    @Override
    public boolean pass(IBird bird)
    {
        return x < bird.getX() + w;
    }
}
