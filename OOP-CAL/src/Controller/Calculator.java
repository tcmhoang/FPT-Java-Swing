package Controller;

import Entity.Controller.ICalculatorOperator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;
import java.util.regex.Pattern;

public class Calculator implements ICalculatorOperator
{
    private Stack<BigDecimal> operand;
    private BigDecimal remNum;
    private Pattern pattern;

    private int size;
    private int oldSize;


    public Calculator()
    {
        remNum = BigDecimal.ZERO;
        operand = new Stack<>();
        pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    }

    @Override
    public void reset()
    {
        operand.clear();
    }

    @Override
    public void append(String data)
    {
        oldSize = size;
        if (pattern.matcher(data).matches())
            operand.add(new BigDecimal(data));
        size = operand.size();
    }

    @Override
    public String sqrt()
    {
        BigDecimal tmp = operand.pop();

        if (tmp.compareTo(new BigDecimal("0")) == -1)
        {
            return "0"; //ERROR return 0
        }
        return String.valueOf(Math.sqrt(tmp.doubleValue()));
    }

    @Override
    public String flip()
    {
        BigDecimal tmp = operand.pop();
        if (tmp.equals(0))
            return "0"; //ERROR return 0
        return String.valueOf(BigDecimal.ONE.divide(tmp,10,RoundingMode.HALF_EVEN));
    }

    @Override
    public String add()
    {
        BigDecimal tmp1 = operand.pop();
        BigDecimal tmp2 = operand.pop();

        return String.valueOf(tmp1.add(tmp2));
    }

    @Override
    public String subtract()
    {
        BigDecimal tmp1 = operand.pop();
        BigDecimal tmp2 = operand.pop();

        return String.valueOf(tmp2.subtract(tmp1));
    }

    @Override
    public String multiply()
    {
        BigDecimal tmp1 = operand.pop();
        BigDecimal tmp2 = operand.pop();

        return String.valueOf(tmp1.multiply(tmp2));
    }

    @Override
    public String divide()
    {
        BigDecimal tmp1 = operand.pop();
        BigDecimal tmp2 = operand.pop();

        return String.valueOf(tmp2.divide(tmp1,10,RoundingMode.HALF_EVEN));
    }

    @Override
    public String readMem()
    {
        return String.valueOf(remNum);
    }

    @Override
    public void clrMem()
    {
        remNum = BigDecimal.ZERO;
    }

    @Override
    public void memAdd()
    {
        remNum = remNum.add(operand.peek());
    }

    @Override
    public void memSub()
    {
        remNum = remNum.subtract(operand.peek());
    }

    @Override
    public String percent()
    {
        BigDecimal tmp = operand.pop();
        return String.valueOf(tmp.divide(BigDecimal.TEN.multiply(BigDecimal.TEN)));
    }

    @Override
    public String negate()
    {
        BigDecimal tmp = operand.pop();
        return String.valueOf(tmp.negate());
    }

    @Override
    public boolean isAdded()
    {
        return oldSize != size;
    }


}
