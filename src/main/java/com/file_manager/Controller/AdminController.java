package com.file_manager.Controller;

import com.file_manager.pojo.File;
import com.file_manager.pojo.User;
import com.file_manager.service.AdminService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class AdminController {
    @GetMapping("/admin/file")
    public List<File> searchAllFile() {
        return new AdminService().searchAllFile();
    }

    @GetMapping("/admin/user")
    public List<User> searchAllUser() {
        return new AdminService().searchAllUser();
    }

    @DeleteMapping("/admin/deleteuser")
    public boolean deleteUser(int id){
        return new AdminService().deleteUser(id);
    }

    @PatchMapping("/admin/delete")
    public boolean toBin(int id){
        return new AdminService().toBin(id);
    }

    @DeleteMapping("/admin/realdelete")
    public boolean deleteSharedFile(int id) throws IOException {
        return new AdminService().deleteSharedFile(id);
    }

}
