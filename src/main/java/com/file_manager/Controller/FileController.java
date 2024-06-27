package com.file_manager.Controller;

import com.file_manager.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/user/download")
    public ResponseEntity<byte[]> Download(int id) throws IOException {
        return fileService.Download(id);
    }

    @PutMapping("/user/share")
    public boolean Share(int id) throws IOException {
        return fileService.Share(id);
    }
    @DeleteMapping("/user/unshare")
    public boolean unShare(int id){
        return fileService.unShare(id);
    }
}
