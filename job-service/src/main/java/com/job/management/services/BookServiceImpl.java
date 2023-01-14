package com.job.management.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.job.management.model.Book;
import com.job.management.model.Reader;
import com.job.management.model.Subscription;
import com.job.management.repository.BookRepository;
import com.job.management.repository.SubscriptionRepository;
import com.job.management.utility.BookException;

import javax.validation.Valid;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private SubscriptionRepository subscriptionRepo;

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Override
    public void createbook(Book book) throws BookException {
        try{
            bookRepo.save(book);
        }catch (Exception e){
            throw new BookException(e.getMessage());
        }
    }
    @Override
    public void updateBook(Book book, @Valid Integer authorId, @Valid Integer bookId) throws BookException {
        try{
            Optional<Book> books = bookRepo.findById(bookId);
            logger.debug("Data from repository -> {}",books);
            logger.debug("{}",books.isPresent());
            if (books.isPresent() && books.get().getAuthorID() == authorId) {
                if (book.getLogo() != null)
                    books.get().setLogo(book.getLogo());
                if (book.getBookTitle() != null && !book.getBookTitle().equalsIgnoreCase(""))
                    books.get().setBookTitle(book.getBookTitle());
                if (book.getCategory() != null && !book.getCategory().equalsIgnoreCase(""))
                    books.get().setCategory(book.getCategory());
                if (book.getPrice() >= 0)
                    books.get().setPrice(book.getPrice());
                if (book.getAuthorID()!=0)
                    books.get().setAuthorID(book.getAuthorID());
                if (book.getPublisher() != null && !book.getPublisher().equalsIgnoreCase(""))
                    books.get().setPublisher(book.getPublisher());
                if (book.getContent() != null && !book.getContent().isEmpty())
                    books.get().setContent(book.getContent());

                books.get().setActive(book.isActive());
                books.get().setUpdatedOn(book.getUpdatedOn());

                bookRepo.save(books.get());
            } else {
                throw new BookException("Sorry something went wrong in update book functionality");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<Book> searchBook(String title, String author, String price, String publisher,String category) throws BookException {
        List<Book> book;
        try {
            if (!title.isEmpty() && !author.isEmpty() && !price.isEmpty() && !publisher.isEmpty() && !category.isEmpty()) {
                book = bookRepo.searchBooks(title, author, price, publisher,category);
                logger.debug("Data from repo -> {}", book);
                List<Book> activeBooks = new ArrayList<>();
                for(Book b:book){
                    if(b.isActive()){
                        activeBooks.add(b);
                    }
                }
                return activeBooks;
            }

            //Not recommended
            if (title.isEmpty() && author.isEmpty() && price.isEmpty() && publisher.isEmpty() && category.isEmpty()) {
                book = bookRepo.findAll();
                List<Book> activeBooks = new ArrayList<>();
                for(Book b:book){
                    if(b.isActive()){
                        activeBooks.add(b);
                    }
                }
                return activeBooks;
            }

            if (!title.isEmpty()) {
                book = bookRepo.findByBookTitle(title);
                List<Book> activeBooks = new ArrayList<>();
                for(Book b:book){
                    if(b.isActive()){
                        activeBooks.add(b);
                    }
                }
                return activeBooks;
            }

            if (!author.isEmpty()) {
                book = bookRepo.findByAuthorId(Integer.parseInt(author));
                logger.info("{}",book);
                return new ArrayList<>(book);
            }

            if (!price.isEmpty()) {
                book = bookRepo.findByPrice(price);
                List<Book> activeBooks = new ArrayList<>();
                for(Book b:book){
                    if(b.isActive()){
                        activeBooks.add(b);
                    }
                }
                return activeBooks;
            }
            if(!category.isEmpty()){
                book = bookRepo.findByCategory(category);
                List<Book> activeBooks = new ArrayList<>();
                for(Book b:book){
                    if(b.isActive()){
                        activeBooks.add(b);
                    }
                }
                return activeBooks;
            }
            else {
                book = bookRepo.findByPublisher(publisher);
                List<Book> activeBooks = new ArrayList<>();
                for(Book b:book){
                    if(b.isActive()){
                        activeBooks.add(b);
                    }
                }
                return activeBooks;
            }

        } catch (Exception e) {
            throw new BookException("Books not found");
        }

    }
    @Override
    public boolean blockOrUnBlockBookByAuthor(String authorId, String bookId, String block) throws BookException {
        if (bookId != null && !bookId.isEmpty()) {
            Optional<Book> book = bookRepo.findById(Integer.parseInt(bookId));
            if (book.isPresent() && book.get().getAuthorID() == Integer.parseInt(authorId)) {
                book.get().setActive(block == null || !block.equalsIgnoreCase("Yes"));
                bookRepo.save(book.get());
                return true;
            }
            else {
                throw new BookException("AUTHOR ID Not matching with records" + authorId);
            }
        } else {
            throw new BookException("CANNOT FIND BOOK WITH GIVEN BOOK ID" + bookId);
        }
    }

    @Override
    public boolean subscribeBook(String bookId, Reader reader) throws BookException{

        if (bookId != null && !bookId.equalsIgnoreCase("")) {
            Optional<Book> book = bookRepo.findById(Integer.parseInt(bookId));
            if (book.isPresent()) {
                List<Subscription> sub = subscriptionRepo.findByEmailId(reader.getEmailId());
                boolean notsubscribe = true;
                for (Subscription subbook : sub) {
                    if (subbook.getBookId() == Integer.parseInt(bookId) && subbook.getIsActive()==1L) {
                        notsubscribe = false;
                    }
                }
                if (notsubscribe) {
                    Subscription sBook = new Subscription();

                    sBook.setBookId(Integer.parseInt(bookId));
                    sBook.setReaderId(reader.getReaderId());
                    sBook.setEmailId(reader.getEmailId());
                    sBook.setSubscriptionDate(new Date());
                    sBook.setIsActive(1L);
                    subscriptionRepo.save(sBook);

                    Long count = book.get().getSubscriptionCount();
                    book.get().setSubscriptionCount(count + 1);
                    bookRepo.save(book.get());
                    return true;
                } else {
                    throw new BookException("BOOK_IS_ALREADY_SUBSCRIBED" + bookId);
                }
            } else {
                throw new BookException("CAN_NOT_FIND_BOOK_WITH_ID" + bookId);
            }

        } else {
            throw new BookException("CAN_NOT_FIND_BOOK_WITH_ID" + bookId);
        }
    }

    @Override
    public List<Book> getAllSubscribedBooks(String emailId) throws BookException {
        List<Book> book = null;
        List<Subscription> subscribedBooks = subscriptionRepo.findByEmailId(emailId);
        if (subscribedBooks != null && !subscribedBooks.isEmpty()) {
            List<Integer> bookIds = new ArrayList<>();
            for (Subscription value : subscribedBooks) {
                if(value.getIsActive()==1L) {
                    bookIds.add(value.getBookId());
                }
            }
            book = bookRepo.findAllById(bookIds);
        } else {
            logger.info("CANNOT FIND BOOKS For GIVEN EMAIL_ID {}",emailId);
        }
        return book;
    }

    @Override
    public Book getSubscribedBook(String emailId, String subscriptionId) throws BookException {
        if (subscriptionId!=null && !subscriptionId.isEmpty()) {
            Optional<Subscription> subscribedBooks= subscriptionRepo.findById(Integer.parseInt(subscriptionId));
            if (!subscribedBooks.isEmpty() && subscribedBooks.get()!= null && subscribedBooks.get().getEmailId().equalsIgnoreCase(emailId)) {
                Optional<Book> optionalBook = bookRepo.findById(subscribedBooks.get().getBookId());
                if (!optionalBook.isEmpty()) {
                    return optionalBook.get();

                } else {
                    throw new BookException("CANNOT FIND BOOK WITH GIVEN SUBSCRIPTION ID" + subscriptionId);
                }
            } else {
                throw new BookException("CANNOT FIND BOOK WITH GIVEN SUBSCRIPTION ID" + subscriptionId);
            }
        } else {
            throw new BookException("Email and Subscription IDs are missing");
        }
    }

    @Override
    public boolean cancleSubscriptionWithIn24Hours(String emailId, String subscriptionId) throws BookException {
        if (subscriptionId != null && !subscriptionId.isEmpty()) {
           // Optional<Subscription> subscribedBook = subscriptionRepo.findById(Integer.parseInt(subscriptionId));

            List<Subscription> subscribedBook = subscriptionRepo.findByBookId(Integer.parseInt(subscriptionId));

            for(int i=0 ; i< subscribedBook.size() ; i++) {

                if (!subscribedBook.isEmpty() && subscribedBook.get(i).getEmailId().equalsIgnoreCase(emailId)) {
                    Date presentDate = new Date();
                    Date subscribedDate = subscribedBook.get(i).getSubscriptionDate();
                    long diffInHours = getDiffInHours(subscribedDate, presentDate, TimeUnit.HOURS);
                    if (diffInHours < 24) {
                        subscribedBook.get(i).setIsActive(0L);
                        subscriptionRepo.save(subscribedBook.get(i));
                        Optional<Book> book = bookRepo.findById(subscribedBook.get(i).getBookId());
                        long count = book.isPresent() ? book.get().getSubscriptionCount() : 0;
                        if (count > 0) {
                            book.get().setSubscriptionCount(count - 1);
                            bookRepo.save(book.get());
                        }
                        //subscriptionRepo.deleteById(subscribedBook.get(i).getSubscriberId());
                        return true;
                    } else {
                        throw new BookException("CAN_NOT_CANCEL_SUBSCRIPTION_AFTER_24_HOURS" + subscriptionId);
                    }
                }
//                else {
//                                        throw new BookException("ACCESS_DENIED" + subscriptionId);
//                }
            }
        } else {
            throw new BookException("CAN_NOT_FIND_THE_SUBSCRIPTION_FOR_THE_BOOK_WITH_ID" + subscriptionId);
        }

        return false;
    }

    public static long getDiffInHours(final Date date1, final Date date2, final TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();

        return timeUnit.convert(diffInMillies, timeUnit.MILLISECONDS);
    }

}
