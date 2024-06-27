package com.file_manager.service;


import ch.qos.logback.core.util.FileUtil;
import com.file_manager.mapper.FIleMapper;
import com.file_manager.pojo.File;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
}
