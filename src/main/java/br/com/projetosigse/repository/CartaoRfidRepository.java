package br.com.projetosigse.repository;

import br.com.projetosigse.model.CartaoRfid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoRfidRepository extends JpaRepository<CartaoRfid, Long> {

    Optional<CartaoRfid> findByUidIgnoreCase(String uid);
}
