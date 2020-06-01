import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseController extends MouseAdapter
{
    ControlPanel controlPanel;
    IPuzzle puzzle;
    MouseController(ControlPanel panel, IPuzzle puzzlePanel)
    {
        controlPanel = panel;
        puzzle = puzzlePanel;
    }

    public void mousePressed(MouseEvent e)
    {
        System.out.println("here");
        controlPanel.updateMove(controlPanel.puzzlePanel.getMoveCounter());
        if(controlPanel.puzzlePanel.checkTilesOrder())
            controlPanel.stopStopWatch();
    }
}
