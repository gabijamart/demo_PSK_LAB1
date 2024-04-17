package lt.vu.mybatis.dao;

import lt.vu.mybatis.model.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookMapper {

    @Select("SELECT id, name FROM BOOK")
    List<Book> findAll();

    @Insert("INSERT INTO BOOK(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Book book);

    @Update("UPDATE BOOK SET name = #{name} WHERE id = #{id}")
    void update(Book book);

    @Delete("DELETE FROM BOOK WHERE id = #{id}")
    void delete(Integer id);

    @Select("SELECT id, name FROM BOOK WHERE id = #{id}")
    Book findById(Integer id);

    @Select("SELECT id, name FROM BOOK WHERE authorId = #{authorId}")
    List<Book> findByAuthorId(Integer authorId);

}

