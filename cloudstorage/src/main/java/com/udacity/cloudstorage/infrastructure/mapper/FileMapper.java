package com.udacity.cloudstorage.infrastructure.mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;
import com.udacity.cloudstorage.model.File;

@Mapper
public interface FileMapper {

    @Delete("DELETE FROM FILES WHERE id = #{id} AND userid = #{userId}")
    void delete(File file);

    @Select("SELECT id, name, content_type, size, data, userid FROM FILES WHERE id = #{id} AND userid = #{userId}")
    @Results({
        @Result(property = "contentType", column = "content_type")
    })
    File get(File file);

    @Select("SELECT id, name FROM FILES WHERE userid = #{userId} AND name = #{name}")
    File find(File file);

    @Select("SELECT id, name FROM FILES WHERE userid = #{UID} ORDER BY id DESC")
    List<File> allFrom(String UID);

    @Insert("INSERT INTO FILES (id, name, content_type, size, data, userid) " +
                        "VALUES(#{id}, #{name}, #{contentType}, #{size}, #{data}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(File file);

}
