package com.programming.expense.types;

import com.programming.expense.Expense;

public class LunchExpense extends Expense {

    public LunchExpense(int amount) {
        super("Lunch", amount);
    }

    @Override
    public boolean isOverExpense() {
        return amount > 5000;
    }

    @Override
    public boolean isMealExpense() {
        return true;
    }
}
