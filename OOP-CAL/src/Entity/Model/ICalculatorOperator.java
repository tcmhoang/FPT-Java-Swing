package Entity.Model;

public interface ICalculatorOperator
{
    void reset();
    void clear();
    double get();
    void append(String data);
    void del(String data);
    void sqrt();
    void percent();
    void div_by_one();
    void swap();
}
