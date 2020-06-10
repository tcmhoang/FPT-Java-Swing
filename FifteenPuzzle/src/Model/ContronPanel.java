package Model;

import Entity.IControlPanel;
import Entity.IPuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class ContronPanel extends JPanel implements IControlPanel
{


    private JLabel txt_duration;
    private JLabel txt_moveCount;
    private JPanel panel_puzzleDimSelector;
    private JButton btn_newGame;
    private JComboBox ddl_gameTileSelector;
    private Timer stopWatch;

    private IPuzzle puzzlePanel;

    private int sec;
    private boolean isClickedFirstTime;


    public ContronPanel(IPuzzle puzzle)
    {
        puzzlePanel = puzzle;
        isClickedFirstTime = true;

        setPreferredSize(new Dimension(100, 100));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        __init__();
        __add_all__();
    }

    private void __init__()
    {
        txt_duration = new JLabel();
        txt_moveCount = new JLabel();
        btn_newGame = new JButton();

        updateMove(0);
        updateTime();

        __init__panelSelDim__();
        initNewGameBtn();

        selFont();
    }

    private void __init__panelSelDim__()
    {
        panel_puzzleDimSelector = new JPanel(new FlowLayout());
        panel_puzzleDimSelector.setSize(getWidth(), 20);

        JLabel tmp = new JLabel("Size: ");

        panel_puzzleDimSelector.add(tmp);

        String[] sizes = {"2x2","2x3", "3x3", "3x4", "4x4", "4x5", "5x5"};
        ddl_gameTileSelector = new JComboBox(sizes);
        ddl_gameTileSelector.setSelectedIndex(2);

        panel_puzzleDimSelector.add(ddl_gameTileSelector);
    }

    private void initNewGameBtn()
    {
        btn_newGame.setText("New Game");
        btn_newGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                sec = 0;
                updateTime();

                String[] size = String.valueOf(ddl_gameTileSelector.getSelectedItem()).split("\\D+");
                puzzlePanel.reset();
                puzzlePanel.setDimension(new Dimension(Integer.parseInt(size[0]), Integer.parseInt(size[1])));
                puzzlePanel.createPuzzle();

                if (!isClickedFirstTime)
                    stopStopWatch();
                startRecordTime();

                isClickedFirstTime = false;

            }
        });
    }


    private void selFont()
    {
        Font UIFont = DEFAULT_FONT.deriveFont(Font.HANGING_BASELINE, 15);

        txt_duration.setFont(UIFont);
        txt_moveCount.setFont(UIFont);
        btn_newGame.setFont(UIFont);

        Arrays.stream(panel_puzzleDimSelector.getComponents()).forEach(v -> v.setFont(UIFont));
    }

    private void __add_all__()
    {
        this.add(txt_duration);
        this.add(txt_moveCount);
        this.add(panel_puzzleDimSelector);
        this.add(btn_newGame);


        txt_duration.setAlignmentX(CENTER_ALIGNMENT);
        txt_moveCount.setAlignmentX(CENTER_ALIGNMENT);
        panel_puzzleDimSelector.setAlignmentX(CENTER_ALIGNMENT);
        btn_newGame.setAlignmentX(CENTER_ALIGNMENT);
    }

    @Override
    public void startRecordTime()
    {
        stopWatch = new Timer();
        stopWatch.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                sec++;
                updateTime();
            }
        }, 1000, 1000);
    }

    @Override
    public void resetElapsedSecs()
    {
        sec = 0;
        updateTime();
    }

    @Override
    public void stopStopWatch()
    {
        stopWatch.cancel();
        stopWatch.purge();
    }

    private void updateTime()
    {
        txt_duration.setText(String.format(FORMAT_ELAPSE, sec));
    }

    @Override
    public void updateMove(int steps)
    {
        txt_moveCount.setText(String.format(FORMAT_MOVE_COUNT, steps));
    }

}
