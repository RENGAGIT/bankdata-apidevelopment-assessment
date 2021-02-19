package rivers.bankdata.demo.bankdataaccountprofilews.models;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private String accountNumber;
    private LocalDateTime transactionTs;
    private BigDecimal amount;
    private String type;

    public Transaction() {

    }
}
