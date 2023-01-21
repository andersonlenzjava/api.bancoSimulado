package banco.simulado.api.domain.Conta;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long numero;
    private BigDecimal saldo = BigDecimal.ZERO;
    private TipoConta tipoConta = TipoConta.CORRENTE;

    @ManyToOne
    private Agencia agencia;

    @ManyToOne
    private Gerente gerente;

    @ManyToOne
    private Cliente cliente;
}
