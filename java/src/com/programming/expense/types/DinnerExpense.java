package com.programming.expense.types;

import com.programming.expense.Expense;

public class DinnerExpense extends Expense {

    public DinnerExpense(int amount) {
        super("Dinner", amount);
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
