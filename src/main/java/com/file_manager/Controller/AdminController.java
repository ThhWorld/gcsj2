package com.file_manager.Controller;

import com.file_manager.pojo.File;
import com.file_manager.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    @GetMapping("/admin/file")
    public List<File> searchAllFile(){
        return new AdminService().searchAllFile();
    }
}
