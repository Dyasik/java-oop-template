package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Author;
import com.epam.izh.rd.online.entity.SchoolBook;
import com.epam.izh.rd.online.repository.BookRepository;
import com.epam.izh.rd.online.repository.SimpleSchoolBookRepository;

public class SimpleSchoolBookService implements BookService<SchoolBook> {

    private BookRepository<SchoolBook> schoolBookBookRepository;
    private AuthorService authorService;

    public SimpleSchoolBookService() {
        schoolBookBookRepository = new SimpleSchoolBookRepository();
        authorService = new SimpleAuthorService();
    }

    public SimpleSchoolBookService(
        BookRepository<SchoolBook> schoolBookBookRepository, AuthorService authorService) {
        this.schoolBookBookRepository = schoolBookBookRepository;
        this.authorService = authorService;
    }

    @Override
    public boolean save(SchoolBook book) {
        boolean isAuthorSaved = authorService.findByFullName(book.getAuthorName(), book.getAuthorLastName()) != null;

        if (!isAuthorSaved) {
            return false;
        }

        return schoolBookBookRepository.save(book);
    }

    @Override
    public SchoolBook[] findByName(String name) {
        return schoolBookBookRepository.findByName(name);
    }

    @Override
    public int getNumberOfBooksByName(String name) {
        return schoolBookBookRepository.findByName(name).length;
    }

    @Override
    public boolean removeByName(String name) {
        return schoolBookBookRepository.removeByName(name);
    }

    @Override
    public int count() {
        return schoolBookBookRepository.count();
    }

    /**
     * Метод должен возвращать автора книги по названию книги.
     * <p>
     * То есть приждется сходить и в репозиторий с книгами и в сервис авторов.
     * <p>
     * Если такой книги не найдено, метод должен вернуть null.
     *
     * @param name
     */
    @Override
    public Author findAuthorByBookName(String name) {
        SchoolBook[] booksByName = schoolBookBookRepository.findByName(name);

        if (booksByName.length == 0) {
            return null;
        }

        SchoolBook book = booksByName[0];

        return authorService.findByFullName(book.getAuthorName(), book.getAuthorLastName());
    }
}
