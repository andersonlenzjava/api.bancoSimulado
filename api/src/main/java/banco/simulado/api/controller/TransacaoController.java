package banco.simulado.api.controller;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping("/depositar")
    @Transactional
    public ResponseEntity<SacarDepositarDto> depositar(@RequestBody @Valid SacarDepositarForm sacarDepositarForm,
                                                       UriComponentsBuilder uriBuilder) throws Exception {
        return transacaoService.depositar(sacarDepositarForm, uriBuilder);
    }

    @PostMapping("/sacar")
    @Transactional
    public ResponseEntity<SacarDepositarDto> sacar(@RequestBody @Valid SacarDepositarForm sacarDepositarForm,
                                                   UriComponentsBuilder uriBuilder) throws Exception {
        return transacaoService.sacar(sacarDepositarForm, uriBuilder);
    }

    @PostMapping("/transferir")
    @Transactional
    public ResponseEntity<TransferirDto> transferir(@RequestBody @Valid TransferirForm transferirForm,
                                                    UriComponentsBuilder uriBuilder) throws Exception {
        return transacaoService.transferir(transferirForm, uriBuilder);
    }

    //retorna as transacoes por numero da conta
    @GetMapping
    public Page<TransferirDto> listarPorConta(@RequestParam(required = false) Long numeroConta,
                                              @PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
        return transacaoService.listarPorConta(numeroConta, paginacao);
    }

    //retorna por transacao
    @GetMapping("/{id}")
    public ResponseEntity<TransferirDto> detalharId(@PathVariable Long id) {
        return transacaoService.detalharPorId(id);
    }

    @GetMapping("/maioresQue")
    public Page<TransferirDto> listarPorValor(@RequestParam(required = true) BigDecimal valorTransacao,
                                              @PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
        return transacaoService.listarTransacaoMaiorQue(valorTransacao, paginacao);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> removerTransacao(@PathVariable Long id) {
        return transacaoService.deletarTransacao(id);
    }
}
