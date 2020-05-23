import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.ThreadLocalRandom;

public class Pipe extends GameObject
{
    private double x;
    private short top;
    private short bottom;
    private byte w;
    private int velocity;

    public Pipe()
    {
        x = WIDTH;
        short span = (short) ThreadLocalRandom.current().nextInt(Bird.DIAMETER * 4, (int) (WIDTH / 2));
        short centery = (short) ThreadLocalRandom.current().nextInt(span, (int) (WIDTH - span));
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
    protected void updatePos(Graphics2D g2d)
    {
        draw(g2d);
        x -= velocity;

    }

    public boolean isOffScreen()
    {
        return x < -w;
    }

    public boolean hits(Bird bird)
    {
        double xB = bird.getX();
        double yB = bird.getY();
        if (yB < top || yB > bottom)
        {
            if (xB >= x && xB <= x + w)
                return true;
        }

        return false;
    }
}
