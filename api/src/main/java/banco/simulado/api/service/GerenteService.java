package banco.simulado.api.service;

import banco.simulado.api.domain.Agencia.AgenciaRepository;
import banco.simulado.api.domain.Gerente.Gerente;
import banco.simulado.api.domain.Gerente.GerenteRegister;
import banco.simulado.api.domain.Gerente.GerenteRepository;
import banco.simulado.api.domain.Gerente.GerenteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.print.Pageable;
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
            Page<Gerente> gerentes = gerenteRepository.findByNome(nomeGerente, paginacao);
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
    public ResponseEntity<GerenteResponse> cadastrarGerente(GerenteRegister gerenteForm, UriComponentsBuilder uriBuilder)
            throws Exception {
        Gerente gerente = gerenteForm.converter(agenciaRepository);
        Optional<Gerente> gerenteOptional = gerenteRepository.findByNomeOrCpf(gerente.getPessoa().getNome(),
                gerente.getPessoa().getCpf());
        if (gerenteOptional.isEmpty()) {
            gerenteRepository.save(gerente);
            URI uri = uriBuilder.path("/gerentes/{id}").buildAndExpand(gerente.getId()).toUri();
            return ResponseEntity.created(uri).body(new GerenteResponse(gerente));
        } else {
            throw new Exception("Gerente já existe");
        }
    }

    // atualizar
    public ResponseEntity<GerenteResponse> atualizar(Long id, GerenteRegister gerenteForm) throws Exception {
        Optional<Gerente> optionalGerente = gerenteRepository.findById(id);
        if (optionalGerente.isPresent()) {
            Gerente gerente = gerenteForm.atualizar(optionalGerente.get(), agenciaRepository);
            return ResponseEntity.ok(new GerenteResponse(gerente));
        }
        return ResponseEntity.notFound().build();
    }

    // delete
    public ResponseEntity<?> removerGerente(Long id) {
        Optional<Gerente> optinalGerente = gerenteRepository.findById(id);
        if (optinalGerente.isPresent()) {
            gerenteRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
