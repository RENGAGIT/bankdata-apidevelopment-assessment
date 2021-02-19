package rivers.bankdata.demo.bankdataaccountprofilews.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "Balance")
@Data
public class BalanceEntity {
    @Id
    private String id;
    private String accountNumber;
    private BigDecimal balance;
    private LocalDateTime lastUpdatedTime;
}


