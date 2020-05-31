public interface ITile
{
    abstract byte getRow();
    abstract byte getCol();
    abstract void changeState(Tile EmptyCell);
}
