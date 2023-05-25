package com.example.datshopspring2.services.impl;

import com.example.datshopspring2.exceptions.UserNotFoundException;
import com.example.datshopspring2.models.*;
import com.example.datshopspring2.models.enums.AuthenticationProvider;
import com.example.datshopspring2.repositories.UserRepository;
import com.example.datshopspring2.repositories.BookRepository;
import com.example.datshopspring2.repositories.OrderInformationRepository;
import com.example.datshopspring2.repositories.OrderRepository;
import com.example.datshopspring2.security.details.UserDetailsServiceImpl;
import com.example.datshopspring2.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final OrderInformationRepository orderInformationRepository;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private final BookRepository bookRepository;

    @Override
    public List<Order> findAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public void saveOrder(Integer total, UserProfile userProfile, List<Book> bookList, List<Integer> quantity) {
        Order order = Order.builder()
                .date(OffsetDateTime.now())
                .price(total)
                .user(userDetailsServiceImpl.findAccountByAccountId(userProfile.getProfileId()))
                .userName(userProfile.getUserName())
                .build();
        long orderId = orderRepository.save(order).getId();

        saveInformationOrder(orderId, bookList, quantity);

    }

    @Override
    public void saveInformationOrder(long orderId, List<Book> bookList, List<Integer> quantity) {
        for (int i = 0; i < bookList.size(); i++) {
            OrderInformation orderInformation = OrderInformation.builder()
                    .order(orderRepository.findById(orderId).get())
                    .title(bookList.get(i).getTitle())
                    .image(bookList.get(i).getImage())
                    .price(bookList.get(i).getPrice() * quantity.get(i))
                    .quantity(quantity.get(i))
                    .build();
            orderInformationRepository.save(orderInformation);

            bookList.get(i).setQuantitySold(bookList.get(i).getQuantitySold() + quantity.get(i));
            bookRepository.save(bookList.get(i));
        }
    }

    @Override
    public List<Order> findAllOrderByEmailAndAuth(String email, AuthenticationProvider auth) {
        User user = userRepository.findUserByEmailAndAuthenticationProvider(email, auth).orElseThrow(
                () -> new UserNotFoundException("User not found", 404)
        );

        return orderRepository.findAllByUser(user);
    }
}
