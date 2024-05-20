package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Author;
import lt.vu.entities.Book;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.AuthorDAO;
import lt.vu.persistence.BooksDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

@Model
public class BooksOfAuthor implements Serializable {

    @Inject
    private AuthorDAO authorDAO;

    @Inject
    private BooksDAO booksDAO;

    @Getter @Setter
    private Author author;

    @Getter @Setter
    private Book bookToCreate = new Book();

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer authorId = Integer.parseInt(requestParameters.get("authorId"));
        this.author = authorDAO.findOne(authorId);
    }

    //@Transactional
    //@LoggedInvocation
    //public void createBook(String bookName) {
        //Book tempBook = booksDAO.findOneByName(bookToCreate.getName());
        //if (tempBook == null){
            //booksDAO.persist(bookToCreate);
            //tempBook = bookToCreate;
        //}
        //author.createBook(tempBook);
    //}

    @Transactional
    @LoggedInvocation
    public void createBook() {
        try {
            Book existingBook = booksDAO.findOneByName(bookToCreate.getName());
            if (existingBook != null && author.getBooks().contains(existingBook)) {
                throw new IllegalArgumentException("This author already has a book with the same name.");
            }

            if (existingBook == null){
                booksDAO.persist(bookToCreate);
                existingBook = bookToCreate;
            }

            author.getBooks().add(existingBook);
            authorDAO.update(author);
        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), null));
        }
    }
}
