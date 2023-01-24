package banco.simulado.api.domain.Conta;

import banco.simulado.api.domain.TipoConta.TipoConta;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Optional;

public record ContaRegister(

        @NotEmpty
        @NotNull
        @Length(min = 5, max = 5)
        String numero,

        @NotNull
        TipoConta tipoConta,

        @NotEmpty
        @NotNull
        @Length(min = 5, max = 5)
        String agenciaNumero,

        @NotEmpty
        @NotNull
        String cpfCliente
) {
}
