package banco.simulado.api.domain.Pessoa;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private Integer idade;

    public Pessoa(String nome, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.idade = this.calculaIdade(dataNascimento);
    }

    public Integer calculaIdade(LocalDate dataNascimento) {
        Period idade = Period.between(dataNascimento, LocalDate.now());
        return Integer.valueOf(idade.getYears());
    }

}
