package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Author;
import lt.vu.entities.Contact;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.AuthorDAO;
import lt.vu.persistence.ContactDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

@Model
public class Contacts implements Serializable {
    @Inject
    private ContactDAO contactDAO;
    @Inject
    private AuthorDAO authorDAO;

    @Getter @Setter
    private Author author;

    @Getter
    @Setter
    private Contact contactToCreate = new Contact();

    @PostConstruct
    public void init(){
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer authorId = Integer.parseInt(requestParameters.get("authorId"));
        this.author = authorDAO.findOne(authorId);
//        loadAllContacts();
    }

    @Transactional
    @LoggedInvocation
    public void createContact(){
        contactToCreate.setAuthor(this.author);
        this.contactDAO.persist(contactToCreate);
    }
}
