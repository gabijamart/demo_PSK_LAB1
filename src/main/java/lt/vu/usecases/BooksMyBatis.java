package lt.vu.usecases;

import lt.vu.mybatis.dao.AuthorMapper;
import lt.vu.mybatis.dao.BookMapper;
import lt.vu.mybatis.model.Author;
import lt.vu.mybatis.model.Book;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("BooksMyBatis")
@ViewScoped
public class BooksMyBatis implements Serializable {

    @Inject
    private AuthorMapper authorMapper;

    @Inject
    private BookMapper bookMapper;

    public List<Author> getAllAuthors() {
        return authorMapper.findAll();
    }

    public List<Book> getBooksByAuthor(int authorId) {
        return bookMapper.findByAuthorId(authorId);
    }

}
