package lt.vu.persistence;

import lombok.Setter;
import lt.vu.entities.Author;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Setter
@ApplicationScoped
public class AuthorDAO {

    @Inject
    private EntityManager em;

    public List<Author> loadAll() {
        return em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
    }

    public void persist(Author author) {
        this.em.persist(author);
    }

    public Author findOne(Integer id) {
        return em.find(Author.class, id);
    }

    public Author findByName(String name) {
        return em.createQuery("SELECT a FROM Author a WHERE a.name = :name", Author.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public Author update(Author author) {
        return em.merge(author);
    }
}
