package startegy;

import dto.Transaction;
import models.User;

import java.util.List;
import java.util.Map;

public interface SettleUpStrategy {

    public List<Transaction> settleUpUser(Map<User,Integer> map) ;

}
