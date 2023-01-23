package banco.simulado.api.service;

import banco.simulado.api.domain.Conta.Conta;
import banco.simulado.api.domain.Conta.ContaRepository;
import banco.simulado.api.domain.TipoOperacao.TipoOperacao;
import banco.simulado.api.domain.Transacao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class TransacaoService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    //depositar
    public ResponseEntity<SacarDepositarResponse> depositar(SacarDepositarRegister sacarDepositarForm,
                                                            UriComponentsBuilder uriBuilder) throws Exception {
        Optional<Conta> contaOperadora = contaRepository.findByNumero(Long.valueOf(sacarDepositarForm.numeroContaTransferir()));
        if (contaOperadora.isPresent()) {
            Conta conta = contaOperadora.get();
            conta.setSaldo(conta.getSaldo().add(sacarDepositarForm.valorTransferir()));
            contaRepository.save(conta);

            Transacao transacao = salvarTransferencia(conta, conta, TipoOperacao.DEPOSITAR, sacarDepositarForm.valorTransferir());
            URI uri = uriBuilder.path("/transacao/{id}").buildAndExpand(transacao.getId()).toUri();
            return ResponseEntity.created(uri).body(new SacarDepositarResponse(transacao));
        } else {
            throw new Exception("Conta inestitente!");
        }
    }

    //sacar
    public ResponseEntity<SacarDepositarResponse> sacar(SacarDepositarRegister sacarDepositarForm,
                                                   UriComponentsBuilder uriBuilder) throws Exception {
        Optional<Conta> contaOperadora = contaRepository.findByNumero(Long.valueOf(sacarDepositarForm.numeroContaTransferir()));
        if (contaOperadora.isPresent()) {
            Conta conta = contaOperadora.get();

            if (((sacarDepositarForm.valorTransferir().compareTo(conta.getSaldo())) < 0)) {
                conta.setSaldo(conta.getSaldo().subtract(sacarDepositarForm.valorTransferir()));
                contaRepository.save(conta);

                Transacao transacao = salvarTransferencia(conta, conta, TipoOperacao.SACAR, sacarDepositarForm.valorTransferir());
                URI uri = uriBuilder.path("/transacao/{id}").buildAndExpand(transacao.getId()).toUri();
                return ResponseEntity.created(uri).body(new SacarDepositarResponse(transacao));
            } else {
                throw new Exception("Saldo insuficiente!");
            }
        } else {
            throw new Exception("Conta inestitente!");
        }
    }

    //transferir
    public ResponseEntity<TransferirResponse> transferir(TransferirRegister transferirForm, UriComponentsBuilder uriBuilder)
            throws Exception {
        Optional<Conta> contaOptionalOperadora = contaRepository.findByNumero(Long.valueOf(transferirForm.numeroContaTransferir()));
        Optional<Conta> contaOptionalDestino = contaRepository.findByNumero(Long.valueOf(transferirForm.numeroContaTransferir()));
        if ((contaOptionalOperadora.isPresent()) && (contaOptionalDestino.isPresent())) {
            Conta contaOperadora = contaOptionalOperadora.get();
            Conta contaDestino = contaOptionalDestino.get();

            if (((transferirForm.valorTransferir().compareTo(contaOperadora.getSaldo())) < 0)) {
                contaOperadora.setSaldo(contaOperadora.getSaldo().subtract(transferirForm.valorTransferir()));
                contaDestino.setSaldo(contaDestino.getSaldo().add(transferirForm.valorTransferir()));
                contaRepository.save(contaOperadora);
                contaRepository.save(contaDestino);

                 Transacao transacao = salvarTransferencia(contaOperadora, contaDestino, TipoOperacao.TRANSFERIR,
                        transferirForm.valorTransferir());
                URI uri = uriBuilder.path("/transacao/{id}").buildAndExpand(transacao.getId()).toUri();
                return ResponseEntity.created(uri).body(new TransferirResponse(transacao));
            } else {
                throw new Exception("Saldo insuficiente!");
            }
        } else {
            throw new Exception("Alguma conta inestitente!");
        }
    }

    //deletarTransacao
    public ResponseEntity<?> deletarTransacao(Long id) {
        Optional<Transacao> optionalTransacao = transacaoRepository.findById(id);
        if (optionalTransacao.isPresent()) {
            Transacao transacao = optionalTransacao.get();
            Conta contaOperadora = transacao.getContaOperadora();
            Conta contaDestino = transacao.getContaDestino();

            contaOperadora.setSaldo(contaOperadora.getSaldo().add(transacao.getValor()));
            contaDestino.setSaldo(contaDestino.getSaldo().subtract(transacao.getValor()));

            contaRepository.save(contaOperadora);
            contaRepository.save(contaDestino);
            transacaoRepository.delete(transacao);

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //detalharPorId
    public ResponseEntity<TransferirResponse> detalharPorId(Long id) {
        Optional<Transacao> optionalTransacao = transacaoRepository.findById(id);
        if (optionalTransacao.isPresent()) {
            return ResponseEntity.ok(TransferirResponse.converterUmaTransacao(optionalTransacao.get()));
        }
        return ResponseEntity.notFound().build();
    }

    //ListarPorConta
    public Page<TransferirResponse> listarPorConta(Long numeroConta, Pageable paginacao) {
        if (numeroConta == null) {
            Page<Transacao> transacoes = transacaoRepository.findAll(paginacao);
            return TransferirResponse.converterTrasacoes(transacoes);
        } else {
            Page<Transacao> transacoes = transacaoRepository.findByContaNumero(numeroConta, paginacao);
            return TransferirResponse.converterTrasacoes(transacoes);
        }
    }

    //ListarTransacaoMaioresQue
    public Page<TransferirResponse> listarTransacaoMaiorQue(BigDecimal valorTransacao, Pageable paginacao) {
        if (valorTransacao != null) {
            Page<Transacao> transacoes = transacaoRepository.findMaiorQue(valorTransacao, paginacao);
            return TransferirResponse.converterTrasacoes(transacoes);
        } else {
            return null;
        }
    }

    //Auxiliar -- salvarTransferencia
    public Transacao salvarTransferencia(Conta contaOperadora, Conta contaDestino, TipoOperacao tipoOperacao,
                                    BigDecimal valor) {
        Transacao transacao = new Transacao(contaOperadora, contaDestino, tipoOperacao, valor, LocalDate.now());
        transacaoRepository.save(transacao);
        return transacao;
    }
}

