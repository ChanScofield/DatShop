package com.example.datshopspring2.services.impl;

import com.example.datshopspring2.models.Book;
import com.example.datshopspring2.models.Order;
import com.example.datshopspring2.models.OrderInformation;
import com.example.datshopspring2.repositories.OrderInformationRepository;
import com.example.datshopspring2.services.OrderInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderInformationServiceImpl implements OrderInformationService {

    @Autowired
    private OrderInformationRepository orderInformationRepository;


    @Override
    public List<OrderInformation> findInformationOrderByOrderId(Long orderId) {
        return orderInformationRepository.findInformationOrderByOrderId(orderId);
    }
}
