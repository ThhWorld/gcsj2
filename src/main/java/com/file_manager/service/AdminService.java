package com.file_manager.service;

import com.file_manager.mapper.AdminMapper;
import com.file_manager.pojo.File;
import com.file_manager.pojo.User;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service("adminService")
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public List<File> searchAllFile() {
        return adminMapper.searchAllFile();
    }

    public List<User> searchAllUser() {
        return adminMapper.searchAllUser();
    }

    public boolean deleteUser(int id) throws IOException {
        List<String> paths=adminMapper.findLocalPaths(id);
        for (String path : paths) {
            java.io.File deleteFile = new java.io.File(path);
            if (deleteFile.exists() == true) {
                FileUtils.forceDelete(deleteFile);
            }
        }
        if(adminMapper.deleteFiles(id)>0){
            if (adminMapper.deleteUser(id) > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean toBin(int id) {
        if (adminMapper.toBin(id) > 0) {
            return true;
        }
        return false;
    }

    public boolean deleteSharedFile(int id) throws IOException {
        String path = adminMapper.findLocalPath(id);
        if (adminMapper.deleteSharedFile(id) > 0) {
            java.io.File deleteFile = new java.io.File(path);
            if (deleteFile.exists() == true) {
                FileUtils.forceDelete(deleteFile);
            }
            return true;
        }
        return false;
    }
}
