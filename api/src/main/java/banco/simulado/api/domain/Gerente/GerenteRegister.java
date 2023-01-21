package banco.simulado.api.domain.Gerente;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record GerenteRegister(

        @NotNull
        @NotEmpty
        @Length(min = 5, max = 80)
        String nome,

        @NotNull
        @NotEmpty
        @Length(min = 9, max = 9)
        String cpf,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,

        @NotNull
        @NotEmpty
        String agenciaNumero
) {
}
