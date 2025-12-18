package com.workintech.book.category;

public class Novel extends Category{
    private String type;

    public Novel(Long id, String name, String type) {
        super(id, name);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
