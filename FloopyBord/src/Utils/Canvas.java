package Utils;

import GameObject.FlopBird;
import Entity.GameObject;
import GameObject.Pipe;

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
    private FlopBird flopBird;
    private java.util.List<Pipe> pipes;
    private short score;


    public Canvas(Dimension s)
    {
        setSize(s);
        GameObject.setSize(s);
        __init__();
    }

    private void __init__()
    {
        score = 0;
        setBackground(Color.BLACK);
        setOpaque(true);

        flopBird = new FlopBird();
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

        g2d.drawString(String.valueOf(score),getWidth()-20,getHeight());
    }

    private void gameOver()
    {
        Object[] options = {"Yes, please",
                "No, thanks. I am birded enough"};

        int n = JOptionPane.showOptionDialog(this,
                "Your Final Score is " + score ,
                "A Silly Question",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        //TODO: close windows and start other session
        if (n == 0) {
            __init__();
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) flopBird.flap();
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
    public void keyReleased(KeyEvent e)
    {

    }
}
