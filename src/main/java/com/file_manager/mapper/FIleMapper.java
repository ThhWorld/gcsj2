package com.file_manager.mapper;

import com.file_manager.pojo.SharedFile;
import org.apache.ibatis.annotations.*;

import org.springframework.stereotype.Controller;
import com.file_manager.pojo.File;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Mapper
public interface FIleMapper {
    @Insert("INSERT INTO file (use_id,file_path,create_time,upload_time,file_name,is_deleted,local_path)VALUES(#{useId},#{filePath},#{createTime},#{uploadTime},#{fileName},#{isDeleted},#{localPath})")
    int Upload(File file);
    @Select("select file_name from file where file_name like concat(#{fileName},'%') and use_id=#{useId}")
    List<String> isExist(@Param("fileName") String fileName,@Param("useId") int useId);
    @Select("select * from file where id=#{id}")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    File Download(int id);

    @Insert("INSERT INTO shared_file( id,use_id, file_path, create_time, upload_time, file_name, is_deleted, local_path) VALUES(#{id},#{useId},#{filePath},#{createTime},#{uploadTime},#{fileName},#{isDeleted},#{localPath})")
    int Share(File file);

    @Delete("delete from shared_file where id=#{id}")
    int unShare(int id);

    @Select("SELECT * from shared_file where id=#{id}")
    File SelectShare(int id);

    @Update("update file set file_name=#{fileName} where id=#{id}")
    int reName(@Param("id")int id,@Param("fileName") String fileName);

    @Select("select * from file where use_id=#{useid}")
    List<File>  rearchFile(@Param("useid") int useid);

    @Select("select * from shared_file")
    List<SharedFile> rearchSharedFile();
    @Update("update file set is_deleted=if(is_deleted=0,1,0) where id=#{id}")
    int toBin(int id);
    @Delete("delete from file where id=#{id}")
    int deleteFile(int id);
    @Select("select local_path from file where id=#{id}")
    String findLocalPath(int id);

}
