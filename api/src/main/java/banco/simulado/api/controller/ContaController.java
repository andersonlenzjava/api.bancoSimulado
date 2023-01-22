package banco.simulado.api.controller;

import banco.simulado.api.domain.Conta.ContaRegister;
import banco.simulado.api.domain.Conta.ContaResponse;
import banco.simulado.api.service.ContaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @GetMapping
    public Page<ContaResponse> listar(@RequestParam(required = false) Long numeroConta,
                                      @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable paginacao) {
        return contaService.listar(numeroConta, paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaResponse> detalhar(@PathVariable Long id) {
        return contaService.detalharPorId(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ContaResponse> cadastrar(@RequestBody @Valid ContaRegister contaForm,
                                              UriComponentsBuilder uriBuilder) throws Exception {
        return contaService.cadastrarConta(contaForm, uriBuilder);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ContaResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ContaRegister contaForm) throws Exception {
        return contaService.atualizar(id, contaForm);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remover(@PathVariable Long id) {
        return contaService.removerCliente(id);
    }


}
