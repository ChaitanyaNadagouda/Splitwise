package Services;

import Repositories.GroupRepo;
import Repositories.UserExpenseRepo;
import dto.Transaction;
import models.*;
import startegy.SettleUpStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {

    private GroupRepo groupRepo ;
    private UserExpenseRepo userExpenseRepo ;

    private SettleUpStrategy settleUpStrategy ;

    public UserService(GroupRepo groupRepo, UserExpenseRepo userExpenseRepo, SettleUpStrategy settleUpStrategy) {
        this.groupRepo = groupRepo;
        this.userExpenseRepo = userExpenseRepo;
        this.settleUpStrategy = settleUpStrategy;
    }

    public List<Transaction> settleUser(String userName, String groupName){
        /*
        1) get all expenses of a group
        2) filter out regular expenses
        3) for every expense find user expenses
        4) we iterate over all the user expenses and simultaneously wee calc the extra aamount of every user
           by maintaining the map of extra amount .
           Data : [A:2000,B:1000,etc] , if the type is paid_by then extra amount += amount ;
           else if it is whohadtopay , exra amount -= amount ;
        5) pass this map to another class which will use heaps and gets me the transaction list .
         */

        Map<User,Integer> extraAmountMap = new HashMap<>() ;

        List<Expense> expenses = groupRepo.findExpensesbyGroup(groupName) ;

        for(Expense expense:expenses){
            if (expense.getExpenseType().equals(ExpenseType.REGULAR)) {
                List<UserExpense> userExpenseList = userExpenseRepo.findUserExpensesByExpenseDescription(expense.getDescription()) ;
                for(UserExpense userExpense:userExpenseList){
                    User user = userExpense.getUser() ;
                    if (!extraAmountMap.containsKey(user)) {
                        extraAmountMap.put(user,0) ;
                    }
                    Integer amount = extraAmountMap.get(user) ;
                    if (userExpense.getUserExpenseType()== UserExpenseType.PAID_BY) {
                        amount+=userExpense.getAmount() ;
                    }else{
                        amount-= userExpense.getAmount();
                    }

                    extraAmountMap.put(user,amount) ;
                }
            }
        }

//        finding the transaction using extra amount for every user ;

        List<Transaction> grouptransactions = settleUpStrategy.settleUpUser(extraAmountMap) ;

        List<Transaction> userTransactions = new ArrayList<>() ;

        for(Transaction transaction:grouptransactions){
            if (transaction.getFrom().equals(userName) || transaction.getTo().equals(userName)) {
                userTransactions.add(transaction) ;
            }
        }


        return userTransactions ;
    }

}
