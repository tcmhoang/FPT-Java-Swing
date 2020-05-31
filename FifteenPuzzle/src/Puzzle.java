import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Puzzle extends JPanel implements ActionListener, IPuzzle
{
    private Tile[][] tiles;
    private Tile emptySlot;

    public Puzzle(Dimension x)
    {
        setLayout(new GridLayout(x.width, x.height, 10, 10));
        setSize(500, 500);
        setVisible(true);
        createPuzzle(x);
    }

    @Override
    public void createPuzzle(Dimension x)
    {
        tiles = new Tile[x.width][x.height];
        createSolution(x);
        emptySlot = tiles[x.width - 1][x.height - 1];
        scramble();
    }

    private void createSolution(Dimension x)
    {
        for (byte i = 0; i < x.width; i++)
        {
            for (byte j = 0; j < x.height; j++)
            {
                Tile cell = new Tile(i, j);
                cell.setSize(20, 20);
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
                tile.changeState(emptyCell);
                emptySlot = tile;
                checkTilesOrder();
            }
        }
    }

    private void checkTilesOrder()
    {
        List<String> tmp;
        if (!tiles[tiles.length - 1][tiles[0].length - 1].getText().equals("empty"))
            return;
        if ((tmp = Arrays.stream(tiles).flatMap(Arrays::stream).map(AbstractButton::getText).collect(Collectors.toCollection(LinkedList::new)))
                .stream().sorted().collect(Collectors.toList()).equals(tmp))
        {
            Object[] options = {"Yes, please",
                    "No, thanks"};

            int n = JOptionPane.showOptionDialog(this,
                    "Congrats! You win",
                    "Congratulation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (n == 0) scramble();
        }
    }


    private void scramble()
    {
        shuffler(100, null);
    }

    private void shuffler(int times, Tile cell)
    {
        if (times < 0) return;
        List<Tile> tmp = getAdjacentTiles(emptySlot);
        if (cell != null) tmp.remove(cell);
        Tile preCell = tmp.get(ThreadLocalRandom.current().nextInt(0, tmp.size()));
        Tile shiftedCell = emptySlot;
        shiftTile(preCell);
        shuffler(--times, shiftedCell);
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

        if (row > 0) res.add(tiles[row - 1][col]);
        if (row < maxSize - 1) res.add(tiles[row + 1][col]);
        if (col > 0) res.add(tiles[row][col - 1]);
        if (col < maxSize - 1) res.add(tiles[row][col + 1]);

        return res;

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() instanceof Tile)
        {
            shiftTile((Tile) e.getSource());
        }
    }

    public static void main(String[] args)
    {
        JFrame test = new JFrame();
        test.setLayout(new BorderLayout());
        test.setSize(600, 600);
        test.add(new Puzzle(new Dimension(3, 3)),BorderLayout.CENTER);
        test.setVisible(true);
        test.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
