package com.programming.expense;

import java.util.Arrays;

import org.junit.Test;

import com.programming.expense.types.BreakfastExpense;
import com.programming.expense.types.CarRentalExpense;
import com.programming.expense.types.DinnerExpense;

public class TestExpenseReport {

    @Test
    public void testExpenseReport() {
        ExpenseReport expenseReport = new ExpenseReport();
        expenseReport.printReport(Arrays.asList(new DinnerExpense(6000), new BreakfastExpense(500), new CarRentalExpense(10000)));

        expenseReport.printReport(Arrays.asList(new DinnerExpense(6000), new BreakfastExpense(1200), new CarRentalExpense(10000)));
    }
}
