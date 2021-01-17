package com.udacity.cloudstorage.infrastructure.mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;
import com.udacity.cloudstorage.model.Note;

@Mapper
public interface NoteMapper {

    @Insert("DELETE FROM NOTES WHERE id = #{id}")
    void delete(Integer id);

    @Select("SELECT * FROM NOTES WHERE userid = #{UID} ORDER BY id DESC")
    List<Note> allFrom(String UID);

    @Update("UPDATE NOTES SET title=#{title}, description=#{description} WHERE id =#{id}")
    void update(Note note);

    @Insert("INSERT INTO NOTES (id, title, description, userid) VALUES(#{id}, #{title}, #{description}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Note note);

}
