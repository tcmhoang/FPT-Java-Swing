package Entity.Controller;

public interface ICalculatorOperator
{
    void reset();

    void append(String data);

    String sqrt();

    String flip();

    String add();

    String subtract();

    String multiply();

    String divide();

    String mem();

    void clrMem();

    void memAdd();

    void memSub();

    String percent();

    String negate();

    boolean isAdded();

}
