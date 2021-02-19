package rivers.bankdata.demo.bankdataaccountprofilews.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import rivers.bankdata.demo.bankdataaccountprofilews.Entity.BalanceEntity;
import rivers.bankdata.demo.bankdataaccountprofilews.Entity.TransactionEntity;
import rivers.bankdata.demo.bankdataaccountprofilews.Repository.TransactionsRepository;
import rivers.bankdata.demo.bankdataaccountprofilews.models.GetTransactionDetails;
import rivers.bankdata.demo.bankdataaccountprofilews.models.Transaction;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Transaction> retrieveTransactions(GetTransactionDetails getTransactionDetails) {
        List<TransactionEntity> transactionsEntityList = null;
        List<Transaction> txnList = null;
        LocalDate todaysDate = LocalDate.now();
        LocalDate startDate = null;
        LocalDate endDate = null;

        if(getTransactionDetails.getAccountNumber() != null )
        {
            List<String> type = new ArrayList<>();
            if(getTransactionDetails.getType() == null) {
                type.add("DEPOSIT");
                type.add("WITHDRAW");
            } else {
                type.add(getTransactionDetails.getType());
            }

            if (getTransactionDetails.getFromDate() != null && getTransactionDetails.getToDate() != null)
            {
                startDate = getTransactionDetails.getFromDate();
                endDate = getTransactionDetails.getToDate();
                Query queryTransaction = new Query();
                CriteriaDefinition criteriaDef = Criteria.where("accountNumber").is(getTransactionDetails.getAccountNumber())
                        .andOperator(
                                Criteria.where("transactionTs").gte(startDate.atStartOfDay()),
                                Criteria.where("transactionTs").lte(endDate.atStartOfDay()),
                                Criteria.where("type").in(type));

                queryTransaction.addCriteria(criteriaDef);
                transactionsEntityList = mongoTemplate.find(queryTransaction, TransactionEntity.class);

            }
            else if (getTransactionDetails.getTimeRange() != null)
            {
                switch (getTransactionDetails.getTimeRange())
                {
                    case "LAST_MONTH":
                        startDate = todaysDate.minusDays(30);
                        break;
                    case "LAST_7_DAYS":
                        startDate = todaysDate.minusDays(7);
                        break;
                    default:
                        startDate = todaysDate;
                        break;
                }
                logger.info("startDate {} endDate {} type {}", startDate, endDate, type);
                Query queryTransaction = new Query();
                CriteriaDefinition criteriaDef = Criteria.where("accountNumber").is(getTransactionDetails.getAccountNumber())
                                                    .andOperator(
                                                     Criteria.where("transactionTs").gte(startDate.atStartOfDay()),
                                                     Criteria.where("type").in(type));

                queryTransaction.addCriteria(criteriaDef);
                transactionsEntityList = mongoTemplate.find(queryTransaction, TransactionEntity.class);

            }
        }

        return transactionsEntityList.stream().map(tl -> {
            Transaction transaction = new Transaction();
            transaction.setAccountNumber(tl.getAccountNumber());
            transaction.setAmount(tl.getAmount().setScale(1));
            transaction.setType(tl.getType());
            transaction.setTransactionTs(tl.getTransactionTs());
            return transaction;
                }).collect(Collectors.toList());
    }

    public void addUpdateBalance(@RequestBody BalanceEntity balanceEntity)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("accountNumber").is(balanceEntity.getAccountNumber()));
        Update update = new Update();
        update.set("balance",balanceEntity.getBalance());
        update.set("lastUpdatedTime", balanceEntity.getLastUpdatedTime());
        mongoTemplate.upsert(query, update, BalanceEntity.class);
    }


}
