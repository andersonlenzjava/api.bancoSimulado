package banco.simulado.api.domain.Gerente;

import banco.simulado.api.domain.Agencia.Agencia;
import banco.simulado.api.domain.Pessoa.Pessoa;
import banco.simulado.api.domain.StatusTrabalho.StatusTrabalho;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Gerente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    StatusTrabalho statusTrabalho = StatusTrabalho.TRABALHANDO;

    @ManyToOne
    private Agencia agencia;

    @Embedded
    private Pessoa pessoa;

    public Gerente (String nome, String cpf, LocalDate dataNascimento, Agencia agencia) {
        this.pessoa = new Pessoa(nome, cpf, dataNascimento);
        this.agencia = agencia;
    }

}
