package banco.simulado.api.controller;

import banco.simulado.api.domain.Agencia.AgenciaRegister;
import banco.simulado.api.domain.Agencia.AgenciaResponse;
import banco.simulado.api.service.AgenciaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/agencias")
public class AgenciaController {

    @Autowired
    private AgenciaService agenciaService;

    @GetMapping
    public Page<AgenciaResponse> listar(@RequestParam(required = false) String numeroAgencia,
                                        @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable paginacao) {
        return agenciaService.listar(numeroAgencia, paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgenciaResponse> detalhar(@PathVariable Long id) {
        return agenciaService.detalharPorId(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<AgenciaResponse> cadastrar(@RequestBody @Valid AgenciaRegister agenciaRegister,
                                                UriComponentsBuilder uriBuilder) throws Exception {
        return agenciaService.cadastrarAgencia(agenciaRegister, uriBuilder);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AgenciaResponse> atualizar(@PathVariable Long id, @RequestBody @Valid AgenciaRegister agenciaRegister,
                                                UriComponentsBuilder uriBuilder) throws Exception {
        return agenciaService.atualizarAgencia(id, agenciaRegister, uriBuilder);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remover(@PathVariable Long id) {
        return agenciaService.removerAgencia(id);
    }
}