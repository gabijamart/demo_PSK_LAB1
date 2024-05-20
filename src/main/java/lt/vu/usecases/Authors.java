package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Author;
import lt.vu.persistence.AuthorDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Authors {
    @Inject
    private AuthorDAO authorsDAO;

    @Inject
    private EntityManagerFactory emf;

    @Getter @Setter
    private Author authorToCreate = new Author();

    @Getter
    private List<Author> allAuthors;

    @PostConstruct
    public void init() {
        loadAllAuthors();
    }

    @Transactional
    public void createAuthor() {
        try {
            Author existingAuthor = authorsDAO.findByName(authorToCreate.getName());
            if (existingAuthor != null) {
                throw new IllegalArgumentException("Author with this name already exists.");
            }
            authorsDAO.persist(authorToCreate);
        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), null));
        }
    }

    @Transactional
    public void updateAuthorName(Integer authorId, String newName) {
        Author author1 = authorsDAO.findOne(authorId);
        author1.setName("Initial Name");

        // Simulate concurrent update
        new Thread(() -> {
            EntityManager em2 = emf.createEntityManager();
            EntityTransaction transaction2 = em2.getTransaction();
            transaction2.begin();
            Author author2 = em2.find(Author.class, authorId);
            author2.setName("Concurrent Name");
            em2.merge(author2);
            transaction2.commit();
            em2.close();
        }).start();

        try {
            Thread.sleep(1000); // Wait for the concurrent transaction to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            author1.setName(newName);
            authorsDAO.update(author1);
        } catch (OptimisticLockException ole) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Optimistic Lock Exception: " + ole.getMessage(), null));
        }
    }

    private void loadAllAuthors() {
        this.allAuthors = authorsDAO.loadAll();
    }

    // Test method to simulate update
    @Transactional
    public void testUpdateAuthorName() {
        // Create an author
        Author newAuthor = new Author();
        newAuthor.setName("Test Author");
        authorsDAO.persist(newAuthor);

        // Attempt to update author
        updateAuthorName(newAuthor.getId(), "Updated Author Name");
    }
}
