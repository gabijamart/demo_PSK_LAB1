package lt.vu.persistence;

import lt.vu.entities.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class BooksDAO {

    @Inject
    private EntityManager em;

    public List<Book> loadAll() {
        return em.createNamedQuery("Book.findAll", Book.class).getResultList();
    }

    public void persist(Book book){
        this.em.persist(book);
    }

    public Book findOne(Integer id){
        return em.find(Book.class, id);
    }

    public Book findOneByName(String name) {
        List foundBooks;
        foundBooks = em.createQuery("SELECT b FROM Book b WHERE b.name LIKE :findName")
                 .setParameter("findName", name)
                 .setMaxResults(1)
                 .getResultList();
        if (foundBooks.isEmpty()) return null;
        return (Book) foundBooks.get(0);
    }

    public Book update(Book book){
        return em.merge(book);
    }
}
