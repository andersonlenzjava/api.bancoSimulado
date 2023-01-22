package banco.simulado.api.domain.Transacao;

import banco.simulado.api.domain.TipoOperacao.TipoOperacao;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransferirResponse(
        Long id,
        Long numeroContaTransferir,
        Long numeroContaReceber,
        TipoOperacao tipoOperacao,
        BigDecimal valor,
        LocalDate dataHoraTransacao) {

    public TransferirResponse(Transacao transacao) {
        this(transacao.getId(),
                transacao.getContaOperadora().getNumero(),
                transacao.getContaDestino().getNumero(),
                transacao.getTipoOperacao(),
                transacao.getValor(),
                transacao.getDataTransacao());
    }

    public static Page<TransferirResponse> converterTrasacoes(Page<Transacao> transacoes) {
        return transacoes.map(TransferirResponse::new);
    }

    public static TransferirResponse converterUmaTransacao(Transacao transacao) {
        return new TransferirResponse(transacao);
    }
}
