package com.programming.expense.types;

import com.programming.expense.Expense;

public class BreakfastExpense extends Expense {

    public BreakfastExpense(int amount) {
        super("Breakfast", amount);
    }

    @Override
    public boolean isOverExpense() {
        return amount > 1000;
    }

    @Override
    public boolean isMealExpense() {
        return true;
    }
}
