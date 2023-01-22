package banco.simulado.api.service;

import banco.simulado.api.domain.Gerente.Gerente;
import banco.simulado.api.domain.Gerente.GerenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.print.Pageable;
import java.util.Optional;

@Service
public class GerenteService {

    @Autowired
    private GerenteRepository gerenteRepository;

    @Autowired
    private AgenciaRepository agenciaRepository;

    // get
    public Page<GerenteDto> listar(String nomeGerente, Pageable paginacao) {
        if (nomeGerente == null) {
            Page<Gerente> gerentes = gerenteRepository.findAll(paginacao);
            return GerenteDto.converter(gerentes);
        } else {
            Page<Gerente> gerentes = gerenteRepository.findByNome(nomeGerente, paginacao);
            return GerenteDto.converter(gerentes);
        }
    }

    // get id
    public ResponseEntity<GerenteDto> detalharPorId(Long id) {
        Optional<Gerente> gerente = gerenteRepository.findById(id);
        if (gerente.isPresent()) {
            return ResponseEntity.ok(GerenteDto.converterUmGerente(gerente.get()));
        }
        return ResponseEntity.notFound().build();
    }

    // cadastrar
    public ResponseEntity<GerenteDto> cadastrarGerente(GerenteForm gerenteForm, UriComponentsBuilder uriBuilder)
            throws Exception {
        Gerente gerente = gerenteForm.converter(agenciaRepository);
        Optional<Gerente> gerenteOptional = gerenteRepository.findByNomeOrCpf(gerente.getNome(),
                gerente.getCpf());
        if (gerenteOptional.isEmpty()) {
            gerenteRepository.save(gerente);
            URI uri = uriBuilder.path("/gerentes/{id}").buildAndExpand(gerente.getId()).toUri();
            return ResponseEntity.created(uri).body(new GerenteDto(gerente));
        } else {
            throw new Exception("Gerente já existe");
        }
    }

    // atualizar
    public ResponseEntity<GerenteDto> atualizar(Long id, GerenteForm gerenteForm) throws Exception {
        Optional<Gerente> optionalGerente = gerenteRepository.findById(id);
        if (optionalGerente.isPresent()) {
            Gerente gerente = gerenteForm.atualizar(optionalGerente.get(), agenciaRepository);
            return ResponseEntity.ok(new GerenteDto(gerente));
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
