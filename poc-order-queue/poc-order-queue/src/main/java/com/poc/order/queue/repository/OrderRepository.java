package com.poc.order.queue.repository;

import com.poc.order.queue.domain.Order_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order_, Long> {
}
