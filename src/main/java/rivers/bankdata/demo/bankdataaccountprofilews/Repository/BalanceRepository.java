package rivers.bankdata.demo.bankdataaccountprofilews.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import rivers.bankdata.demo.bankdataaccountprofilews.Entity.BalanceEntity;

public interface BalanceRepository extends MongoRepository<BalanceEntity, String> {
    public BalanceEntity findByAccountNumber(String accountNumber);
}