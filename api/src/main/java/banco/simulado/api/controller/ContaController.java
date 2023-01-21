package banco.simulado.api.controller;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @GetMapping
    public Page<ContaDto> listar(@RequestParam(required = false) Long numeroConta,
                                 @PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
        return contaService.listar(numeroConta, paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDto> detalhar(@PathVariable Long id) {
        return contaService.detalharPorId(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ContaDto> cadastrar(@RequestBody @Valid ContaForm contaForm,
                                              UriComponentsBuilder uriBuilder) throws Exception {
        return contaService.cadastrarConta(contaForm, uriBuilder);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ContaDto> atualizar(@PathVariable Long id, @RequestBody @Valid ContaForm contaForm) throws Exception {
        return contaService.atualizar(id, contaForm);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        return contaService.removerCliente(id);
    }


}
