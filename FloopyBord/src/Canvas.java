import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.stream.IntStream;

public class Canvas extends JPanel implements ActionListener, KeyListener
{
    private Timer timer;
    private java.util.Timer genPipes;
    private Bird bird;
    private java.util.List<Pipe> pipes;

    public Canvas(Dimension s)
    {
        setSize(s);
        GameObject.setSize(s);
        __init__();
    }

    private void __init__()
    {
        setBackground(Color.BLACK);
        setOpaque(true);

        bird = new Bird();
        pipes = new LinkedList();
        pipes.add(new Pipe());
        genPipes = new java.util.Timer(true);
        TimerTask genTask = new TimerTask()
        {
            @Override
            public void run()
            {
                pipes.add(new Pipe());
            }
        };

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocus();

        addKeyListener(this);
        setVisible(true);
        timer = new Timer(10, this);
        timer.start();
        genPipes.schedule(genTask, 1000, 1000);

    }

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
        Canvas canvas;
        gameFrame.add(canvas = new Canvas(new Dimension(w, h)));
        gameFrame.addKeyListener(canvas);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        bird.updatePos(g2d);
        IntStream.iterate(pipes.size() - 1, i -> i >= 0, i -> i - 1).mapToObj(i -> pipes.get(i)).forEachOrdered(tmp ->
        {
            tmp.updatePos(g2d);
            if (tmp.isOffScreen()) pipes.remove(0);
            if(tmp.hits(bird)) System.out.println("HIT");
        });
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) bird.flap();
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
