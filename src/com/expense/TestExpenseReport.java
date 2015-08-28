package com.expense;

import com.expense.types.BreakfastExpense;
import com.expense.types.CarRentalExpense;
import com.expense.types.DinnerExpense;
import org.junit.Test;

import java.util.Arrays;

public class TestExpenseReport {

    @Test
    public void testExpenseReport() {
        ExpenseReport expenseReport = new ExpenseReport();
        expenseReport.printReport(Arrays.asList(new DinnerExpense(6000), new BreakfastExpense(500), new CarRentalExpense(10000)));

        expenseReport.printReport(Arrays.asList(new DinnerExpense(6000), new BreakfastExpense(1200), new CarRentalExpense(10000)));
    }
}
