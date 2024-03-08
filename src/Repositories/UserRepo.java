package Repositories;

import models.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepo {

    private List<User>users = new ArrayList<>() ;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getUserbyuserId(int userId){
//        iterate over all users and get the corresponding user ;
        return null ;
    }
}
