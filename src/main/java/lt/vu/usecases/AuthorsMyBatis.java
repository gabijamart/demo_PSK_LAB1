package lt.vu.usecases;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

import lt.vu.mybatis.dao.AuthorMapper;
import lt.vu.mybatis.model.Author;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import javax.inject.Inject;
import java.util.List;

@Named("AuthorsMyBatis")
@RequestScoped
public class AuthorsMyBatis {

    private List<Author> allAuthors;

    // Inject the SqlSessionFactory produced by your MyBatisResources class
    @Inject
    private SqlSessionFactory sqlSessionFactory;

    // Method to load all authors from the database using MyBatis
    public void loadAllAuthors() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AuthorMapper mapper = session.getMapper(AuthorMapper.class);
            this.allAuthors = mapper.findAll();
        } // try-with-resources will auto close the session
    }

    // Getter that JSF will use to get the data for rendering
    public List<Author> getAllAuthors() {
        if (allAuthors == null) {
            loadAllAuthors();
        }
        return allAuthors;
    }

    // If you need to add new authors through your JSF page
    public void addAuthor(Author newAuthor) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AuthorMapper mapper = session.getMapper(AuthorMapper.class);
            mapper.insert(newAuthor);
            session.commit(); // Don't forget to commit the transaction
        }
    }
}
