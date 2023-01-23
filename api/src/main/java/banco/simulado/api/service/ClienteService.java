package banco.simulado.api.service;

import banco.simulado.api.domain.Cliente.Cliente;
import banco.simulado.api.domain.Cliente.ClienteRegister;
import banco.simulado.api.domain.Cliente.ClienteRepository;
import banco.simulado.api.domain.Cliente.ClienteResponse;
import banco.simulado.api.domain.Conta.Conta;
import banco.simulado.api.domain.Conta.ContaRepository;
import banco.simulado.api.domain.Conta.ContaResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaRepository contaRepository;

    // get
    public Page<ClienteResponse> listar(String nomeCliente, Pageable paginacao) {
        if (nomeCliente == null) {
            Page<Cliente> clientes = clienteRepository.findAll(paginacao);
            return ClienteResponse.converter(clientes);
        } else {
            Page<Cliente> clientes = clienteRepository.findByPessoaNome(nomeCliente, paginacao);
            return ClienteResponse.converter(clientes);
        }
    }

    // retornar contas do usuário
    public Page<ContaResponse> listarContasUsuario(Long id, Pageable paginacao) throws Exception {
        Optional<Cliente> optinalCliente = clienteRepository.findById(id);
        if (optinalCliente.isPresent()) {
            Cliente cliente = optinalCliente.get();
            Page<Conta> contas = contaRepository.findByCliente(cliente, paginacao);
            return ContaResponse.converter(contas);
        } else {
            throw new Exception("Cliente não existente");
        }
    }

    // get id
    public ResponseEntity<ClienteResponse> detalharPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(ClienteResponse.converterUmCliente(cliente.get()));
        }
        return ResponseEntity.notFound().build();
    }

    // cadastrar
    public ResponseEntity<ClienteResponse> cadastrarCliente(@Valid ClienteRegister clienteRegister, UriComponentsBuilder uriBuilder)
            throws Exception {
        Cliente cliente = clienteRegister.converter();
        Optional<Cliente> clienteOptional = clienteRepository.findByPessoaNomeOrPessoaCpf(cliente.getPessoa().getNome(),
                cliente.getPessoa().getCpf());
        if (clienteOptional.isEmpty()) {
            System.out.println("teste");
            clienteRepository.save(cliente);
            URI uri = uriBuilder.path("/gerentes/{id}").buildAndExpand(cliente.getId()).toUri();
            return ResponseEntity.created(uri).body(new ClienteResponse(cliente));
        } else {
            throw new Exception("Cliente já existe");
        }
    }

    // atualizar
    public ResponseEntity<ClienteResponse> atualizar(Long id, ClienteRegister clienteRegister) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteRegister.atualizar(clienteOptional.get(), clienteRepository);
            return ResponseEntity.ok(new ClienteResponse(cliente));
        }
        return ResponseEntity.notFound().build();
    }

    // delete
    public ResponseEntity<?> removerCliente(Long id) {
        Optional<Cliente> optinalCliente = clienteRepository.findById(id);
        if (optinalCliente.isPresent()) {
            clienteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}