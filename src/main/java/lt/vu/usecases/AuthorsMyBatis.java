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

    @Inject
    private SqlSessionFactory sqlSessionFactory;

    public void loadAllAuthors() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AuthorMapper mapper = session.getMapper(AuthorMapper.class);
            this.allAuthors = mapper.findAll();
        }
    }

    public List<Author> getAllAuthors() {
        if (allAuthors == null) {
            loadAllAuthors();
        }
        return allAuthors;
    }

    public void addAuthor(Author newAuthor) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AuthorMapper mapper = session.getMapper(AuthorMapper.class);
            mapper.insert(newAuthor);
            session.commit();
        }
    }
}
