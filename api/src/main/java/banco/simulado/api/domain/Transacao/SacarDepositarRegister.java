package banco.simulado.api.domain.Transacao;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record SacarDepositarRegister(

        @NotNull
        @NotEmpty
        String numeroContaTransferir,

        @Positive
        @NotNull
        BigDecimal valorTransferir
) {
}
