package com.job.management.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Subscription")
public class Subscription {
    @Id
    @Column(name = "subscriber_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subscriberId;

    @Column(name = "reader_id")
    private int readerId;

    @NotNull
    @Size(max = 50)
    @Valid
    @Column(name = "emailId")
    private String emailId;

    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    @Column(name = "is_active")
    private Long isActive;

    public Subscription() {
        super();
    }

//    @ManyToMany(cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
//    @Column(name = "subscribed_books")
//    private List<Book> book;


//    public Subscription(String emailId, List<Book> book) {
//        this.emailId = emailId;
//        this.book = book;
//    }


    public Subscription(String emailId, Integer bookId) {
        this.emailId = emailId;
        this.bookId = bookId;
    }

    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public void setSubscriptionDate(Date subscriptionDt) {
        this.subscriptionDate = subscriptionDt;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public Long getIsActive() {
        return isActive;
    }

    public void setIsActive(Long isActive) {
        this.isActive = isActive;
    }

    //    public List<Book> getBook() {
//        return book;
//    }
//
//    public void setBook(List<Book> book) {
//        this.book = book;
//    }
}
