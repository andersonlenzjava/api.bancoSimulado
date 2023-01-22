package banco.simulado.api.service;

import banco.simulado.api.domain.Agencia.AgenciaRepository;
import banco.simulado.api.domain.Cliente.ClienteRepository;
import banco.simulado.api.domain.Conta.Conta;
import banco.simulado.api.domain.Conta.ContaRepository;
import banco.simulado.api.domain.Gerente.GerenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Page<ContaDto> listar(Long numeroConta, Pageable paginacao) {
        if (numeroConta == null) {
            Page<Conta> contas = contaRepository.findAll(paginacao);
            return ContaDto.converter(contas);
        } else {
            Page<Conta> contas = contaRepository.findByNumero(numeroConta, paginacao);
            return ContaDto.converter(contas);
        }
    }

    // get id
    public ResponseEntity<ContaDto> detalharPorId(Long id) {
        Optional<Conta> contaOptional = contaRepository.findById(id);
        if (contaOptional.isPresent()) {
            return ResponseEntity.ok(ContaDto.converterUmaConta(contaOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    // cadastrar
    public ResponseEntity<ContaDto> cadastrarConta(ContaForm contaForm, UriComponentsBuilder uriBuilder)
            throws Exception {
        Optional<Conta> contaOptional = contaRepository.findByNumero(contaForm.getNumero());
        if (contaOptional.isEmpty()) {
            Conta conta = contaForm.converter(agenciaRepository, gerenteRepository, clienteRepository);
            contaRepository.save(conta);
            URI uri = uriBuilder.path("/conta/{id}").buildAndExpand(conta.getId()).toUri();
            return ResponseEntity.created(uri).body(new ContaDto(conta));
        } else {
            throw new Exception("Conta já existe");
        }
    }

    // atualizar
    public ResponseEntity<ContaDto> atualizar(Long id, ContaForm contaForm) throws Exception {
        Optional<Conta> contaOptional = contaRepository.findById(id);
        if (contaOptional.isPresent()) {
            Conta conta = contaForm.atualizar(contaOptional.get(), agenciaRepository);
            return ResponseEntity.ok(new ContaDto(conta));
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