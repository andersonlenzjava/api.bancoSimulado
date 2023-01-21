package banco.simulado.api.domain.Gerente;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Optional;

@Repository
public interface GerenteRepository extends JpaRepository<Gerente, Long> {

    Page<Gerente> findByNome(String nomeGerente, Pageable paginacao);

    Optional<Gerente> findByNomeOrCpf(String nome, String cpf);

    Optional<Gerente> findByCpf(String gerenteCpf);

    Optional<Gerente> findByNomeAndCpf(String gerenteNome, String gerenteCpf);

}