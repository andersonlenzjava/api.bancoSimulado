package banco.simulado.api.controller;

import banco.simulado.api.domain.Cliente.ClienteRegister;
import banco.simulado.api.domain.Cliente.ClienteResponse;
import banco.simulado.api.domain.Conta.ContaResponse;
import banco.simulado.api.service.ClienteService;
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
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public Page<ClienteResponse> listar(@RequestParam(required = false) String nomeCliente,
                                        @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable paginacao) {
        return clienteService.listar(nomeCliente, paginacao);
    }

    @GetMapping("/contas/{id}")
    public Page<ContaResponse> listarContasUsuario(@PathVariable Long id) throws Exception {
        return clienteService.listarContasUsuario(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> detalhar(@PathVariable Long id) {
        return clienteService.detalharPorId(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ClienteResponse> cadastrar(@RequestBody @Valid ClienteRegister clienteForm,
                                                UriComponentsBuilder uriBuilder) throws Exception {
        return clienteService.cadastrarCliente(clienteForm, uriBuilder);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ClienteRegister clienteForm) {
        return clienteService.atualizar(id, clienteForm);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remover(@PathVariable Long id) {
        return clienteService.removerCliente(id);
    }


}