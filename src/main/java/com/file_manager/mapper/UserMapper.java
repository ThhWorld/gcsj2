package com.file_manager.mapper;

import org.apache.ibatis.annotations.*;

import com.file_manager.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

        @Select("SELECT * FROM users WHERE account = #{account} AND password = #{password}")
        User findByAccountAndPassword(String account, String password);

        @Select("SELECT * FROM users WHERE account = #{account}")
        User findByAccount(String account);

        @Select("SELECT * FROM users WHERE id = #{id}")
        User findById(int id);

        @Insert("INSERT INTO users(account, password, userName, phoneNumber, email, sex) VALUES(#{account}, #{password}, #{userName}, #{phoneNumber}, #{email}, #{sex})")
        @Options(useGeneratedKeys = true, keyProperty = "id")
        void insertUser(User user);

        @Update("UPDATE users SET userName = #{userName}, phoneNumber = #{phoneNumber}, email = #{email} WHERE id = #{id}")
        void updateUser(User user);

        @Update("UPDATE users SET password = #{password} WHERE id = #{id}")
        void updatePassword( int id, String password);

        @Delete("DELETE FROM users WHERE id = #{id}")
        void deleteUser(int id);
    }

