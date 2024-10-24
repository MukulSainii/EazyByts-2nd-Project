package com.RODS.repository;



import com.RODS.entity.Orders;
import com.RODS.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findOrdersByLogin_Id(Long id);
    List<Orders> findOrdersByOrderByIdAsc();

    Optional<Orders> findByIdAndLogin_IdAndStatus(Long id, Long loginId, Status status);

    @Modifying
    @Query("UPDATE Orders o SET o.status = :status WHERE o.id = :id")
    void updateStatus(@Param(value = "id") Long id,
                      @Param(value = "status") Status status);
}
