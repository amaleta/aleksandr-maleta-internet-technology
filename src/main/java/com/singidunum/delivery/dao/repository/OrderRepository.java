package com.singidunum.delivery.dao.repository;

import com.singidunum.delivery.dao.entity.OrderEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByStatus(String status);
}
