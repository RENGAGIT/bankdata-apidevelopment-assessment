package rivers.bankdata.demo.bankdataaccountprofilews.kafka;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import rivers.bankdata.demo.bankdataaccountprofilews.Entity.BalanceEntity;
import rivers.bankdata.demo.bankdataaccountprofilews.models.Balance;
import rivers.bankdata.demo.bankdataaccountprofilews.service.TransactionService;

@Component
@Slf4j
public class BalanceListener {

    Logger logger = LoggerFactory.getLogger(BalanceListener.class);

    @Autowired
    TransactionService transactionService;

    @KafkaListener(topics = "bank_balance", groupId = "bank-balance-grp", containerFactory = "balanceListenerContainerFactory")
    public void balanceListener(Balance balance)
    {
        try
        {
            BalanceEntity balanceEntity = new BalanceEntity();
            balanceEntity.setAccountNumber(balance.getAccountNumber());
            balanceEntity.setBalance(balance.getBalance());
            balanceEntity.setLastUpdatedTime(balance.getLastUpdateTimestamp());
            transactionService.addUpdateBalance(balanceEntity);

        } catch(Exception e)
        {
            logger.error("Exception while updating balance {} ", e);
        }
    }
 }
