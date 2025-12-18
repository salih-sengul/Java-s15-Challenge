package com.workintech.book.category;

public class Textbook extends Category{
    private String lecture;

    public Textbook(Long id, String name, String lecture) {
        super(id, name);
        this.lecture = lecture;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }
}
