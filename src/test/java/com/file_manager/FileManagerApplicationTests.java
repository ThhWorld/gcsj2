package com.file_manager;

import com.file_manager.mapper.AdminMapper;
import com.file_manager.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class FileManagerApplicationTests {

    @Autowired
    private AdminMapper adminMapper;
    @Test
    public void test() throws IOException {
        System.out.println(adminMapper.deleteSharedFile(11));
    }


}
