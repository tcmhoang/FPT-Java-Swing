package Entity;

import java.awt.*;

public interface ITile extends GameObject
{
    static Color DEFAULT_COLOR = Color.PINK;
    static Color DEFAULT_FORE = Color.DARK_GRAY;

    abstract void changeState(ITile EmptyCell);

    abstract byte getRow();

    abstract byte getCol();

    abstract void setText(String text);

    abstract String getText();
}
