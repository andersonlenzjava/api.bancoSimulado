package banco.simulado.api.service;

import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    //depositar
    public ResponseEntity<SacarDepositarDto> depositar(SacarDepositarForm sacarDepositarForm,
                                                       UriComponentsBuilder uriBuilder) throws Exception {
        Optional<Conta> contaOperadora = contaRepository.findByNumero(sacarDepositarForm.getNumeroContaTransferir());
        if (contaOperadora.isPresent()) {
            Conta conta = contaOperadora.get();
            conta.setSaldo(conta.getSaldo().add(sacarDepositarForm.getValorTransferir()));
            contaRepository.save(conta);

            salvarTransferencia(conta, conta, TipoOperacao.DEPOSITAR, sacarDepositarForm.getValorTransferir());
            return ResponseEntity.ok(new SacarDepositarDto(conta, TipoOperacao.DEPOSITAR,
                    sacarDepositarForm.getValorTransferir(), LocalDate.now()));
        } else {
            throw new Exception("Conta inestitente!");
        }
    }

    //sacar
    public ResponseEntity<SacarDepositarDto> sacar(SacarDepositarForm sacarDepositarForm,
                                                   UriComponentsBuilder uriBuilder) throws Exception {
        Optional<Conta> contaOperadora = contaRepository.findByNumero(sacarDepositarForm.getNumeroContaTransferir());
        if (contaOperadora.isPresent()) {
            Conta conta = contaOperadora.get();

            if (((sacarDepositarForm.getValorTransferir().compareTo(conta.getSaldo())) < 0)) {
                conta.setSaldo(conta.getSaldo().subtract(sacarDepositarForm.getValorTransferir()));
                contaRepository.save(conta);

                salvarTransferencia(conta, conta, TipoOperacao.SACAR, sacarDepositarForm.getValorTransferir());
                return ResponseEntity.ok(new SacarDepositarDto(conta, TipoOperacao.SACAR,
                        sacarDepositarForm.getValorTransferir(), LocalDate.now()));
            } else {
                throw new Exception("Saldo insuficiente!");
            }
        } else {
            throw new Exception("Conta inestitente!");
        }
    }

    //transferir
    public ResponseEntity<TransferirDto> transferir(TransferirForm transferirForm, UriComponentsBuilder uriBuilder)
            throws Exception {
        Optional<Conta> contaOptionalOperadora = contaRepository
                .findByNumero(transferirForm.getNumeroContaTransferir());
        Optional<Conta> contaOptionalDestino = contaRepository.findByNumero(transferirForm.getNumeroContaReceber());
        if ((contaOptionalOperadora.isPresent()) && (contaOptionalDestino.isPresent())) {
            Conta contaOperadora = contaOptionalOperadora.get();
            Conta contaDestino = contaOptionalDestino.get();

            if (((transferirForm.getValorTransferir().compareTo(contaOperadora.getSaldo())) < 0)) {
                contaOperadora.setSaldo(contaOperadora.getSaldo().subtract(transferirForm.getValorTransferir()));
                contaDestino.setSaldo(contaDestino.getSaldo().add(transferirForm.getValorTransferir()));
                contaRepository.save(contaOperadora);
                contaRepository.save(contaDestino);

                salvarTransferencia(contaOperadora, contaDestino, TipoOperacao.TRANSFERIR,
                        transferirForm.getValorTransferir());
                return ResponseEntity.ok(new TransferirDto(contaOperadora, contaDestino, TipoOperacao.TRANSFERIR,
                        transferirForm.getValorTransferir(), LocalDate.now()));
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

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    //detalharPorId
    public ResponseEntity<TransferirDto> detalharPorId(Long id) {
        Optional<Transacao> optionalTransacao = transacaoRepository.findById(id);
        if (optionalTransacao.isPresent()) {
            return ResponseEntity.ok(TransferirDto.converterUmaTransacao(optionalTransacao.get()));
        }
        return ResponseEntity.notFound().build();
    }

    //ListarPorConta
    public Page<TransferirDto> listarPorConta(Long numeroConta, Pageable paginacao) {
        if (numeroConta == null) {
            Page<Transacao> transacoes = transacaoRepository.findAll(paginacao);
            return TransferirDto.converterTrasacoes(transacoes);
        } else {
            Page<Transacao> transacoes = transacaoRepository.findByContaNumero(numeroConta, paginacao);
            return TransferirDto.converterTrasacoes(transacoes);
        }
    }

    //ListarTransacaoMaioresQue
    public Page<TransferirDto> listarTransacaoMaiorQue(BigDecimal valorTransacao, Pageable paginacao) {
        if (valorTransacao != null) {
            Page<Transacao> transacoes = transacaoRepository.findMaiorQue(valorTransacao, paginacao);
            return TransferirDto.converterTrasacoes(transacoes);
        } else {
            return null;
        }
    }

    //Auxiliar -- salvarTransferencia
    public void salvarTransferencia(Conta contaOperadora, Conta contaDestino, TipoOperacao tipoOperacao,
                                    BigDecimal valor) {
        Transacao transacao = new Transacao(contaOperadora, contaDestino, tipoOperacao, valor, LocalDate.now());
        transacaoRepository.save(transacao);
    }
}

