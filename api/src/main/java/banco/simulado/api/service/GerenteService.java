package banco.simulado.api.service;

import banco.simulado.api.domain.Agencia.Agencia;
import banco.simulado.api.domain.Agencia.AgenciaRepository;
import banco.simulado.api.domain.Gerente.Gerente;
import banco.simulado.api.domain.Gerente.GerenteRegister;
import banco.simulado.api.domain.Gerente.GerenteRepository;
import banco.simulado.api.domain.Gerente.GerenteResponse;
import banco.simulado.api.infra.exeption.ItemJaExisteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class GerenteService {

    @Autowired
    private GerenteRepository gerenteRepository;

    @Autowired
    private AgenciaRepository agenciaRepository;

    // get
    public Page<GerenteResponse> listar(String nomeGerente, Pageable paginacao) {
        if (nomeGerente == null) {
            Page<Gerente> gerentes = gerenteRepository.findAll(paginacao);
            return GerenteResponse.converter(gerentes);
        } else {
            Page<Gerente> gerentes = gerenteRepository.findByPessoaNome(nomeGerente, paginacao);
            return GerenteResponse.converter(gerentes);
        }
    }

    // get id
    public ResponseEntity<GerenteResponse> detalharPorId(Long id) {
        Optional<Gerente> gerente = gerenteRepository.findById(id);
        if (gerente.isPresent()) {
            return ResponseEntity.ok(GerenteResponse.converterUmGerente(gerente.get()));
        }
        return ResponseEntity.notFound().build();
    }

    // cadastrar
    public ResponseEntity<GerenteResponse> cadastrarGerente(GerenteRegister gerenteRegister, UriComponentsBuilder uriBuilder)
            throws Exception {

        // ver se a agência existe
        Optional<Agencia> agenciaOptional = agenciaRepository.findByNumero(gerenteRegister.agenciaNumero());
        Gerente gerente;
        if (agenciaOptional.isPresent()) {
            Agencia agencia = agenciaOptional.get();
            gerente = new Gerente(gerenteRegister.nome(), gerenteRegister.cpf(), gerenteRegister.dataNascimento(), agencia);

            // verificar se o gernete já existe
            Optional<Gerente> gerenteOptional = gerenteRepository.findByPessoaNomeOrPessoaCpf(gerente.getPessoa().getNome(),
                    gerente.getPessoa().getCpf());
            if (gerenteOptional.isEmpty()) {
                gerenteRepository.save(gerente);
                URI uri = uriBuilder.path("/gerentes/{id}").buildAndExpand(gerente.getId()).toUri();
                return ResponseEntity.created(uri).body(new GerenteResponse(gerente));
            } else {
                throw new ItemJaExisteException("Gerente já existe");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // atualizar
    public ResponseEntity<GerenteResponse> atualizar(Long id, GerenteRegister gerenteRegister) {

        // se o gerente existe
        Optional<Gerente> optionalGerente = gerenteRepository.findById(id);
        if (optionalGerente.isPresent()) {
            Gerente gerente = optionalGerente.get();

            // verifica se a agencia existe
            Optional<Agencia> optionalAgencia = agenciaRepository.findByNumero(gerenteRegister.agenciaNumero());
            if (optionalAgencia.isPresent()) {
                Agencia agencia = optionalAgencia.get();
                gerente.setAgencia(agencia);
                gerente.getPessoa().setNome(gerenteRegister.nome());
                gerente.getPessoa().setCpf(gerenteRegister.cpf());
                gerente.getPessoa().setDataNascimento(gerenteRegister.dataNascimento());
                return ResponseEntity.ok(new GerenteResponse(gerente));
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    // delete
    public ResponseEntity<?> removerGerente(Long id) {
        Optional<Gerente> optinalGerente = gerenteRepository.findById(id);
        if (optinalGerente.isPresent()) {
            gerenteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
