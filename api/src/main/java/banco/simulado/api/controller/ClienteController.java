package banco.simulado.api.controller;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public Page<ClienteDto> listar(@RequestParam(required = false) String nomeCliente,
                                   @PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
        return clienteService.listar(nomeCliente, paginacao);
    }

    @GetMapping("/contas/{id}")
    public List<Conta> listarContas(@PathVariable Long id) throws Exception {
        return clienteService.listarContas(id);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> detalhar(@PathVariable Long id) {
        return clienteService.detalharPorId(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ClienteDto> cadastrar(@RequestBody @Valid ClienteForm clienteForm,
                                                UriComponentsBuilder uriBuilder) throws Exception {
        return clienteService.cadastrarCliente(clienteForm, uriBuilder);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ClienteDto> atualizar(@PathVariable Long id, @RequestBody @Valid ClienteForm clienteForm) {
        return clienteService.atualizar(id, clienteForm);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        return clienteService.removerCliente(id);
    }


}