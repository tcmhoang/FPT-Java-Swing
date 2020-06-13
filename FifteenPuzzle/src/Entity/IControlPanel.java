package Entity;

public interface IControlPanel extends GameObject
{
    String FORMAT_ELAPSE = "Elapsed: %d sec";
    String FORMAT_MOVE_COUNT = "Move count: %d";

    void updateMove(int steps);

    void startRecordTime();

    void stopStopWatch();

    void resetElapsedSecs();

}
