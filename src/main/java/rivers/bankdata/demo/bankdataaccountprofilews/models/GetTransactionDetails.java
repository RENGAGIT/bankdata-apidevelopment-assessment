package rivers.bankdata.demo.bankdataaccountprofilews.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class GetTransactionDetails {
    private String accountNumber;
    private String type;
    private BigDecimal amount;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String timeRange;
}
