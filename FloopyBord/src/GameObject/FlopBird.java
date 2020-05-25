package GameObject;

import Entity.IBird;
import Entity.GameObject;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class FlopBird extends GameObject implements IBird
{
    private double x;
    private double y;
    private double velocity;
    private double gravity;
    private double upForce;
    protected static final int DIAMETER = 20;

    public FlopBird()
    {
        super.isInit();
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

    @Override
    public void flap()
    {
        velocity += upForce;
    }

    @Override
    public double getX()
    {
        return x;
    }

    @Override
    public double getY()
    {
        return y;
    }
}
