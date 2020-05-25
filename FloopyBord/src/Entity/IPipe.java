package Entity;

public interface IPipe
{
    abstract boolean isOffScreen();

    abstract boolean hits(IBird bird);

    abstract boolean pass(IBird bird);
}
