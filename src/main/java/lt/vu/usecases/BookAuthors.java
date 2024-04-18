package lt.vu.usecases;

import lombok.Getter;
import lt.vu.entities.Author;
import lt.vu.entities.Book;
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
public class BookAuthors implements Serializable {

    @Inject
    private BooksDAO booksDAO;

    @Inject
    private AuthorDAO authorDAO; // This should be injected, not instantiated with new

    @Getter
    private Book selectedBook;

    private Integer selectedAuthorId;

    @PostConstruct
    public void init() {
        try {
            Map<String, String> requestParameters =
                    FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer bookId = Integer.parseInt(requestParameters.get("bookId"));
            this.selectedBook = booksDAO.findOne(bookId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Integer getSelectedAuthorId() {
        return selectedAuthorId;
    }

    public void setSelectedAuthorId(Integer selectedAuthorId) {
        this.selectedAuthorId = selectedAuthorId;
    }

    @Transactional
    public String addAuthorToBook() {
        Author author = authorDAO.findOne(selectedAuthorId);
        if (author != null && !selectedBook.getAuthors().contains(author)) {
            selectedBook.getAuthors().add(author);
            booksDAO.update(selectedBook);
            return "bookDetails?faces-redirect=true&bookId=" + selectedBook.getId();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Author not found or already added", null));
            return null;
        }
    }
}


