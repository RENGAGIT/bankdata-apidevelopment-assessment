package rivers.bankdata.demo.bankdataaccountprofilews.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "Transactions")
@Data
public class TransactionEntity {

    @Id
    private String id;
    private String accountNumber;
    private BigDecimal amount;
    private LocalDateTime transactionTs;
    private String type;

}
