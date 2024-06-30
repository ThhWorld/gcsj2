package com.file_manager.Controller;

import com.file_manager.pojo.SharedFile;
import com.file_manager.pojo.UpFile;
import com.file_manager.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.file_manager.pojo.File;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class FileController {
    @Autowired
    FileService fileService;
    @PostMapping("/user/fileupload")
    @CrossOrigin(origins = "*")
    public File Upload(@RequestBody UpFile upFile) throws IOException {
        return fileService.Upload(upFile.getFile(), upFile.getUseId(), upFile.getFilePath());
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

    @PatchMapping("/user/rename")
    public boolean reName(int id, String fileName){
        return  fileService.reName(id,fileName);
    }

    @GetMapping("/user/flie")
    public List<File> searchFile(int useid){
        return  fileService.searchFile(useid);
    }

    @GetMapping("user/sharedfile")
    public List<SharedFile> searchSharedFile(){
        return  fileService.searchSharedFile();
    }
    @PatchMapping("/user/tobin")
    public boolean toBin(int id){
        return fileService.toBin(id);
    }
    @DeleteMapping("/user/realdelete")
    public boolean deleteSharedFile(int id) throws IOException {
        return fileService.deleteFile(id);
    }
}
