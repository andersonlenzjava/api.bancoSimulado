package banco.simulado.api.domain.Transacao;

import banco.simulado.api.domain.Conta.Conta;
import banco.simulado.api.domain.TipoOperacao.TipoOperacao;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;

    private LocalDate dataTransacao;

    @Enumerated(EnumType.STRING)
    private TipoOperacao tipoOperacao;

    @ManyToOne
    private Conta contaOperadora;

    @ManyToOne
    private Conta contaDestino;




}
