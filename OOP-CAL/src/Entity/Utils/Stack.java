package Entity.Utils;

public interface Stack
{
    double getNumElements();
    String getElement(int e);
    void push(String operateable);
    String pop();
    void dup();
    void roll();
    void swap();
}
