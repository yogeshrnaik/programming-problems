// Problem: Following is the current implementation of the accounting system.
// The new requirement is to add a new meal type called 'Lunch'
// Your job is to apply OCP to the printReport functional, so that in future
// if you add new expense types, we should be able to do it by adding new code rather than
// modifing existing one.
// Also discover whether it is violating any other principle.

package com.programming.expense;

import java.util.Date;
import java.util.List;

public class ExpenseReport {

    public void printReport(List<Expense> expenses) {
        int total = 0;
        int mealExpenses = 0;

        printHeader();

        for (Expense expense : expenses) {
            if (expense.isMealExpense()) {
                mealExpenses += expense.amount;
            }

            System.out.println(expense);
            total += expense.amount;
        }

        printFooter(total, mealExpenses);
    }

    private void printFooter(int total, int mealExpenses) {
        System.out.println("Meal expenses : " + mealExpenses);
        System.out.println("Total expenses : " + total);
    }

    private void printHeader() {
        System.out.println("Expenses " + new Date() + "\n");
    }
}
