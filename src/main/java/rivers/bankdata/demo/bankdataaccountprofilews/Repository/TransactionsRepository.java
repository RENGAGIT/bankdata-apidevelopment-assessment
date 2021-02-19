package rivers.bankdata.demo.bankdataaccountprofilews.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import rivers.bankdata.demo.bankdataaccountprofilews.Entity.TransactionEntity;
import rivers.bankdata.demo.bankdataaccountprofilews.models.Transaction;

import java.util.List;

public interface TransactionsRepository extends MongoRepository<TransactionEntity, String> {
    public List<Transaction> findByAccountNumber(String accountNumber);
}
