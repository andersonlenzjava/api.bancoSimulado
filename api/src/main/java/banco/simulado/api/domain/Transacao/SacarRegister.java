package banco.simulado.api.domain.Transacao;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record SacarRegister(

        @NotNull
        @NotEmpty
        String numeroContaSacar,

        @Positive
        @NotNull
        BigDecimal valorTransferir
) {
}
