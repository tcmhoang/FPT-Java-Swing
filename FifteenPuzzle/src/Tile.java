import javax.swing.*;
import java.awt.*;

public class Tile extends JButton implements ITile
{
    private final byte row;
    private final byte col;
    private static byte val = 1;
    private static Color DEFAULT_COLOR = Color.PINK;
    private static Color DEFAULT_FORE = Color.DARK_GRAY;
    private static final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 20);

    public Tile (byte row, byte col)
    {
        super(String.valueOf(val));
        setFont(DEFAULT_FONT);
        this.row = row;
        this.col = col;
        val++;
        setBackground(DEFAULT_COLOR);
        setForeground(DEFAULT_FORE);
        setContentAreaFilled(false);
        setOpaque(true);
    }

    @Override
    public byte getRow()
    {
        return row;
    }

    @Override
    public byte getCol()
    {
        return col;
    }

    @Override
    public void changeState(Tile emptyCell)
    {
        emptyCell.setText(getText());
        setText("empty");
        emptyCell.setVisible(true);
        setVisible(false);
    }

    public static void resetVal()
    {
        val = 1;
    }
}
