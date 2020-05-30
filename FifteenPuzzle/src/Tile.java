import javax.swing.*;

public class Tile extends JButton
{
    private final byte row;
    private final byte col;
    private static byte val;
    public Tile (byte row, byte col)
    {
        super(String.valueOf(val));
        this.row = row;
        this.col = col;
        val++;
    }

    public byte getRow()
    {
        return row;
    }

    public byte getCol()
    {
        return col;
    }

    public void changeState(Tile emptyCell)
    {
        emptyCell.setText(getText());
        setText("empty");
    }
}
