package banco.simulado.api.domain.Gerente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GerenteRepository extends JpaRepository<Gerente, Long> {

    Page<Gerente> findByPessoaNome(String nomeGerente, Pageable paginacao);

    Optional<Gerente> findByPessoaNomeAndPessoaCpf(String nome, String cpf);

    Optional<Gerente> findByPessoaCpf(String gerenteCpf);

//    Optional<Gerente> findByNomeAndCpf(String gerenteNome, String gerenteCpf);

//    Page<Gerente> findByNome(String nomeGerente, Pageable paginacao);
//
//    Optional<Gerente> findByNomeOrCpf(String nome, String cpf);
//
//    Optional<Gerente> findByCpf(String gerenteCpf);
//
//    Optional<Gerente> findByNomeAndCpf(String gerenteNome, String gerenteCpf);

}