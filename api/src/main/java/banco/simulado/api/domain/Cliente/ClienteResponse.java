package banco.simulado.api.domain.Cliente;

import banco.simulado.api.domain.Agencia.Agencia;
import banco.simulado.api.domain.Agencia.AgenciaResponse;
import org.springframework.data.domain.Page;

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

    public static Page<ClienteResponse> converter(Page<Cliente> clientes) {
        return clientes.map(ClienteResponse::new);
    }

    public static ClienteResponse converterUmCliente(Cliente cliente) {
        return new ClienteResponse(cliente);
    }

}
