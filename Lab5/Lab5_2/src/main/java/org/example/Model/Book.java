package org.example.Model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class Book {
    private final String title;
    private final String author;
    private final Date published;

    public Book(String title, String author, LocalDateTime published) {
        this.title = title;
        this.author = author;
        this.published= Date.from(published.toInstant(ZoneOffset.UTC));
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public Date getPublished(){
        return published;
    }
    // constructors, getter, setter ommitted
}
