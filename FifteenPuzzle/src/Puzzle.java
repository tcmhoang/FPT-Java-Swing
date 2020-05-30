import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class Puzzle extends JPanel implements ActionListener
{
    private Tile[][] tiles;

    public Puzzle(Dimension x)
    {
        setLayout(new GridLayout(x.width, x.height, 10, 10));
        setSize(500, 500);
        setVisible(true);
        tiles = new Tile[x.width][x.height];
        createPuzzle(x);
    }

    private void createPuzzle(Dimension x)
    {
        for (byte i = 0; i < x.width; i++)
        {
            for (byte j = 0; j < x.height; j++)
            {
                Tile cell = new Tile(i, j);
                cell.setSize(20,20);
                cell.addActionListener(this);
                tiles[i][j] = cell;
                add(cell);
            }
        }
        tiles[x.width - 1][x.height - 1].setText("empty");
    }

    private void shiftTile(Tile tile)
    {

        if (!tile.getText().equals("empty"))
        {
            Tile emptyCell = getEmptyTileFrom(tile);
            if (emptyCell != null)
            {
                System.out.println("aha");

                tile.changeState(emptyCell);
                checkTilesOrder();
            }
        }

    }

    private void checkTilesOrder()
    {
        if(!tiles[tiles.length-1][tiles[0].length-1].getText().equals("empty"))
        {
            return;
        }
        if(Arrays.stream(tiles).flatMap(Arrays::stream).map(AbstractButton::getText).collect(Collectors.toCollection(LinkedList::new))
                 .stream().sorted().collect(Collectors.toList()).equals(tiles))
        {
            return;
        }
        // Puzzle is solved, offers to scramble it
        scramble();

    }

    //TODO: FINISH THIS FUNC
    private void scramble()
    {

    }

    private Tile getEmptyTileFrom(Tile tile)
    {

        return getAdjacentTiles(tile).stream().parallel().filter(t -> t.getText().equals("empty")).findAny().orElse(null);
    }

    private List<Tile> getAdjacentTiles(Tile tile)
    {
        List<Tile> res = new LinkedList<>();
        byte col = tile.getCol();
        byte row = tile.getRow();

        byte maxSize = (byte) tiles.length;

        System.out.println(row + " " + col + " " + maxSize);
        if (row > 0) res.add(tiles[row - 1][col]);
        if (row < maxSize - 1) res.add(tiles[row + 1][col]);
        if (col > 0) res.add(tiles[row][col - 1]);
        if (col < maxSize - 1) res.add(tiles[row][col + 1]);

        System.out.println(res.size());
        return res;

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() instanceof Tile)
        {
            shiftTile((Tile) e.getSource());
        }
    }

    public static void main(String[] args)
    {
        JFrame test = new JFrame();
        test.setLayout(null);
        test.setSize(600,600);
        test.add(new Puzzle(new Dimension(3,3)));
        test.setVisible(true);
        test.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
