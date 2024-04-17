package lt.vu.usecases;

import lt.vu.mybatis.dao.AuthorMapper;
import lt.vu.mybatis.dao.BookMapper;
import lt.vu.mybatis.model.Author;
import lt.vu.mybatis.model.Book;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import java.io.Serializable;

@Named("BooksMyBatis")
@ViewScoped
public class BooksMyBatis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SqlSessionFactory sqlSessionFactory;

    private Author author;
    private List<Book> books; // Cache the list of books
    private Book bookToCreate = new Book();

    // Method to initialize the bean with author and books
    public void init(Integer authorId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AuthorMapper authorMapper = session.getMapper(AuthorMapper.class);
            this.author = authorMapper.findById(authorId);
            if (this.author != null) {
                BookMapper bookMapper = session.getMapper(BookMapper.class);
                this.books = bookMapper.findByAuthorId(this.author.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error initializing BooksMyBatis", e);
        }
    }

    public void addBook() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            BookMapper mapper = session.getMapper(BookMapper.class);
            mapper.insert(bookToCreate);
            session.commit();
            // Assuming the Book object has a reference to its Author
            books.add(bookToCreate); // Add the new book to the list
            bookToCreate = new Book(); // Reset the placeholder for the new book
        } catch (Exception e) {
            throw new RuntimeException("Error adding new book", e);
        }
    }

    // Getters and Setters
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBookToCreate() {
        return bookToCreate;
    }

    public void setBookToCreate(Book bookToCreate) {
        this.bookToCreate = bookToCreate;
    }
}
