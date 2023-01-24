package banco.simulado.api.service;

import banco.simulado.api.domain.Conta.Conta;
import banco.simulado.api.domain.Conta.ContaRepository;
import banco.simulado.api.domain.TipoOperacao.TipoOperacao;
import banco.simulado.api.domain.Transacao.*;
import banco.simulado.api.infra.exeption.SaldoInsuficienteException;
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
    public ResponseEntity<SacarDepositarResponse> depositar(DepositarRegister depositarRegister,
                                                            UriComponentsBuilder uriBuilder) throws Exception {
        Optional<Conta> contaOperadora = contaRepository.findByNumero(Long.valueOf(depositarRegister.numeroContaDepositar()));
        if (contaOperadora.isPresent()) {
            Conta conta = contaOperadora.get();
            conta.setSaldo(conta.getSaldo().add(depositarRegister.valorTransferir()));
            contaRepository.save(conta);

            Transacao transacao = salvarTransferencia(conta, conta, TipoOperacao.DEPOSITAR, depositarRegister.valorTransferir());
            URI uri = uriBuilder.path("/transacao/{id}").buildAndExpand(transacao.getId()).toUri();
            return ResponseEntity.created(uri).body(new SacarDepositarResponse(transacao));
        } else {
            return ResponseEntity.notFound().build();
//            throw new Exception("Conta inestitente!");
        }
    }

    //sacar
    public ResponseEntity<SacarDepositarResponse> sacar(SacarRegister sacarRegister,
                                                        UriComponentsBuilder uriBuilder) throws Exception {
        Optional<Conta> contaOperadora = contaRepository.findByNumero(Long.valueOf(sacarRegister.numeroContaSacar()));
        if (contaOperadora.isPresent()) {
            Conta conta = contaOperadora.get();

            if (((sacarRegister.valorTransferir().compareTo(conta.getSaldo())) < 0)) {
                conta.setSaldo(conta.getSaldo().subtract(sacarRegister.valorTransferir()));
                contaRepository.save(conta);

                Transacao transacao = salvarTransferencia(conta, conta, TipoOperacao.SACAR, sacarRegister.valorTransferir());
                URI uri = uriBuilder.path("/transacao/{id}").buildAndExpand(transacao.getId()).toUri();
                return ResponseEntity.created(uri).body(new SacarDepositarResponse(transacao));
            } else {
                throw new SaldoInsuficienteException("Saldo insuficiente!");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //transferir
    public ResponseEntity<TransferirResponse> transferir(TransferirRegister transferirRegister, UriComponentsBuilder uriBuilder) throws Exception {
        Optional<Conta> contaOptionalOperadora = contaRepository.findByNumero(Long.valueOf(transferirRegister.numeroContaTransferir()));
        Optional<Conta> contaOptionalDestino = contaRepository.findByNumero(Long.valueOf(transferirRegister.numeroContaReceber()));
        if ((contaOptionalOperadora.isPresent()) && (contaOptionalDestino.isPresent())) {
            Conta contaOperadora = contaOptionalOperadora.get();
            Conta contaDestino = contaOptionalDestino.get();

            if (((transferirRegister.valorTransferir().compareTo(contaOperadora.getSaldo())) < 0)) {
                contaOperadora.setSaldo(contaOperadora.getSaldo().subtract(transferirRegister.valorTransferir()));
                contaDestino.setSaldo(contaDestino.getSaldo().add(transferirRegister.valorTransferir()));
                contaRepository.save(contaOperadora);
                contaRepository.save(contaDestino);

                 Transacao transacao = salvarTransferencia(contaOperadora, contaDestino, TipoOperacao.TRANSFERIR,
                        transferirRegister.valorTransferir());
                URI uri = uriBuilder.path("/transacao/{id}").buildAndExpand(transacao.getId()).toUri();
                return ResponseEntity.created(uri).body(new TransferirResponse(transacao));
            } else {
                 throw new SaldoInsuficienteException("Saldo insuficiente!");
            }
        } else {
            return ResponseEntity.notFound().build();
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

//    ---------------------------------------------------------------------------------------

    //Auxiliar -- salvarTransferencia
    public Transacao salvarTransferencia(Conta contaOperadora, Conta contaDestino, TipoOperacao tipoOperacao,
                                    BigDecimal valor) {
        Transacao transacao = new Transacao(contaOperadora, contaDestino, tipoOperacao, valor, LocalDate.now());
        transacaoRepository.save(transacao);
        return transacao;
    }
}

