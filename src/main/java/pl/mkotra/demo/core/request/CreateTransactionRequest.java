package pl.mkotra.demo.core.request;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateTransactionRequest(

        @NotNull
        String customerId,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal amount) {
}
