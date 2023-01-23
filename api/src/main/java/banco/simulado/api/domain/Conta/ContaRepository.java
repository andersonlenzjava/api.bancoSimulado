package banco.simulado.api.domain.Conta;

import banco.simulado.api.domain.Cliente.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    Page<Conta> findByNumero(Long numeroConta, Pageable paginacao);

    Optional<Conta> findByNumero(Long numero);

    Page<Conta> findByCliente(Cliente cliente, Pageable paginacao);

}
