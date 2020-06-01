import java.awt.*;
import java.awt.event.ActionListener;

public interface IPuzzle extends ActionListener
{
    abstract void setDimension(Dimension x);
    abstract void createPuzzle();

    abstract int getMoveCounter();
    abstract void checkTilesOrder();
    abstract void reset();
}
