package banco.simulado.api.domain.Transacao;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Conta contaOperadora;

    @ManyToOne
    private Conta contaDestino;

    @Enumerated(EnumType.STRING)
    private TipoOperacao tipoOperacao;
    private BigDecimal valor;
    private LocalDate dataTransacao;

}
