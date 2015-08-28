package com.expense.types;

import com.expense.Expense;

public class CarRentalExpense extends Expense {

    public CarRentalExpense(int amount) {
        super("Car Rental", amount);
    }

    @Override
    public boolean isOverExpense() {
        return false;
    }

    @Override
    public boolean isMealExpense() {
        return false;
    }
}
