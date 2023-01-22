package banco.simulado.api.domain.Agencia;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Agencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String nome;
    private String rua;
    private String cep;
    private Long numeroPredio;

    public Agencia(String numero, String nome, String rua, String cep, Long numeroPredio) {
        this.numero = numero;
        this.nome = nome;
        this.rua = rua;
        this.cep = cep;
        this.numeroPredio = numeroPredio;
    }
}
