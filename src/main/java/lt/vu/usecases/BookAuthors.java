package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Author;
import lt.vu.entities.Book;
import lt.vu.persistence.AuthorDAO;
import lt.vu.persistence.BooksDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

@Model
@ViewScoped
public class BookAuthors implements Serializable {

    @Inject
    private BooksDAO booksDAO;

    @Inject
    private AuthorDAO authorDAO; 

    @Getter
    private Book selectedBook;

    @Setter
    @Getter
    private Integer selectedAuthorId;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestParameters =
                context.getExternalContext().getRequestParameterMap();
        String bookIdStr = requestParameters.get("bookId");

        if (bookIdStr == null || bookIdStr.trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No book ID provided", null));
            return;
        }

        try {
            Integer bookId = Integer.parseInt(bookIdStr);
            this.selectedBook = booksDAO.findOne(bookId);
            if (this.selectedBook == null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Book not found for ID: " + bookId, null));
            }
        } catch (NumberFormatException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid book ID format", null));
        }
    }

    @Transactional
    public String addAuthorToBook() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> requestParameters =
                context.getExternalContext().getRequestParameterMap();
        String bookIdStr = requestParameters.get("bookId");

        if (bookIdStr != null && !bookIdStr.trim().isEmpty()) {
            Integer bookId = Integer.parseInt(bookIdStr);
            selectedBook = booksDAO.findOne(bookId);
        }
        if (selectedBook == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No book selected", null));
            return null; // Stay on the same page.
        }
        if (selectedAuthorId == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No author selected", null));
            return null; // Stay on the same page.
        }
        try {
            Author author = authorDAO.findOne(selectedAuthorId);
            if (author != null && !selectedBook.getAuthors().contains(author)) {
                selectedBook.getAuthors().add(author);
                author.getBooks().add(selectedBook); // Assuming you want to update both sides of the relationship
                booksDAO.update(selectedBook);
                // Or you could update the author instead, depending on how your persistence context is configured
                // authorDAO.update(author);
                return null;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Author not found or already added", null));
                return null;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error adding author to book: " + e.getMessage(), null));
            return null;
        }
    }
}


