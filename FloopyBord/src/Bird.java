import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Bird extends GameObject
{
    private double x;
    private double y;
    private double velocity;
    private double gravity;
    private double upForce;
    protected static final int DIAMETER = 20;

    public Bird()
    {
        isInit();
        x = 25;
        y = HEIGHT / 2;
        velocity = 0;
        gravity = 0.7;
        upForce = -(gravity * 25);
    }

    @Override
    protected void draw(Graphics2D g)
    {
        g.setPaint(Color.WHITE);
        Ellipse2D bird = new Ellipse2D.Double(x, y, DIAMETER, DIAMETER);
        g.fill(bird);
    }

    @Override
    public void updatePos(Graphics2D g)
    {
        draw(g);
        velocity += gravity;
        velocity *= 0.9;
        y += velocity;
        if (y < 0 || y > HEIGHT - DIAMETER)
        {
            y = y < 0 ? 0 : HEIGHT - DIAMETER;
            velocity = y == HEIGHT - DIAMETER ? 0 : velocity;
        }
    }

    public void flap()
    {
        velocity += upForce;
    }

    protected double getX()
    {
        return x;
    }

    protected double getY()
    {
        return y;
    }
}
