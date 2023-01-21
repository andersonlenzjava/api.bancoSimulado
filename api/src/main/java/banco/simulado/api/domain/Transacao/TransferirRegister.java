package banco.simulado.api.domain.Transacao;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferirRegister(

        @NotNull
        @NotEmpty
        String numeroContaTransferir,

        @NotNull
        @NotEmpty
        String numeroContaReceber,

        @Positive
        @NotNull
        BigDecimal valorTransferir
) {
}
