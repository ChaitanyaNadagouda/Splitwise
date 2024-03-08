package Runner;

import Controller.UserController;
import Repositories.ExpenseRepo;
import Repositories.GroupRepo;
import Repositories.UserExpenseRepo;
import Repositories.UserRepo;
import Services.UserService;
import dto.Transaction;
import models.*;
import startegy.HeapSettleUpStartegy;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        User chaitanya = new User("Chaitanya", "7759927484", "1233");
        User keerthi = new User("Keerthi", "7759917434", "1234");
        User sanjana = new User("Sanjana", "7752927484", "1233");
        User ramesh = new User("Ramesh", "7759927454", "1233");

        List<User> goaGuys = new ArrayList<>() ;
        goaGuys.add(chaitanya);
        goaGuys.add(keerthi);
        goaGuys.add(sanjana);
        goaGuys.add(ramesh);

//        create the group and add these users to the group
        Group goaTrip = new Group("GOA-TRIP");
        goaTrip.setUsers(goaGuys) ;

//        They went for goa ;Dinner
        Expense dinnerExpense = new Expense("Dinner", 5000, ExpenseType.REGULAR);
//        Add the expense share of everyone
        UserExpense chaitanyaShare = new UserExpense(chaitanya, dinnerExpense, 1000, UserExpenseType.HADTOPAY);
        UserExpense keerthiShare = new UserExpense(keerthi, dinnerExpense, 1000, UserExpenseType.HADTOPAY);
        UserExpense sundarShare = new UserExpense(sanjana, dinnerExpense, 1000, UserExpenseType.HADTOPAY);
        UserExpense rameshShare = new UserExpense(ramesh, dinnerExpense, 2000, UserExpenseType.HADTOPAY);

//        Capture who paid amount
        UserExpense paidByChaitanya = new UserExpense(chaitanya, dinnerExpense, 5000, UserExpenseType.PAID_BY);

//    Manually add all these details in the repos
        UserRepo userRepo = new UserRepo() ;
        GroupRepo groupRepo = new GroupRepo() ;
        UserExpenseRepo userExpenseRepo = new UserExpenseRepo() ;
        ExpenseRepo expenseRepo = new ExpenseRepo() ;

        userRepo.setUsers(goaGuys);
        goaTrip.getExpenses().add(dinnerExpense) ;
        groupRepo.getGroups().add(goaTrip);
        expenseRepo.getExpenses().add(dinnerExpense) ;

        userExpenseRepo.getUserExpenseList().add(chaitanyaShare) ;
        userExpenseRepo.getUserExpenseList().add(keerthiShare) ;
        userExpenseRepo.getUserExpenseList().add(sundarShare) ;
        userExpenseRepo.getUserExpenseList().add(rameshShare) ;

        userExpenseRepo.getUserExpenseList().add(paidByChaitanya) ;

        UserController userController = new UserController(new UserService(groupRepo, userExpenseRepo, new HeapSettleUpStartegy()));
        List<Transaction> userTransaction = userController.settleUser("Chaitanya","GOA-TRIP") ;
        for(Transaction transaction:userTransaction){
            System.out.println(transaction.getFrom()+" -> " + transaction.getTo() + " = " + transaction.getAmount()) ;
        }

        /*
        EXPECTED =
        KEERTHI 1K,SUNDAR 1K , RAMESH , 2K
         */

    }
}
