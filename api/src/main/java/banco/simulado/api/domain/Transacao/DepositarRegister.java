package banco.simulado.api.domain.Transacao;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DepositarRegister(

        @NotNull
        @NotEmpty
        String numeroContaDepositar,

        @Positive
        @NotNull
        BigDecimal valorTransferir
) {
}
