package Controller;

import Services.UserService;
import dto.Transaction;

import java.util.List;

public class UserController {

    private UserService userService ;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public List<Transaction> settleUser(String userName , String groupName){

        return userService.settleUser(userName,groupName) ;

    }

}
