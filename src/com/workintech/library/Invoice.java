package com.workintech.library;

import java.util.Date;

public class Invoice {
    private Long id;
    private Long bookid;
    private final Date createDate;
    private Date endDate;
    private Double price;

    public Invoice(Long id, Long bookid, Double price) {
        this.id = id;
        this.bookid = bookid;
        this.createDate = new Date();
        this.endDate = null;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getBookid() {
        return bookid;
    }

    public void setBookid(Long bookid) {
        this.bookid = bookid;
    }
}
