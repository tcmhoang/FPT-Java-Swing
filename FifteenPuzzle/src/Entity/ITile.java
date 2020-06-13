package Entity;

import java.awt.*;

public interface ITile extends GameObject
{
    Color DEFAULT_COLOR = Color.PINK;
    Color DEFAULT_FORE = Color.DARK_GRAY;

    void changeState(ITile EmptyCell);

    byte getRow();

    byte getCol();

    void setText(String text);

    String getText();
}
