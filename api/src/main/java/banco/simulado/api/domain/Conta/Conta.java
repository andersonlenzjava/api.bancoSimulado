package banco.simulado.api.domain.Conta;

import banco.simulado.api.domain.Agencia.Agencia;
import banco.simulado.api.domain.Cliente.Cliente;
import banco.simulado.api.domain.Gerente.Gerente;
import banco.simulado.api.domain.TipoConta.TipoConta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
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
    private Cliente cliente;

    public Conta(Long numero, TipoConta tipoConta, Agencia agencia, Cliente cliente) {
        this.numero = numero;
        this.tipoConta = tipoConta;
        this.agencia = agencia;
        this.cliente = cliente;
    }
}
