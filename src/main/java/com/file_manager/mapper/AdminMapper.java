package com.file_manager.mapper;

import com.file_manager.pojo.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("select * from file")
    List<File> searchAllFile();
}
