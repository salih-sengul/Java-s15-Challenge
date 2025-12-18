package com.workintech.book.category;

public class KidsBook extends Category{
    private String ageRange;

    public KidsBook(Long id, String name, String ageRange) {
        super(id, name);
        this.ageRange = ageRange;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }
}
