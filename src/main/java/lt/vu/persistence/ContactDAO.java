package lt.vu.persistence;

import lt.vu.entities.Author;
import lt.vu.entities.Contact;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class ContactDAO {

    @Inject
    private EntityManager em;

    public List<Contact> loadAll() {
        return em.createNamedQuery("Contact.findAll", Contact.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Contact contact){
        this.em.persist(contact);
    }

    public Contact findOne(Integer id) {
        return em.find(Contact.class, id);
    }
}
