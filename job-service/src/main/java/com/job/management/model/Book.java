package com.job.management.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Arrays;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookID")
    private int id;
    @NotBlank
    @Size(max = 150)
    @Column(name = "Title")
    private String bookTitle;
    @Column(name = "AuthorID")
    private int authorId;
    @Column(name = "Price")
    private double price;
    @NotBlank
    @Size(max = 100)
    @Column(name = "Category")
    private String category;
    @NotBlank
    @Size(max = 150)
    @Column(name = "Publisher")
    private String publisher;
    @Column(name = "Logo")
    @Lob
    private String logo;
    @Column(name = "AudioURL")
    private String audioURL;
    @Column(name = "Content")
    private String content;
    @Column(name = "IsActive")
    private boolean isActive;
    @Column(name = "Updated_ON")
    private LocalDate updatedOn;
    @Column(name = "Created_ON")
    private LocalDate createdOn;

    @Column(name = "subscription_count")
    private Long subscriptionCount;

    public Book(int id, String bookTitle, int authorId, String category, double price, String publisher, LocalDate date, LocalDate date1, boolean active, String bookcontent,Long subscriptionCount) {
        this.id = id;
        this.bookTitle=bookTitle;
        this.authorId=authorId;
        this.category = category;
        this.price = price;
        this.publisher = publisher;
        this.createdOn = date;
        this.updatedOn = date1;
        this.isActive = active;
        this.content = bookcontent;
        this.subscriptionCount =subscriptionCount;
    }

//    public Subscription getSubscription() {
//        return subscription;
//    }
//
//    public void setSubscription(Subscription subscription) {
//        this.subscription = subscription;
//    }


    public Book() {

    }

    //For create book
    public Book(String bookTitle, int authorId, String category, double price, String publisher, LocalDate now, LocalDate localDate, boolean b, String bookcontent,Long subscriptionCount) {
        this.bookTitle=bookTitle;
        this.authorId=authorId;
        this.category = category;
        this.price = price;
        this.publisher = publisher;
        this.createdOn = now;
        this.updatedOn = localDate;
        this.isActive = b;
        this.content = bookcontent;
        this.subscriptionCount = subscriptionCount;
    }

    //For update book
    public Book(int bookId, String bookTitle, int authorId, String category, double price, String publisher, LocalDate date, LocalDate date1, boolean active, String bookcontent) {
        this.id = bookId;
        this.bookTitle=bookTitle;
        this.authorId=authorId;
        this.category = category;
        this.price = price;
        this.publisher = publisher;
        this.createdOn = date;
        this.updatedOn = date1;
        this.isActive = active;
        this.content = bookcontent;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getAuthorID() {
        return authorId;
    }

    public void setAuthorID(int authorId) {
        this.authorId = authorId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAudioURL() {
        return audioURL;
    }

    public void setAudioURL(String audioURL) {
        this.audioURL = audioURL;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }


    public Long getSubscriptionCount() {
        return subscriptionCount;
    }

    public void setSubscriptionCount(Long subscriptionCount) {
        this.subscriptionCount = subscriptionCount;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", authorId=" + authorId +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", publisher='" + publisher + '\'' +
                ", logo=" + logo +
                ", audioURL='" + audioURL + '\'' +
                ", content='" + content + '\'' +
                ", isActive=" + isActive +
                ", updatedOn=" + updatedOn +
                ", createdOn=" + createdOn +
                ", subscriptionCount=" + subscriptionCount +
                '}';
    }
}
