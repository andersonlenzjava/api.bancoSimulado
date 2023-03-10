package banco.simulado.api.controller;

import banco.simulado.api.domain.Gerente.GerenteDelTransacaoRegister;
import banco.simulado.api.domain.Transacao.*;
import banco.simulado.api.infra.exeption.GerenteDeOutraAgenciaException;
import banco.simulado.api.service.TransacaoService;
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

import java.math.BigDecimal;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping("/depositar")
    @Transactional
    public ResponseEntity<SacarDepositarResponse> depositar(@RequestBody DepositarRegister depositarRegister,
                                                            UriComponentsBuilder uriBuilder) throws Exception {


        return transacaoService.depositar(depositarRegister, uriBuilder);
    }

    @PostMapping("/sacar")
    @Transactional
    public ResponseEntity<SacarDepositarResponse> sacar(@RequestBody @Valid SacarRegister sacarRegister,
                                                   UriComponentsBuilder uriBuilder) throws Exception {
        return transacaoService.sacar(sacarRegister, uriBuilder);
    }

    @PostMapping("/transferir")
    @Transactional
    public ResponseEntity<TransferirResponse> transferir(@RequestBody @Valid TransferirRegister transferirRegister,
                                                         UriComponentsBuilder uriBuilder) throws Exception {
        return transacaoService.transferir(transferirRegister, uriBuilder);
    }

    //retorna as transacoes por numero da conta
    @GetMapping
    public Page<TransferirResponse> listarPorConta(@RequestParam(required = false) Long numeroConta,
                                                   @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable paginacao) {
        return transacaoService.listarPorConta(numeroConta, paginacao);
    }

    //retorna por transacao
    @GetMapping("/{id}")
    public ResponseEntity<TransferirResponse> detalharId(@PathVariable Long id) {
        return transacaoService.detalharPorId(id);
    }

    @GetMapping("/maioresQue")
    public Page<TransferirResponse> listarPorValor(@RequestParam(required = true) BigDecimal valorTransacao,
                                                   @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable paginacao) {
        return transacaoService.listarTransacaoMaiorQue(valorTransacao, paginacao);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity removerTransacao(@RequestBody @Valid GerenteDelTransacaoRegister gerenteDelTransacaoRegister, @PathVariable Long id) throws GerenteDeOutraAgenciaException {
        return transacaoService.deletarTransacao(gerenteDelTransacaoRegister, id);
    }
}
