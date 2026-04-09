package com.github.BeatrizRosa05.ms.pagamentos.repository;

import com.github.BeatrizRosa05.ms.pagamentos.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
