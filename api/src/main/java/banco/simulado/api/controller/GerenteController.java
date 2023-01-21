package banco.simulado.api.controller;

@RestController
@RequestMapping("/gerentes")
public class GerenteController {

    @Autowired
    private GerenteService gerenteService;

    @GetMapping
    public Page<GerenteDto> listar(@RequestParam(required = false) String nomeGerente,
                                   @PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
        return gerenteService.listar(nomeGerente, paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GerenteDto> detalhar(@PathVariable Long id) {
        return gerenteService.detalharPorId(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<GerenteDto> cadastrar(@RequestBody @Valid GerenteForm gerenteForm,
                                                UriComponentsBuilder uriBuilder) throws Exception {
        return gerenteService.cadastrarGerente(gerenteForm, uriBuilder);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<GerenteDto> atualizar(@PathVariable Long id, @RequestBody @Valid GerenteForm gerenteForm) throws Exception {
        return gerenteService.atualizar(id, gerenteForm);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        return gerenteService.removerGerente(id);
    }


}