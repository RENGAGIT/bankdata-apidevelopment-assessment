package rivers.bankdata.demo.bankdataaccountprofilews.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceResponse {

    private String accountNumber;
    private BigDecimal latestBalance;
}
