package banco.simulado.api.domain.Conta;

import banco.simulado.api.domain.Cliente.Cliente;
import banco.simulado.api.domain.Cliente.ClienteResponse;
import banco.simulado.api.domain.TipoConta.TipoConta;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public record ContaResponse(
        Long id,
        Long numero,
        BigDecimal saldo,
        TipoConta tipoConta,
        String numeroAgencia,
        Long idGerente,
        String nomeGerente,
        Long idCliente,
        String nomeCliente
) {
    public ContaResponse (Conta conta) {
        this (conta.getId(),
                conta.getNumero(),
                conta.getSaldo(),
                conta.getTipoConta(),
                conta.getAgencia().getNumero(),
                conta.getGerente().getId(),
                conta.getGerente().getPessoa().getNome(),
                conta.getCliente().getId(),
                conta.getCliente().getPessoa().getNome());
    }

    public static Page<ContaResponse> converter(Page<Conta> contas) {
        return contas.map(ContaResponse::new);
    }

    public static ContaResponse converterUmaConta(Conta conta) {
        return new ContaResponse(conta);
    }

}
