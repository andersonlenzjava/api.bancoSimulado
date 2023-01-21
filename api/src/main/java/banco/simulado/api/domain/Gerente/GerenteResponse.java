package banco.simulado.api.domain.Gerente;

import banco.simulado.api.domain.Agencia.Agencia;
import banco.simulado.api.domain.StatusTrabalho.StatusTrabalho;

import java.time.LocalDate;

public record GerenteResponse(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        StatusTrabalho status,
        Integer idade,
        String agencia
) {
    public GerenteResponse (Gerente gerente) {
        this(gerente.getId(),
                gerente.getPessoa().getNome(),
                gerente.getPessoa().getCpf(),
                gerente.getPessoa().getDataNascimento(),
                gerente.getStatusTrabalho(),
                gerente.getPessoa().getIdade(),
                gerente.getAgencia().getNumero());
    }
}
