import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Puzzle extends JPanel implements IPuzzle
{
    private Tile[][] tiles;
    private Tile emptySlot;
    private Dimension dimension;
    private int moveCounter;
    private boolean gameOver;
    private boolean isPlayerTurn;

    public Puzzle(Dimension x)
    {
        dimension = x;
        setSize(500, 500);
//        createPuzzle();
    }

    @Override
    public void setDimension(Dimension x)
    {
        dimension = x;
    }

    @Override
    public void createPuzzle()
    {
        setLayout(new GridLayout(dimension.width, dimension.height, 10, 10));
        tiles = new Tile[dimension.width][dimension.height];
        createSolution();
        emptySlot = tiles[dimension.width - 1][dimension.height - 1];
        scramble();
        moveCounter = 0;
        gameOver = false;
        isPlayerTurn =true;
    }

    @Override
    public void reset()
    {
        Tile.resetVal();
        removeAll();
        revalidate();
        repaint();
    }

    private void createSolution()
    {
        for (byte i = 0; i < dimension.width; i++)
            for (byte j = 0; j < dimension.height; j++)
            {
                Tile cell = new Tile(i, j);
                cell.setSize(500/tiles.length, 500/tiles.length);
                cell.addActionListener(this);
                tiles[i][j] = cell;
                add(cell);
            }
        tiles[dimension.width - 1][dimension.height - 1].setText("empty");
    }

    private void shiftTile(Tile tile)
    {

        if (!tile.getText().equals("empty"))
        {
            Tile emptyCell = getEmptyTileFrom(tile);
            if (emptyCell != null)
            {
                tile.changeState(emptyCell);
                moveCounter++;
                emptySlot = tile;
                checkTilesOrder();
            }
        }
    }

    @Override
    public void checkTilesOrder()
    {
        if(!isPlayerTurn) return ;
        List<String> tmp;
        if (!tiles[tiles.length - 1][tiles[0].length - 1].getText().equals("empty"))
            return ;
        if ((tmp = Arrays.stream(tiles).flatMap(Arrays::stream).map(AbstractButton::getText).collect(Collectors.toCollection(LinkedList::new)))
                .stream().sorted().collect(Collectors.toList()).equals(tmp))
        {
            gameOver = true;

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

            if (n == 0)
            {
                scramble();
                gameOver = false;
                return;
            }
            ControlPanel.stopStopWatch();
        }
    }


    private void scramble()
    {
        isPlayerTurn = false;
        shuffle(100, null);
        moveCounter = 0;
    }

    private void shuffle(int times, Tile cell)
    {
        if (times < 0) return;
        List<Tile> tmp = getAdjacentTiles(emptySlot);
        if (cell != null) tmp.remove(cell);
        Tile preCell = tmp.get(ThreadLocalRandom.current().nextInt(0, tmp.size()));
        Tile shiftedCell = emptySlot;
        shiftTile(preCell);
        shuffle(--times, shiftedCell);
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
            if (!gameOver)
            {
                shiftTile((Tile) e.getSource());
                ControlPanel.updateMove(getMoveCounter());
            }
        }
    }

    @Override
    public int getMoveCounter()
    {
        return moveCounter;
    }
}
