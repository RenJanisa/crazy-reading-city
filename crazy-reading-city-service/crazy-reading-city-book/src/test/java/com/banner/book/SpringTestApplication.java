package com.banner.book;

import cn.hutool.json.JSONUtil;
import com.banner.book.mapper.CrcBookRelationMapper;
import com.banner.model.book.pojos.CrcBook;
import com.banner.model.user.pojos.CrcUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author rjj
 * @date 2023/7/27 - 18:05
 */
@SpringBootTest(classes = CRCBookApplication.class)
@RunWith(SpringRunner.class)
public class SpringTestApplication {


    @Test
    public void demo(){

    }


}
