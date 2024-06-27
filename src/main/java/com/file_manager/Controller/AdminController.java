package com.file_manager.Controller;

import com.file_manager.mapper.AdminMapper;
import com.file_manager.pojo.File;
import com.file_manager.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @GetMapping("/admin/file")
    public List<File> searchAllFile(){
        return new AdminService().searchAllFile();
    }
}
