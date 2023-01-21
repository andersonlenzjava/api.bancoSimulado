package banco.simulado.api.domain.Gerente;

public class Gerente {

    @Enumerated(EnumType.STRING)
    StatusTrabalho status = StatusTrabalho.TRABALHANDO;
    @ManyToOne
    Agencia agencia;

}
