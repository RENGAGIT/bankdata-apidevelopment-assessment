package rivers.bankdata.demo.bankdataaccountprofilews.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Balance {
    private String accountNumber;
    private LocalDateTime lastUpdateTimestamp;
    private BigDecimal balance;
}
