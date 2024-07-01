package com.file_manager.service;


import com.file_manager.mapper.FIleMapper;
import com.file_manager.pojo.File;
import com.file_manager.pojo.SharedFile;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Service("fileservice")
public class FileService {
    @Autowired
    FIleMapper fIleMapper;
    public File Upload(MultipartFile file, int use_id, String file_path)throws IOException {
        File result=new File();
        List<String> ExistedName;
        String localPath;
        LocalDate date = LocalDate.now();
        String file_name=file.getOriginalFilename();
        ExistedName=fIleMapper.isExist(file_name,use_id);
        if(ExistedName.size()!=0){
            if(ExistedName.size()==1){
                file_name=file_name+"("+1+")";
            }
            else{
                String[] the_silit=ExistedName.get(ExistedName.size()-1).split("[(,)]");
                int num=Integer.parseInt(the_silit[1])+1;
                file_name=file_name+"("+ num +")";
            }
        }
        localPath="C:\\gcsj\\personal\\"+file_name;
        result.setUseId(use_id);
        result.setFilePath(file_path);
        result.setCreateTime(Date.valueOf(date));
        result.setUploadTime(Date.valueOf(date));
        result.setFileName(file_name);
        result.setLocalPath(localPath);
        FileUtils.forceMkdir(new java.io.File("C:\\gcsj\\personal"));
        file.transferTo(new java.io.File(localPath));
        fIleMapper.Upload(result);
        return result;
    }

    public ResponseEntity<byte[]> Download(int id) throws IOException {
        File file= fIleMapper.Download(id);
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        String fileName = file.getFileName();
        fileName = URLEncoder.encode(fileName,"UTF-8");
        builder.header("Access-Control-Expose-Headers", "Content-Disposition");
        builder.header("Content-Disposition","attachment;filename*=UTF-8''"+fileName);
        ResponseEntity <byte[]> responseEntity=builder.body(FileUtils.readFileToByteArray(new java.io.File(file.getLocalPath())));
        return responseEntity;
    }

    public ResponseEntity<byte[]> ShareDownload(int id) throws IOException {
        File file= fIleMapper.ShareDownload(id);
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        String fileName = file.getFileName();
        fileName = URLEncoder.encode(fileName,"UTF-8");
        builder.header("Access-Control-Expose-Headers", "Content-Disposition");
        builder.header("Content-Disposition","attachment;filename*=UTF-8''"+fileName);
        ResponseEntity <byte[]> responseEntity=builder.body(FileUtils.readFileToByteArray(new java.io.File(file.getLocalPath())));
        return responseEntity;
    }

    public boolean Share(int id) throws IOException {
        File file=fIleMapper.Download(id);
        String Path= file.getLocalPath();
       file.setLocalPath("C:\\gcsj\\shared\\"+file.getFileName());
        int result=fIleMapper.Share(file);
        if(result==0){
            return false;
        }
        FileUtils.forceMkdir(new java.io.File("C:\\gcsj\\shared"));
        FileUtils.copyFile(new java.io.File(Path),new java.io.File("C:\\gcsj\\shared\\"+file.getFileName()));
        return true;
    }

    public boolean unShare(int id){
        File file=fIleMapper.SelectShare(id);
        int result=fIleMapper.unShare(id);
        if(result==0){
            return false;
        }
        FileUtils.deleteQuietly(new java.io.File(file.getLocalPath()));
        return true;
    }

    public boolean reName (int id,String fileName ){
        int result=fIleMapper.reName(id,fileName);
        if(result==0){
            return false;
        }
        return true;
    }

    public List<File> searchFile (int useid){
        return fIleMapper.rearchFile(useid);
    }

    public List<SharedFile> searchSharedFile(){
        return fIleMapper.rearchSharedFile();
    }

    public boolean toBin(int id){
        if (fIleMapper.toBin(id) > 0) {
            return true;
        }
        return false;
    }
    public boolean deleteFile(int id) throws IOException {
        String path = fIleMapper.findLocalPath(id);
        if (fIleMapper.deleteFile(id) > 0) {
            java.io.File deleteFile = new java.io.File(path);
            if (deleteFile.exists() == true) {
                FileUtils.forceDelete(deleteFile);
            }
            return true;
        }
        return false;
    }

}
