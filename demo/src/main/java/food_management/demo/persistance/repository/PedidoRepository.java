package food_management.demo.persistance.repository;

import food_management.demo.persistance.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<PedidoEntity,Long> {
}
