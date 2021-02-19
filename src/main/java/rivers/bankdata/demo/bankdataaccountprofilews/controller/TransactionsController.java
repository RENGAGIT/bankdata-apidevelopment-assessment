package rivers.bankdata.demo.bankdataaccountprofilews.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import rivers.bankdata.demo.bankdataaccountprofilews.Entity.BalanceEntity;
import rivers.bankdata.demo.bankdataaccountprofilews.Entity.TransactionEntity;
import rivers.bankdata.demo.bankdataaccountprofilews.Repository.BalanceRepository;
import rivers.bankdata.demo.bankdataaccountprofilews.Repository.TransactionsRepository;
import rivers.bankdata.demo.bankdataaccountprofilews.models.BalanceResponse;
import rivers.bankdata.demo.bankdataaccountprofilews.models.GetTransactionDetails;
import rivers.bankdata.demo.bankdataaccountprofilews.models.Transaction;
import rivers.bankdata.demo.bankdataaccountprofilews.service.TransactionService;
import java.util.List;

@RestController
public class TransactionsController {

    @Autowired
    BalanceRepository balanceRepository;

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    TransactionService transactionService;

    Logger logger = LoggerFactory.getLogger(TransactionsController.class);

    @GetMapping("/balance/retrieve/{accountNumber}")
    public BalanceResponse retrieveBalance(@PathVariable String accountNumber)
    {
        BalanceResponse balance = new BalanceResponse();
        BalanceEntity balanceEntity = balanceRepository.findByAccountNumber(accountNumber);
        balance.setLatestBalance(balanceEntity.getBalance());
        balance.setAccountNumber(balanceEntity.getAccountNumber());
        return balance;
    }

    @PostMapping("/transactions/retrieve")
    public List<Transaction> retrieveTransactions(@RequestBody GetTransactionDetails getTransactionDetails) {
        return transactionService.retrieveTransactions(getTransactionDetails);
    }

    @PostMapping("/addBalance")
    public String saveBalance(@RequestBody BalanceEntity balanceEntity)
    {
         transactionService.addUpdateBalance(balanceEntity);
         return "Saved";
    }

    @PostMapping("/addTransactions")
    public String saveTransactions(@RequestBody TransactionEntity transactionEntity)
    {
        transactionsRepository.save(transactionEntity);
        return "saved";
    }
}
