package banco.simulado.api.domain.Agencia;

@Entity
public class Agencia {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private String nome;
    private String rua;
    private String cep;
    private Long numeroPredio;

}
