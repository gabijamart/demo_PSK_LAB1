package lt.vu.persistence;

import lt.vu.entities.Author;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class AuthorDAO {

    @Inject
    private EntityManager em;

    public List<Author> loadAll() {
        return em.createNamedQuery("Author.findAll", Author.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Author author){
        this.em.persist(author);
    }

    public Author findOne(Integer id) {
        return em.find(Author.class, id);
    }

    public Author findByName(String name) {
        try {
            return em.createQuery("SELECT a FROM Author a WHERE a.name = :name", Author.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // No author found with the provided name
        }
    }
    public Author update(Author author) {
        return em.merge(author);
    }
}
