package Model;

import Entity.IControlPanel;
import Entity.IPuzzle;
import Entity.ITile;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Puzzle extends JPanel implements IPuzzle
{
    private ITile[][] tiles;
    private ITile emptySlot;
    private Dimension dimension;
    private int moveCounter;
    private boolean gameOver;
    private boolean isPlayerTurn;
    private IControlPanel controlPanel;

    public Puzzle(Dimension x)
    {
        dimension = x;
        setSize(500, 500);

    }

    @Override
    public void setDimension(Dimension x)
    {
        dimension = x;
    }

    @Override
    public void createPuzzle()
    {
        if (controlPanel == null) throw new NullPointerException("Do not set value for control panel");
        setLayout(new GridLayout(dimension.width, dimension.height, 10, 10));
        tiles = new Tile[dimension.width][dimension.height];
        createSolution();
        emptySlot = tiles[dimension.width - 1][dimension.height - 1];
        scramble();
        createNewPlayableState();
    }

    private void scramble()
    {
        isPlayerTurn = false;
        shuffle(100, null);
        moveCounter = 0;
    }

    private void shuffle(int times, ITile cell)
    {
        if (times < 1) return;
        List<ITile> tmp = getAdjacentTiles(emptySlot);
        if (cell != null) tmp.remove(cell);
        ITile preCell = tmp.get(ThreadLocalRandom.current().nextInt(0, tmp.size()));
        ITile shiftedCell = emptySlot;
        shiftTile(preCell);
        shuffle(--times, shiftedCell);
    }

    private void shiftTile(ITile tile)
    {

        if (!tile.getText().equals("empty"))
        {
            ITile emptyCell = getEmptyTileFrom(tile);
            if (emptyCell != null)
            {
                tile.changeState(emptyCell);
                moveCounter++;
                emptySlot = tile;
            }
        }
    }

    private ITile getEmptyTileFrom(ITile tile)
    {

        return getAdjacentTiles(tile).stream().parallel().filter(t -> t.getText().equals("empty")).findAny().orElse(null);
    }


    private List<ITile> getAdjacentTiles(ITile tile)
    {
        List<ITile> res = new LinkedList<>();
        byte col = tile.getCol();
        byte row = tile.getRow();

        if (row > 0) res.add(tiles[row - 1][col]);
        if (row < dimension.width - 1) res.add(tiles[row + 1][col]);
        if (col > 0) res.add(tiles[row][col - 1]);
        if (col < dimension.height - 1) res.add(tiles[row][col + 1]);

        return res;

    }

    private void createSolution()
    {
        for (byte i = 0; i < dimension.width; i++)
            for (byte j = 0; j < dimension.height; j++)
            {
                Tile cell = new Tile(i, j);
                cell.setSize(500 / tiles.length, 500 / tiles.length);
                cell.addActionListener(this);
                tiles[i][j] = cell;
                add(cell);
            }
        tiles[dimension.width - 1][dimension.height - 1].setText("empty");
    }

    private void createNewPlayableState()
    {
        moveCounter = 0;
        gameOver = false;
        isPlayerTurn = true;
    }

    @Override
    public void reset()
    {
        moveCounter = 0;
        controlPanel.updateMove(getMoveCounter());
        Tile.resetVal();
        removeAll();
        revalidate();
        repaint();
    }


    @Override
    public void checkTilesOrder()
    {
        if (!(isPlayerTurn || tiles[tiles.length - 1][tiles[0].length - 1].getText().equals("empty"))) return;
        List<String> tmp;
        if ((tmp = Arrays.stream(tiles).flatMap(Arrays::stream).map(ITile::getText)
                .collect(Collectors.toList()))
                .stream().sorted().collect(Collectors.toList()).equals(tmp))
        {
            gameOver = true;

            controlPanel.stopStopWatch();

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
                createNewPlayableState();

                controlPanel.resetElapsedSecs();
                controlPanel.updateMove(getMoveCounter());

                controlPanel.stopStopWatch();
                controlPanel.startRecordTime();
            }
        }
    }

    @Override
    public void setControlPanel(IControlPanel panel)
    {
        controlPanel = panel;
    }

    @Override
    public int getMoveCounter()
    {
        return moveCounter;
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() instanceof Tile)
        {
            if (!gameOver)
            {
                shiftTile((Tile) e.getSource());
                controlPanel.updateMove(getMoveCounter());
                checkTilesOrder();
            }
        }
    }
}
