package com.file_manager.mapper;

import com.file_manager.pojo.File;
import com.file_manager.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("select * from file")
    List<File> searchAllFile();

    @Select("select * from user")
    List<User> searchAllUser();

    @Delete("delete from user where id=#{id}")
    int deleteUser(int id);

    @Update("update shared_file set is_deleted=if(is_deleted=0,1,0) where id=#{id}")
    int toBin(int id);

    @Delete("delete from shared_file where id=#{id}")
    int deleteSharedFile(int id);

    @Select("select local_path from shared_file where id=#{id}")
    String findLocalPath(int id);

    @Select("select local_path from file where use_id=#{id}")
    List<String> findLocalPaths(int id);

    @Delete("delete from file where use_id=#{id}")
    int deleteFiles(int id);
}
