package org.example.Worker;

import org.example.Model.Book;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private final List<Book> store = new ArrayList<>();

    public void addBook(final Book book) {
        store.add(book);
    }

    public List<Book> findBooks(final Date from, final Date to) {
        Calendar end = Calendar.getInstance();
        end.setTime(to);
        end.roll(Calendar.YEAR, 1);

        return store.stream().filter(book -> {return from.before(book.getPublished()) && end.getTime().after(book.getPublished());}).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
    }
    public List<Book> findBooksByTitle(String title) {
        return store.stream().filter( book -> {return title.equals(book.getTitle());}).sorted(Comparator.comparing(Book::getTitle).reversed()).collect(Collectors.toList());
    }

    public List<Book> findBooksByAuthor(String author) {
        return store.stream().filter( book -> {return author.equals(book.getAuthor());}).sorted(Comparator.comparing(Book::getAuthor).reversed()).collect(Collectors.toList());
    }

    public void removeBook(Book book) {
        try {
            store.remove(book);
        }catch (Exception NullPointerException){
            System.out.println("The book you are trying to remove does no exists");
        }

    }
}