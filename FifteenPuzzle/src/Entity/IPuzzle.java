package Entity;

import java.awt.*;
import java.awt.event.ActionListener;

public interface IPuzzle extends ActionListener, GameObject
{
    abstract void setDimension(Dimension x);

    abstract void createPuzzle();

    abstract void checkTilesOrder();

    abstract void reset();

    abstract void setControlPanel(IControlPanel panel);

    abstract int getMoveCounter();
}
