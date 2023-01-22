package banco.simulado.api.domain.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Page<Cliente> findByNome(String nomeCliente, Pageable paginacao);

    Optional<Cliente> findByNomeOrCpf(String nome, String cpf);

    Optional<Cliente> findByCpf(String cpfCliente);

}
