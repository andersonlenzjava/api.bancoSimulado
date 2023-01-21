package banco.simulado.api.domain.Transacao;

import java.math.BigDecimal;

public record TransferirRegister(

        private Long numeroContaTransferir;

        private Long numeroContaReceber;

        @Positive
        @NotNull
        private BigDecimal valorTransferir;

) {
}
