package com.udacity.cloudstorage.infrastructure.mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;
import com.udacity.cloudstorage.model.Credential;

@Mapper
public interface CredentialMapper {

    @Delete("DELETE FROM CREDENTIALS WHERE id = #{id} AND userid = #{userId}")
    void delete(Credential credential);

    @Select("SELECT id, url, password, username, userid FROM CREDENTIALS WHERE userid = #{UID} ORDER BY id DESC")
    List<Credential> allFrom(String UID);

    @Select("SELECT id, key, url, password, username, userid FROM CREDENTIALS WHERE id = #{id} AND userid = #{userId}")
    Credential find(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, password=#{password} WHERE id =#{id}")
    void update(Credential credential);

    @Insert("INSERT INTO CREDENTIALS (id, url, key, username, password, userid) VALUES(#{id}, #{url}, #{key}, #{username}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Credential credential);

}
