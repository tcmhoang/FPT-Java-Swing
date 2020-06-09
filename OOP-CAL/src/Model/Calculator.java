package Model;

import Entity.Model.ICalculatorOperator;

import java.util.Stack;
import java.util.regex.Pattern;

public class Calculator implements ICalculatorOperator
{
    private Stack<String> operable;
    private Pattern pattern;


    public Calculator()
    {
        operable = new Stack<>();
        pattern = Pattern.compile("\\d+(\\.\\d+)?");
    }

    @Override
    public void reset()
    {

    }

    @Override
    public void clear()
    {

    }

    @Override
    public void get()
    {

    }

    @Override
    public void append(String data)
    {
        boolean isNumber = pattern.matcher(data).matches();
        switch (data)
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
