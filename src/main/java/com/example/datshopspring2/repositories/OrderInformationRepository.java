package com.example.datshopspring2.repositories;

import com.example.datshopspring2.models.Book;
import com.example.datshopspring2.models.Order;
import com.example.datshopspring2.models.OrderInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderInformationRepository extends JpaRepository<OrderInformation, Long> {

    @Query("select o from OrderInformation o where o.order.id = ?1 order by o.id")
    List<OrderInformation> findInformationOrderByOrderId(Long orderId);
}