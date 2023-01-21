package banco.simulado.api.domain.Conta;

import banco.simulado.api.domain.TipoConta.TipoConta;

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
}
