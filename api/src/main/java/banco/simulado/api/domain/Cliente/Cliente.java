package banco.simulado.api.domain.Cliente;

import banco.simulado.api.domain.Pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Pessoa pessoa;

    public Cliente (String nome, String cpf, LocalDate dataNascimento) {
        this.pessoa = new Pessoa(nome, cpf, dataNascimento);
    }
}
