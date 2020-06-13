package Entity;

import java.awt.*;
import java.awt.event.ActionListener;

public interface IPuzzle extends ActionListener, GameObject
{
    void setDimension(Dimension x);

    void createPuzzle();

    void checkTilesOrder();

    void reset();

    void setControlPanel(IControlPanel panel);

    int getMoveCounter();
}
