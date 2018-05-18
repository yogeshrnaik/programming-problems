package com.programming.expense;

public abstract class Expense {

    protected int amount;
    protected String name;
    public static final String MEAL_OVER_EXPENSE_MARKER = "X";

    public Expense(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public abstract boolean isOverExpense();

    public abstract boolean isMealExpense();

    @Override
    public String toString() {
        return name + "\t" + amount + "\t" + mealOverExpenseMarker();
    }

    protected String mealOverExpenseMarker() {
        return isMealExpense() && isOverExpense() ? MEAL_OVER_EXPENSE_MARKER : " ";
    }

}