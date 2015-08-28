package com.expense.types;

import com.expense.Expense;

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
