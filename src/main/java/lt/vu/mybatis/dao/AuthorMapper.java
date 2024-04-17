package lt.vu.mybatis.dao;

import lt.vu.mybatis.model.Author;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AuthorMapper {

    @Select("SELECT id, name FROM AUTHOR")
    List<Author> findAll();

    @Insert("INSERT INTO AUTHOR(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Author author);

    @Update("UPDATE AUTHOR SET name = #{name} WHERE id = #{id}")
    void update(Author author);

    @Delete("DELETE FROM AUTHOR WHERE id = #{id}")
    void delete(Integer id);

    Author findById(Integer authorId);
}
