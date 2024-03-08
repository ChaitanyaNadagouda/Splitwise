package Repositories;

import models.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseRepo {

    private List<Expense> expenses = new ArrayList<>() ;

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
