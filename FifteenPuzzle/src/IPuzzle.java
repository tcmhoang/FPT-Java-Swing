import java.awt.*;
import java.awt.event.ActionListener;

public interface IPuzzle extends ActionListener
{
    abstract void createPuzzle();
    abstract int getMoveCounter();
    abstract void setDimension(Dimension x);
    abstract void reset();
    abstract boolean checkTilesOrder();
}
