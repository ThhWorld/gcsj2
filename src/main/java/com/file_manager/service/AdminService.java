package com.file_manager.service;

import com.file_manager.mapper.AdminMapper;
import com.file_manager.pojo.File;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdminService {
    @Autowired
    AdminMapper adminMapper;
    public List<File> searchAllFile(){
        return adminMapper.searchAllFile();
    }
}
