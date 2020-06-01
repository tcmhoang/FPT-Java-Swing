import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class ControlPanel extends JPanel
{
    private final String FORMAT_ELAPSE;
    private final String FORMAT_MOVE_COUNT;

    private JLabel txt_duration;
    private JLabel txt_moveCount;
    private JPanel txt_puzzleDimSelector;
    private JButton btn_newGame;
    private JComboBox ddl_gameTileSelector;
    private Timer stopWatch;

    protected IPuzzle puzzlePanel;

    private int sec;
    private boolean isClickedFirstTime;


    public ControlPanel(IPuzzle puzzle)
    {
        FORMAT_ELAPSE = "Elapsed: %d sec";
        FORMAT_MOVE_COUNT = "Move count: %d";

        puzzlePanel = puzzle;
        isClickedFirstTime = true;

        setPreferredSize(new Dimension(100, 100));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        __init__panelSelDim__();
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
    }

    private void __add_all__()
    {
        this.add(txt_duration);
        this.add(txt_moveCount);
        this.add(txt_puzzleDimSelector);
        this.add(btn_newGame);

        txt_duration.setAlignmentX(CENTER_ALIGNMENT);
        txt_moveCount.setAlignmentX(CENTER_ALIGNMENT);
        txt_puzzleDimSelector.setAlignmentX(CENTER_ALIGNMENT);
        btn_newGame.setAlignmentX(CENTER_ALIGNMENT);
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
                int size = Integer.parseInt((String.valueOf(ddl_gameTileSelector.getSelectedItem())).substring(0,1));
                puzzlePanel.reset();
                puzzlePanel.setDimension(new Dimension(size,size));
                puzzlePanel.createPuzzle();
                if(!isClickedFirstTime)
                {
                    stopStopWatch();
                }
                startRecordTime();
                isClickedFirstTime = false;

            }
        });
    }

    protected void startRecordTime()
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
        }, 1000,1000);
    }

    protected void resetElapsedSecs()
    {
        sec = 0;
        updateTime();
    }

    protected void stopStopWatch()
    {
        stopWatch.cancel();
        stopWatch.purge();
    }

    private void __init__panelSelDim__()
    {
        txt_puzzleDimSelector = new JPanel(new FlowLayout());
        txt_puzzleDimSelector.setSize(getWidth(), 20);
        JLabel tmp = new JLabel("Size: ");
        txt_puzzleDimSelector.add(tmp);
        String[] sizes = {"2X2", "3x3", "4x4", "5x5"};
        ddl_gameTileSelector = new JComboBox(sizes);
        ddl_gameTileSelector.setSelectedIndex(1);
        txt_puzzleDimSelector.add(ddl_gameTileSelector);

    }

    private void updateTime()
    {
        txt_duration.setText(String.format(FORMAT_ELAPSE, sec));
    }

    protected void updateMove(int steps)
    {
        txt_moveCount.setText(String.format(FORMAT_MOVE_COUNT, steps));
    }

}
