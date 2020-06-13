package Entity;

public interface IPipe
{
    boolean isOffScreen();

    boolean hits(IBird bird);

    boolean pass(IBird bird);
}
