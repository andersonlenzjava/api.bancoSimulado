package banco.simulado.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agencias")
public class AgenciaController {

    @Autowired
    private AgenciaService agenciaService;

    @GetMapping
    public Page<AgenciaDto> listar(@RequestParam(required = false) String numeroAgencia,
                                   @PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
        return agenciaService.listar(numeroAgencia, paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgenciaDto> detalhar(@PathVariable Long id) {
        return agenciaService.detalharPorId(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<AgenciaDto> cadastrar(@RequestBody @Valid AgenciaForm agenciaForm,
                                                UriComponentsBuilder uriBuilder) throws Exception {
        return agenciaService.cadastrarAgencia(agenciaForm, uriBuilder);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AgenciaDto> atualizar(@PathVariable Long id, @RequestBody @Valid AgenciaForm agenciaForm,
                                                UriComponentsBuilder uriBuilder) throws Exception {
        return agenciaService.atualizarAgencia(id, agenciaForm, uriBuilder);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        return agenciaService.removerAgencia(id);
    }
}