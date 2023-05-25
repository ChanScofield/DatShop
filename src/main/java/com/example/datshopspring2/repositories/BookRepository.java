package com.example.datshopspring2.repositories;

import com.example.datshopspring2.models.User;
import com.example.datshopspring2.models.Book;
import com.example.datshopspring2.models.Categories;
import com.example.datshopspring2.models.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b order by b.id")
    List<Book> findAll();

    @Query("select b from Book b where b.state = ?1 order by b.id")
    Page<Book> findAllByStateOrderById(Pageable pageable, State state);

    @Query("select b from Book b where b.seller = ?1 and b.state = ?2 order by b.id")
    Page<Book> findAllBySellerAndState(Pageable pageable, User user, State state);

    Optional<Book> findFirstByStateOrderByIdDesc(State state);

    Optional<Book> findFirstByStateOrderByQuantitySoldDesc(State state);

    @Query(value = "select * from book where id = :id and state in (:state)", nativeQuery = true)
    Optional<Book> findBookByIdAndState(Long id, List<String> state);

    List<Book> findAllByStateAndCategoriesId(State state, Categories categoriesId);

    @Query(value = "select b from Book b where b.state = ?1 and b.title ilike ?2")
    List<Book> findAllByStateAndTitleIsLike(State state, String title);

    @Query("select b from Book b where b.seller = ?1 and b.state = ?2 order by b.id")
    List<Book> findAllBySellerAndState(User seller, State state);

    @Query(value = "select b from Book b where b.state = ?1 order by b.id limit 12")
    List<Book> findAllByStateLimit12(State state);

    @Query(value = "select * from book where state = :state order by id offset :amount rows fetch next 3 rows only", nativeQuery = true)
    List<Book> findNext3Book(Integer amount, String state);

    @Query(value = "select * from book where categories_id = :categoriesId and state = :state order by id offset :amount rows fetch next 3 rows only", nativeQuery = true)
    List<Book> findNext3ByCategoriesId(Integer amount, Long categoriesId, String state);

    @Query(value = "select * from book where price = (select max(price) from book) and state in (:states)", nativeQuery = true)
    List<Book> findAllByMaxPrice(List<String> states);

    @Query(value = "select * from book where quantitySold = (select max(quantitySold) from book) and state in (:states)", nativeQuery = true)
    List<Book> findAllByMostSold(List<String> states);

    @Query(value = "select * from book where price > (select avg(price) from book) and state in (:states)", nativeQuery = true)
    List<Book> findAllWherePricesGreaterAverage(List<String> states);

}