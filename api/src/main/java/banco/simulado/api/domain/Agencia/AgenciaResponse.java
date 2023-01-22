package banco.simulado.api.domain.Agencia;

import org.springframework.data.domain.Page;

public record AgenciaResponse (
        Long id,
        String numero,
        String nome,
        String rua,
        String cep,
        Long numeroPredio) {

    public AgenciaResponse(Agencia agencia) {
        this(agencia.getId(),
                agencia.getNumero(),
                agencia.getNome(),
                agencia.getRua(),
                agencia.getCep(),
                agencia.getNumeroPredio());
    }

    public static Page<AgenciaResponse> converter(Page<Agencia> agencias) {
        return agencias.map(AgenciaResponse::new);
    }

    public static AgenciaResponse converterUmaAgencia(Agencia agencia) {
        return new AgenciaResponse(agencia);
    }

}
