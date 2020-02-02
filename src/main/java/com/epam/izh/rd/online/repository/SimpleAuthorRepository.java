package com.epam.izh.rd.online.repository;

import com.epam.izh.rd.online.entity.Author;

import java.util.Arrays;

public class SimpleAuthorRepository implements AuthorRepository {

    private Author[] authors = {};

    @Override
    public boolean save(Author author) {
        if (findByFullName(author.getName(), author.getLastName()) != null) {
            return false;
        }

        Author[] newAuthors = Arrays.copyOf(authors, authors.length + 1);
        newAuthors[newAuthors.length - 1] = author;
        authors = newAuthors;

        return true;
    }

    @Override
    public Author findByFullName(String name, String lastname) {
        Author author = null;

        for (Author a : authors) {
            if (a.getName().equals(name) && a.getLastName().equals(lastname)) {
                author = a;
                break;
            }
        }

        return author;
    }

    @Override
    public boolean remove(Author author) {
        if (findByFullName(author.getName(), author.getLastName()) == null) {
            return false;
        }

        Author[] newAuthors = new Author[authors.length - 1];
        int index = 0;
        for (Author a : authors) {
            if (a != author) {
                newAuthors[index] = a;
            }
            index++;
        }
        authors = newAuthors;

        return true;
    }

    @Override
    public int count() {
        return authors.length;
    }
}
