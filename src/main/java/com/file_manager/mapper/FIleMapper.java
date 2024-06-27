package com.file_manager.mapper;

import org.apache.ibatis.annotations.Insert;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Controller;
import com.file_manager.pojo.File;

import java.util.List;


@Mapper
public interface FIleMapper {
    @Insert("INSERT INTO file (use_id,file_path,create_time,upload_time,file_name,is_deleted,local_path)VALUES(#{useId},#{filePath},#{createTime},#{uploadTime},#{fileName},#{isDeleted},#{localPath})")
    int Upload(File file);
    @Select("select file_name from file where file_name like concat(#{fileName},'%') and use_id=#{useId}")
    List<String> isExist(@Param("fileName") String fileName,@Param("useId") int useId);
}
