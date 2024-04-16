package lt.vu.mybatis.dao;

import lt.vu.mybatis.model.Contact;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ContactMapper {
    final String getAll = "SELECT * FROM CONTACT";
    final String getById = "SELECT * FROM CONTACT WHERE id = #{id}";
    final String insert = "INSERT INTO CONTACT (ContactDescription, ContactInfo, AUTHOR_ID) VALUES (#{contactDescription}, #{contactInfo}, #{authorId})";
    final String update = "UPDATE CONTACT SET ContactDescription=#{contactDescription}, ContactInfo=#{contactInfo}, AUTHOR_ID=#{authorId} WHERE id=#{id}";
    final String deleteById = "DELETE FROM CONTACT WHERE id=#{id}";

    @Select(getAll)
    List<Contact> findAll();

    @Select(getById)
    Contact findById(int id);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Contact contact);

    @Update(update)
    void update(Contact contact);

    @Delete(deleteById)
    void deleteById(int id);
}
