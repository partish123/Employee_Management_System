package com.job.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    @Query(value = "select * from books b where b.Title = ?1 and b.AuthorID = ?2 and b.Price = ?3 and b.Publisher = ?4 and b.Category = ?5", nativeQuery = true)
    List<Book> searchBooks(String title, String author, String price, String publisher,String category);

    List<Book> findAll();

    List<Book> findByBookTitle(String title);

    List<Book> findByAuthorId(Integer author);

    List<Book> findByPrice(String price);

    List<Book> findByPublisher(String publisher);

    List<Book> findByCategory(String category);

    @Override
    default List<Book> findAllById(Iterable<Integer> ids) {
        List<Book> books = new ArrayList<>();
        for (Integer id : ids) {
            Optional<Book> optionalBooks = findById(id);
            optionalBooks.ifPresent(books::add);
        }

        return books;
    }
}
