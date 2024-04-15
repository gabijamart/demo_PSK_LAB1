package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Contact.findAll", query = "select c from Contact as c")
})
@Table(name = "CONTACT")
@Getter @Setter
public class Contact {
    public Contact(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    @Column(name = "ContactDescription")
    private String contactDescription;

    @Size(max = 50)
    @Column(name = "ContactInfo")
    private String contactInfo;

    @ManyToOne
    @JoinColumn(name="AUTHOR_ID")
    private Author author;

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(contactInfo, contact.contactInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactInfo);
    }
}
