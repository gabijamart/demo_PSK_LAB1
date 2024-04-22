package lt.vu.mybatis.dao;

import lt.vu.mybatis.model.Author;
import org.apache.ibatis.annotations.*;
import org.mybatis.cdi.Mapper;

import java.util.List;
@Mapper
public interface AuthorMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.COURSE
     *
     * @mbg.generated Wed Apr 10 13:15:35 EEST 2024
     */
    List<Author> findAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.COURSE
     *
     * @mbg.generated Wed Apr 10 13:15:35 EEST 2024
     */
    void insert(Author author);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.COURSE
     *
     * @mbg.generated Wed Apr 10 13:15:35 EEST 2024
     */
    void update(Author author);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.COURSE
     *
     * @mbg.generated Wed Apr 10 13:15:35 EEST 2024
     */
    void delete(Integer id);

}
