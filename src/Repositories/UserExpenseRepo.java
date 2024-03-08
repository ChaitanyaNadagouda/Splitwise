package Repositories;

import models.UserExpense;

import java.util.ArrayList;
import java.util.List;

public class UserExpenseRepo {
    private List<UserExpense> userExpenses = new ArrayList<>() ;

    public List<UserExpense> getUserExpenseList() {
        return userExpenses;
    }

    public void setUserExpenseList(List<UserExpense> userExpenseList) {
        this.userExpenses = userExpenses;
    }

    public List<UserExpense> findUserExpensesByExpenseDescription(String desc){
        List<UserExpense> userExpenseList = new ArrayList<>() ;
        for(UserExpense userExpense:userExpenses){
            if(desc.equals(userExpense.getExpense().getDescription())){
                userExpenseList.add(userExpense) ;
            }
        }
        return userExpenseList ;
    }
}
