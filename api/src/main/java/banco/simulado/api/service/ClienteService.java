package banco.simulado.api.service;

import banco.simulado.api.domain.Cliente.Cliente;
import banco.simulado.api.domain.Cliente.ClienteRepository;
import banco.simulado.api.domain.Conta.Conta;
import banco.simulado.api.domain.Conta.ContaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.print.Pageable;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaRepository contaRepository;

    // get
    public Page<ClienteDto> listar(String nomeCliente, Pageablee paginacao) {
        if (nomeCliente == null) {
            Page<Cliente> clientes = clienteRepository.findAll(paginacao);
            return ClienteDto.converter(clientes);
        } else {
            Page<Cliente> clientes = clienteRepository.findByNome(nomeCliente, paginacao);
            return ClienteDto.converter(clientes);
        }
    }

    // get id
    public ResponseEntity<ClienteDto> detalharPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(ClienteDto.converterUmCliente(cliente.get()));
        }
        return ResponseEntity.notFound().build();
    }

    // cadastrar
    public ResponseEntity<ClienteDto> cadastrarCliente(@Valid ClienteForm clienteForm, UriComponentsBuilder uriBuilder)
            throws Exception {
        Cliente cliente = clienteForm.converter();
        Optional<Cliente> clienteOptional = clienteRepository.findByNomeOrCpf(cliente.getNome(),
                cliente.getCpf());
        if (clienteOptional.isEmpty()) {
            System.out.println("teste");
            clienteRepository.save(cliente);
            URI uri = uriBuilder.path("/gerentes/{id}").buildAndExpand(cliente.getId()).toUri();
            return ResponseEntity.created(uri).body(new ClienteDto(cliente));
        } else {
            throw new Exception("Cliente já existe");
        }
    }

    // atualizar
    public ResponseEntity<ClienteDto> atualizar(Long id, ClienteForm clienteForm) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteForm.atualizar(clienteOptional.get(), clienteRepository);
            return ResponseEntity.ok(new ClienteDto(cliente));
        }
        return ResponseEntity.notFound().build();
    }

    // delete
    public ResponseEntity<?> removerCliente(Long id) {
        Optional<Cliente> optinalCliente = clienteRepository.findById(id);
        if (optinalCliente.isPresent()) {
            clienteRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // retornar contas do usuário
    public List<Conta> listarContas(Long id) throws Exception {
        Optional<Cliente> optinalCliente = clienteRepository.findById(id);
        if (optinalCliente.isPresent()) {
            Cliente cliente = optinalCliente.get();
            return contaRepository.findByCliente(cliente);
        } else {
            throw new Exception("Cliente não existente");
        }
    }
}