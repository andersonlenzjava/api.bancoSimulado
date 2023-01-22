package banco.simulado.api.domain.Conta;

import banco.simulado.api.domain.Agencia.Agencia;
import banco.simulado.api.domain.Agencia.AgenciaRepository;
import banco.simulado.api.domain.Cliente.Cliente;
import banco.simulado.api.domain.Cliente.ClienteRepository;
import banco.simulado.api.domain.Gerente.Gerente;
import banco.simulado.api.domain.Gerente.GerenteRepository;
import banco.simulado.api.domain.TipoConta.TipoConta;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Optional;

public record ContaRegister(

        Long numero,

        @NotNull
        BigDecimal saldo,

        @NotNull
        TipoConta tipoConta,

        @NotEmpty
        @NotNull
        @Length(min = 5, max = 5)
        String agenciaNumero,

        @NotEmpty
        @NotNull
        String cpfGerente,

        @NotEmpty
        @NotNull
        String cpfCliente
) {
        public Conta converter(AgenciaRepository agenciaRepository,
                               GerenteRepository gerenteRepository, ClienteRepository clienteRepository) throws Exception {
                Optional<Agencia> optionalAgencia = agenciaRepository.findByNumero(agenciaNumero);
                Optional<Gerente> optionalGerente = gerenteRepository.findByCpf(cpfGerente);
                Optional<Cliente> optionalCliente = clienteRepository.findByCpf(cpfCliente);

                if (optionalAgencia.isPresent() && optionalGerente.isPresent() && optionalCliente.isPresent()) {
                        Agencia agencia = optionalAgencia.get();
                        Gerente gerente = optionalGerente.get();
                        Cliente cliente = optionalCliente.get();
                        return new Conta(numero, saldo, tipoConta, agencia, gerente, cliente);
                } else {
                        throw new Exception("Agencia, Gerente ou Cliente não encontrada");
                }
        }

        public Conta atualizar(Conta conta, AgenciaRepository agenciaRepository) throws Exception {
                Optional<Agencia> optionalAgencia = agenciaRepository.findByNumero(agenciaNumero);
                if (optionalAgencia.isPresent()) {
                        Agencia agencia = optionalAgencia.get();
                        conta.setNumero(numero);
                        conta.setSaldo(saldo);
                        conta.setTipoConta(tipoConta);
                        conta.setAgencia(agencia);
                        return conta;
                } else {
                        throw new Exception("Agencia inesistente!");
                }
        }

}
