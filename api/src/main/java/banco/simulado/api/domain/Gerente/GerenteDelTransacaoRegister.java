package banco.simulado.api.domain.Gerente;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;


public record GerenteDelTransacaoRegister(


        @NotNull
        @NotEmpty
        @Length(min = 5, max = 80)
        String nome,

        @NotNull
        @NotEmpty
        @Length(min = 9, max = 9)
        String cpf

) {
}
