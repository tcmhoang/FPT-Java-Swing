package Entity;

public interface IControlPanel extends GameObject
{
    final String FORMAT_ELAPSE = "Elapsed: %d sec";
    final String FORMAT_MOVE_COUNT = "Move count: %d";

    abstract void updateMove(int steps);

    abstract void startRecordTime();

    abstract void stopStopWatch();

    abstract void resetElapsedSecs();

}
