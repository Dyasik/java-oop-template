package com.epam.izh.rd.online.repository;

import com.epam.izh.rd.online.entity.SchoolBook;
import com.sun.istack.internal.NotNull;

import java.util.Arrays;

public class SimpleSchoolBookRepository implements BookRepository<SchoolBook> {

    private SchoolBook[] schoolBooks = {};

    @Override
    public boolean save(@NotNull SchoolBook book) {
        SchoolBook[] newBooks = Arrays.copyOf(schoolBooks, schoolBooks.length + 1);
        newBooks[newBooks.length - 1] = book;
        schoolBooks = newBooks;

        return true;
    }

    @Override
    public SchoolBook[] findByName(String name) {
        SchoolBook[] books = {};

        for (SchoolBook book : schoolBooks) {
            if (book.getName().equals(name)) {
                books = Arrays.copyOf(books, books.length + 1);
                books[books.length - 1] = book;
            }
        }

        return books;
    }

    @Override
    public boolean removeByName(String name) {
        SchoolBook[] booksByName = findByName(name);

        if (booksByName.length == 0) {
            return false;
        }

        SchoolBook[] newBooks = new SchoolBook[schoolBooks.length - booksByName.length];
        int index = 0;

        for (SchoolBook book : schoolBooks) {
            if (!book.getName().equals(name)) {
                newBooks[index] = book;
            }
            index++;
        }

        schoolBooks = newBooks;
        return true;
    }

    @Override
    public int count() {
        return schoolBooks.length;
    }
}
