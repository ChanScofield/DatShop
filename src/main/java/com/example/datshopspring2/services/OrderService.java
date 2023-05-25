package com.example.datshopspring2.services;

import com.example.datshopspring2.dto.BookDto;
import com.example.datshopspring2.models.Book;
import com.example.datshopspring2.models.Order;
import com.example.datshopspring2.models.UserProfile;
import com.example.datshopspring2.models.enums.AuthenticationProvider;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrder();

    void saveOrder(Integer total, UserProfile userProfile, List<Book> bookList, List<Integer> quantity);

    void saveInformationOrder(long orderId, List<Book> bookList, List<Integer> quantity);

    List<Order> findAllOrderByEmailAndAuth(String email, AuthenticationProvider auth);
}
