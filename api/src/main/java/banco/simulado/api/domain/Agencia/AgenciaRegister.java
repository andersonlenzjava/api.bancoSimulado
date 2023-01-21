package banco.simulado.api.domain.Agencia;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record AgenciaRegister(

        @NotNull @NotEmpty @Length(min = 5, max = 5)
        String numero,

        @NotNull @NotEmpty
        String nome,

        @NotNull @NotEmpty
        String rua,

        @NotNull @NotEmpty
        @Length(min = 5, max = 8)
        String cep,

        @Positive
        @NotNull
        Long numeroPredio) {
}
