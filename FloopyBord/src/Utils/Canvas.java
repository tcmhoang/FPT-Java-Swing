package Utils;

import GameObject.FlopBird;
import Entity.GameObject;
import GameObject.Pipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.stream.IntStream;

public class Canvas extends JPanel implements ActionListener
{
    private Timer timer;
    private java.util.Timer genPipes;
    private FlopBird flopBird;
    private java.util.List<Pipe> pipes;
    private short score;
    private JFrame gameFrame;
    private final Dimension CANVAS_SIZE, FRAME_SIZE;
    private Control console;


    public Canvas(int w, int h)
    {
        FRAME_SIZE = new Dimension(w, h);
        CANVAS_SIZE = createGameFrame(w, h);
        setSize(CANVAS_SIZE);
        GameObject.setSize(CANVAS_SIZE);
        __init__();
    }

    private Dimension createGameFrame(int w, int h)
    {
        gameFrame = new JFrame("Nah");
        gameFrame.setLayout(null);
        gameFrame.setSize(w, h);
        gameFrame.setResizable(false);


        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
        gameFrame.setFocusable(true);

        return new Dimension(w - gameFrame.getInsets().right - gameFrame.getInsets().left
                , h - gameFrame.getInsets().top - gameFrame.getInsets().bottom);
    }


    private void __init__()
    {
        gameFrame.add(this);

        score = 0;
        setBackground(Color.BLACK);
        setOpaque(true);

        flopBird = new FlopBird();
        console = new Control(flopBird);
        gameFrame.addKeyListener( console);

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

        addKeyListener(console);
        setVisible(true);
        timer = new Timer(10, this);
        timer.start();
        genPipes.schedule(genTask, 1000, 1000);

    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        flopBird.updatePos(g2d);
        IntStream.iterate(pipes.size() - 1, i -> i >= 0, i -> i - 1).mapToObj(i -> pipes.get(i)).forEachOrdered(tmp ->
        {
            tmp.updatePos(g2d);
            if (tmp.isOffScreen())
            {
                pipes.remove(0);
                if (tmp.pass(flopBird)) score++;
            }
            if (tmp.hits(flopBird)) gameOver();
        });

        g2d.drawString(String.valueOf(score), getWidth() - 20, getHeight());
    }

    private void gameOver()
    {
        gameFrame.dispose();

        Object[] options = {"Yes, please",
                "No, thanks. I am birded enough"};

        int n = JOptionPane.showOptionDialog(this,
                "Your Final Score is " + score,
                "A Silly Question",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        if (n == 0)
        {
            //Reset all value
            gameFrame = null;

            createGameFrame(FRAME_SIZE.width, FRAME_SIZE.height);
            setSize(CANVAS_SIZE);

            //If not stop, they will continue running (Daemon Mode)
            //Although no pointer point to them
            timer.stop();
            genPipes.cancel();

            __init__();
        }
        else System.exit(0); 
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }
}
