package rivers.bankdata.demo.bankdataaccountprofilews.kafka;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import rivers.bankdata.demo.bankdataaccountprofilews.Entity.BalanceEntity;
import rivers.bankdata.demo.bankdataaccountprofilews.Entity.TransactionEntity;
import rivers.bankdata.demo.bankdataaccountprofilews.Repository.BalanceRepository;
import rivers.bankdata.demo.bankdataaccountprofilews.Repository.TransactionsRepository;
import rivers.bankdata.demo.bankdataaccountprofilews.models.Transaction;
import rivers.bankdata.demo.bankdataaccountprofilews.service.TransactionService;
import java.math.BigDecimal;

@Component
@Slf4j
public class TransactionListener {

    Logger logger = LoggerFactory.getLogger(TransactionListener.class);
    @Autowired
    BalanceRepository balanceRepository;

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    TransactionService transactionService;

    @KafkaListener(topics = "bank_transactions", groupId = "bank-transactions-grp", containerFactory = "kafkaListenerContainerFactory")
    public void transactionListener(Transaction transaction)
    {
        try
        {
            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setAccountNumber(transaction.getAccountNumber());
            transactionEntity.setAmount(transaction.getAmount());
            transactionEntity.setType(transaction.getType());
            transactionEntity.setTransactionTs(transaction.getTransactionTs());
            transactionsRepository.save(transactionEntity);

            BalanceEntity currentBalance = balanceRepository.findByAccountNumber(transaction.getAccountNumber());
            BigDecimal availableBalance = currentBalance.getBalance();

            if("DEPOSIT".equalsIgnoreCase(transaction.getType())) {
                   availableBalance = availableBalance.add(transaction.getAmount());
            } else if ("WITHDRAW".equalsIgnoreCase(transaction.getType())) {
                   availableBalance = availableBalance.subtract(transaction.getAmount());
            }
            currentBalance.setBalance(availableBalance);
            currentBalance.setLastUpdatedTime(transaction.getTransactionTs());
            transactionService.addUpdateBalance(currentBalance);
        } catch(Exception e)
        {
            logger.error("Exception while inserting transactions records to db {}", e);
        }
    }
 }
