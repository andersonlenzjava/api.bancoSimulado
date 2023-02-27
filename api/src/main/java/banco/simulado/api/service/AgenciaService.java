package banco.simulado.api.service;

import banco.simulado.api.domain.Agencia.Agencia;
import banco.simulado.api.domain.Agencia.AgenciaRegister;
import banco.simulado.api.domain.Agencia.AgenciaRepository;
import banco.simulado.api.domain.Agencia.AgenciaResponse;
import banco.simulado.api.infra.exeption.ItemJaExisteException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class AgenciaService {

    @Autowired
    private AgenciaRepository agenciaRepository;

    //get
    public Page<AgenciaResponse> listar(String numeroAgencia, Pageable paginacao) {
        if (numeroAgencia == null) {
            Page<Agencia> agencias = agenciaRepository.findAll(paginacao);
            return AgenciaResponse.converter(agencias);
        } else {
            Page<Agencia> agencias = agenciaRepository.findByNumero(numeroAgencia, paginacao);
            return AgenciaResponse.converter(agencias);
        }
    }

    //get id
    public ResponseEntity<AgenciaResponse> detalharPorId(Long id) {
        Optional<Agencia> agencia = agenciaRepository.findById(id);
        if (agencia.isPresent()) {
            return ResponseEntity.ok(AgenciaResponse.converterUmaAgencia(agencia.get()));
        }
        return ResponseEntity.notFound().build();
    }

    //cadastrar
    public ResponseEntity<AgenciaResponse> cadastrarAgencia(AgenciaRegister agenciaRegister,
                                                       UriComponentsBuilder uriBuilder) throws Exception {
        Agencia agencia = agenciaRegister.converter();
        Optional<Agencia> agenciaOptional = agenciaRepository.findByNomeAndNumeroAndNumeroPredio(agencia.getNome(),
                agencia.getNumero(), agencia.getNumeroPredio());
        if (agenciaOptional.isEmpty()) {
            agenciaRepository.save(agencia);
            URI uri = uriBuilder.path("/agencias/{id}").buildAndExpand(agencia.getId()).toUri();
            return ResponseEntity.created(uri).body(new AgenciaResponse(agencia));
        } else {
            throw new ItemJaExisteException("Agencia já existe !!");
        }
    }

    //atualizar
    public ResponseEntity<AgenciaResponse> atualizarAgencia(Long id, AgenciaRegister agenciaRegister,
                                                       UriComponentsBuilder uriBuilder)  {
        Optional<Agencia> agenciaOptional = agenciaRepository.findById(id);
        if(agenciaOptional.isPresent()) {
            Agencia agencia = agenciaOptional.get();
            agencia.setCep(agenciaRegister.cep());
            agencia.setNome(agenciaRegister.nome());
            agencia.setNumero(agenciaRegister.numero());
            agencia.setNumeroPredio(agenciaRegister.numeroPredio());
            agencia.setRua(agenciaRegister.rua());

            return ResponseEntity.ok(new AgenciaResponse(agencia));
        }
        return ResponseEntity.notFound().build();
    }

    //deletar
    public ResponseEntity<?> removerAgencia(Long id) {
        Optional<Agencia> optinalAgencia = agenciaRepository.findById(id);
        if (optinalAgencia.isPresent()) {
            agenciaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
