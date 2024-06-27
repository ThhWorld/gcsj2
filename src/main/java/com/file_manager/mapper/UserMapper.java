package com.file_manager.mapper;

import org.apache.ibatis.annotations.*;

import com.file_manager.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

        @Select("SELECT * FROM user WHERE account = #{account} AND password = #{password}")
        User findByAccountAndPassword(String account, String password);

        @Select("SELECT * FROM user WHERE account = #{account}")
        User findByAccount(String account);

        @Select("SELECT * FROM user WHERE id = #{id}")
        User findById(int id);

        @Insert("INSERT INTO user (account, password, userName, phoneNumber, email, sex) VALUES(#{account}, #{password}, #{userName}, #{phoneNumber}, #{email}, #{sex})")
        @Options(useGeneratedKeys = true, keyProperty = "id")
        void insertUser(User user);

        @Update("UPDATE user SET userName = #{userName}, phoneNumber = #{phoneNumber}, email = #{email} WHERE id = #{id}")
        void updateUser(User user);

        @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
        void updatePassword( int id, String password);

        @Delete("DELETE FROM user WHERE id = #{id}")
        void deleteUser(int id);
    }

