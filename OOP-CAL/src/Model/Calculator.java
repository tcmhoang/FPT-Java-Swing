package Model;

import Entity.Model.ICalculatorOperator;

import java.util.Stack;
import java.util.regex.Pattern;

public class Calculator implements ICalculatorOperator
{
    private Stack<Double> operand;
    private Pattern pattern;


    public Calculator()
    {
        operand = new Stack<>();
        pattern = Pattern.compile("\\d+(\\.\\d+)?");
    }

    @Override
    public void reset()
    {
        operand.clear();
    }

    @Override
    public void clear()
    {

    }

    @Override
    public double get()
    {
        return operand.pop();
    }

    @Override
    public void append(String data)
    {
        if (pattern.matcher(data).matches()) operand.add(Double.parseDouble(data));
    }

    @Override
    public void del(String data)
    {

    }

    @Override
    public void sqrt()
    {

    }

    @Override
    public void percent()
    {

    }

    @Override
    public void div_by_one()
    {

    }

    @Override
    public void swap()
    {

    }
}
