package banco.simulado.api.domain.Conta;

import banco.simulado.api.domain.Cliente.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    Page<Conta> findByNumero(Long numeroConta, Pageable paginacao);

    Optional<Conta> findByNumero(Long numero);

    List<Conta> findByCliente(Cliente cliente);

}
