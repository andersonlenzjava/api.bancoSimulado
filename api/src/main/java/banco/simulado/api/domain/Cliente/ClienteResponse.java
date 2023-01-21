package banco.simulado.api.domain.Cliente;

import java.time.LocalDate;

public record ClienteResponse(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        Integer idade
) {
    public ClienteResponse (Cliente cliente) {
        this(cliente.getId(),
                cliente.getPessoa().getNome(),
                cliente.getPessoa().getCpf(),
                cliente.getPessoa().getDataNascimento(),
                cliente.getPessoa().getIdade());
    }
}
