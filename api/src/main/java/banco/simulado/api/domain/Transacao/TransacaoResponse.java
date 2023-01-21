package banco.simulado.api.domain.Transacao;

import banco.simulado.api.domain.TipoOperacao.TipoOperacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoResponse(
        Long id,
        Long numeroContaTransferir,
        Long numeroContaReceber,
        TipoOperacao tipoOperacao,
        BigDecimal valor,
        LocalDate dataHoraTransacao) {

    public TransacaoResponse(Transacao transacao) {
        this(transacao.getId(),
                transacao.getContaOperadora().getNumero(),
                transacao.getContaDestino().getNumero(),
                transacao.getTipoOperacao(),
                transacao.getValor(),
                transacao.getDataTransacao());
    }
}
