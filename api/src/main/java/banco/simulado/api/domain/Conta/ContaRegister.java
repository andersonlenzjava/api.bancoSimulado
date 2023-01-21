package banco.simulado.api.domain.Conta;

import banco.simulado.api.domain.TipoConta.TipoConta;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record ContaRegister(

        Long numero,

        @NotNull
        BigDecimal saldo,

        @NotNull
        TipoConta tipoConta,

        @NotEmpty
        @NotNull
        @Length(min = 5, max = 5)
        String agenciaNumero,

        @NotEmpty
        @NotNull
        String cpfGerente,

        @NotEmpty
        @NotNull
        String cpfCliente
) {
}
