package banco.simulado.api.domain.Transacao;

import banco.simulado.api.domain.TipoOperacao.TipoOperacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SacarDepositarResponse(

        Long id,
        Long contaOperacao,
        TipoOperacao tipoOperacao,
        BigDecimal valor,
        LocalDate dataHoraTransacao) {

    public SacarDepositarResponse(Transacao transacao) {
        this(transacao.getId(),
                transacao.getContaOperadora().getNumero(),
                transacao.getTipoOperacao(),
                transacao.getValor(),
                transacao.getDataTransacao());
    }
}
