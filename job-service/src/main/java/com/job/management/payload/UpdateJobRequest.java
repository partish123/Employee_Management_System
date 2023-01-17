package com.job.management.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateJobRequest {
    @NotBlank
    @Size(max = 150)
    private String bookTitle;

    @NotBlank
    @Size(max = 100)
    private String category;

    private double price;

    @NotBlank
    @Size(max = 120)
    private String publisher;


    private String image;
    @NotNull
    boolean isActive;

    @NotBlank
    private String bookcontent;


    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getBookcontent() {
        return bookcontent;
    }

    public void setBookcontent(String bookcontent) {
        this.bookcontent = bookcontent;
    }


    public UpdateJobRequest(String bookTitle, String category, double price, String publisher, String image, String bookcontent) {
        this.bookTitle = bookTitle;
        this.category = category;
        this.price = price;
        this.publisher = publisher;
        this.image = image;
        this.bookcontent = bookcontent;
    }

    @Override
    public String toString() {
        return "UpdateBookRequest{" +
                "bookTitle='" + bookTitle + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", publisher='" + publisher + '\'' +
                ", image=" + image +
                ", isActive=" + isActive +
                ", bookcontent='" + bookcontent + '\'' +
                '}';
    }
}
