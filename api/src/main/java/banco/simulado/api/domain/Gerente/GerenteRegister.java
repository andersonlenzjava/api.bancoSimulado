package banco.simulado.api.domain.Gerente;

import banco.simulado.api.domain.Agencia.Agencia;
import banco.simulado.api.domain.Agencia.AgenciaRepository;
import banco.simulado.api.domain.Cliente.Cliente;
import banco.simulado.api.domain.Cliente.ClienteResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Optional;

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
        public Gerente converter(AgenciaRepository agenciaRepository) throws Exception {
                Optional<Agencia> agenciaOptional = agenciaRepository.findByNumero(this.agenciaNumero);
                Gerente gerente;
                if (agenciaOptional.isPresent()) {
                        Agencia agencia = agenciaOptional.get();
                        gerente = new Gerente(this.nome, this.cpf, this.dataNascimento, agencia);
                        return gerente;
                } else {
                        throw new Exception("Agencia inesistente!");
                }
        }

        public Gerente atualizar(Gerente gerente, AgenciaRepository agenciaRepository) throws Exception {

                Optional<Agencia> optionalAgencia = agenciaRepository.findByNumero(this.agenciaNumero);
                if (optionalAgencia.isPresent()) {
                        Agencia agencia = optionalAgencia.get();
                        gerente.setAgencia(agencia);
                        gerente.getPessoa().setNome(this.nome);
                        gerente.getPessoa().setCpf(cpf);
                        gerente.getPessoa().setDataNascimento(dataNascimento);
                        return gerente;
                } else {
                        throw new Exception("Agencia inesistente");
                }
        }

}
