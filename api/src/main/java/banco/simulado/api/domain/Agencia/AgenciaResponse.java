package banco.simulado.api.domain.Agencia;

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
}
