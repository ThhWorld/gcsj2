package com.file_manager.Controller;

import com.file_manager.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.file_manager.pojo.File;
import java.io.IOException;

@RestController
public class FileController {
    @Autowired
    FileService fileService;
    @PostMapping("/user/fileupload")
    @CrossOrigin(origins = "*")
    public File Upload(MultipartFile file, int useId, String filePath) throws IOException {
        return fileService.Upload(file,useId,filePath);
    }
}
