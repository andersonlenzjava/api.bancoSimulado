package banco.simulado.api.service;

import banco.simulado.api.domain.Agencia.AgenciaRepository;
import banco.simulado.api.domain.Cliente.ClienteRepository;
import banco.simulado.api.domain.Conta.Conta;
import banco.simulado.api.domain.Conta.ContaRegister;
import banco.simulado.api.domain.Conta.ContaRepository;
import banco.simulado.api.domain.Conta.ContaResponse;
import banco.simulado.api.domain.Gerente.GerenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.print.Pageable;
import java.net.URI;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private AgenciaRepository agenciaRepository;

    @Autowired
    private GerenteRepository gerenteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // get
    public Page<ContaResponse> listar(Long numeroConta, Pageable paginacao) {
        if (numeroConta == null) {
            Page<Conta> contas = contaRepository.findAll(paginacao);
            return ContaResponse.converter(contas);
        } else {
            Page<Conta> contas = contaRepository.findByNumero(numeroConta, paginacao);
            return ContaResponse.converter(contas);
        }
    }

    // get id
    public ResponseEntity<ContaResponse> detalharPorId(Long id) {
        Optional<Conta> contaOptional = contaRepository.findById(id);
        if (contaOptional.isPresent()) {
            return ResponseEntity.ok(ContaResponse.converterUmaConta(contaOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    // cadastrar
    public ResponseEntity<ContaResponse> cadastrarConta(ContaRegister contaForm, UriComponentsBuilder uriBuilder)
            throws Exception {
        Optional<Conta> contaOptional = contaRepository.findByNumero(contaForm.numero());
        if (contaOptional.isEmpty()) {
            Conta conta = contaForm.converter(agenciaRepository, gerenteRepository, clienteRepository);
            contaRepository.save(conta);
            URI uri = uriBuilder.path("/conta/{id}").buildAndExpand(conta.getId()).toUri();
            return ResponseEntity.created(uri).body(new ContaResponse(conta));
        } else {
            throw new Exception("Conta já existe");
        }
    }

    // atualizar
    public ResponseEntity<ContaResponse> atualizar(Long id, ContaRegister contaForm) throws Exception {
        Optional<Conta> contaOptional = contaRepository.findById(id);
        if (contaOptional.isPresent()) {
            Conta conta = contaForm.atualizar(contaOptional.get(), agenciaRepository);
            return ResponseEntity.ok(new ContaResponse(conta));
        }
        return ResponseEntity.notFound().build();
    }

    // delete
    public ResponseEntity<?> removerCliente(Long id) {
        Optional<Conta> optinalCliente = contaRepository.findById(id);
        if (optinalCliente.isPresent()) {
            contaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}