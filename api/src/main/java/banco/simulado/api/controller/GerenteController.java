package banco.simulado.api.controller;

import banco.simulado.api.domain.Gerente.GerenteRegister;
import banco.simulado.api.domain.Gerente.GerenteResponse;
import banco.simulado.api.service.GerenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/gerentes")
public class GerenteController {

    @Autowired
    private GerenteService gerenteService;

    @GetMapping
    public Page<GerenteResponse> listar(@RequestParam(required = false) String nomeGerente,
                                        @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable paginacao) {
        return gerenteService.listar(nomeGerente, paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GerenteResponse> detalhar(@PathVariable Long id) {
        return gerenteService.detalharPorId(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<GerenteResponse> cadastrar(@RequestBody @Valid GerenteRegister gerenteForm,
                                                UriComponentsBuilder uriBuilder) throws Exception {
        return gerenteService.cadastrarGerente(gerenteForm, uriBuilder);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<GerenteResponse> atualizar(@PathVariable Long id, @RequestBody @Valid GerenteRegister gerenteForm) throws Exception {
        return gerenteService.atualizar(id, gerenteForm);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remover(@PathVariable Long id) {
        return gerenteService.removerGerente(id);
    }


}