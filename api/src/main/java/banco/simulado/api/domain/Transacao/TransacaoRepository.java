package banco.simulado.api.domain.Transacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("SELECT u FROM Transacao u WHERE u.contaOperadora.numero = :numeroConta "
            + "OR u.contaDestino.numero = :numeroConta")
    Page<Transacao> findByContaNumero(@Param("numeroConta") Long numeroConta, Pageable paginacao);

    @Query("SELECT u FROM Transacao u WHERE u.valor >= :valorTransacao")
    Page<Transacao> findMaiorQue(BigDecimal valorTransacao, Pageable paginacao);

}