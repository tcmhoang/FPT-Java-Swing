package Model;

import Entity.ITile;

import javax.swing.*;

public class Tile extends JButton implements ITile
{
    private static byte val = 1;

    private final byte row;
    private final byte col;

    public Tile(byte row, byte col)
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
    public void setText(String text)
    {
        super.setText(text);
    }

    @Override
    public String getText()
    {
        return super.getText();
    }

    @Override
    public void changeState(ITile emptyCell)
    {
        if (!(emptyCell instanceof JButton))
            throw new IllegalArgumentException("PASS-IN ARGUMENT is not JButton child's type");
        JButton convertedEmptyCell = (JButton) emptyCell;
        convertedEmptyCell.setText(getText());
        setText("empty");
        convertedEmptyCell.setVisible(true);
        setVisible(false);
    }

    public static void resetVal()
    {
        val = 1;
    }
}
