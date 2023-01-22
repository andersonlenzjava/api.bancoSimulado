package banco.simulado.api.domain.Transacao;

import banco.simulado.api.domain.Cliente.Cliente;
import banco.simulado.api.domain.Cliente.ClienteResponse;
import banco.simulado.api.domain.TipoOperacao.TipoOperacao;
import org.springframework.data.domain.Page;

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

    public static Page<SacarDepositarResponse> converter(Page<Transacao> transacoes) {
        return transacoes.map(SacarDepositarResponse::new);
    }

    public static SacarDepositarResponse converterUmaTransacaoSimples(Transacao transacao) {
        return new SacarDepositarResponse(transacao);
    }

}
