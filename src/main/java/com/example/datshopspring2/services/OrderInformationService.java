package com.example.datshopspring2.services;

import com.example.datshopspring2.models.Book;
import com.example.datshopspring2.models.Order;
import com.example.datshopspring2.models.OrderInformation;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderInformationService {

    List<OrderInformation> findInformationOrderByOrderId(Long orderId);
}
