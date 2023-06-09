package com.gs;

import com.gs.common.utils.SecurityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * 启动程序
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application
{
    public static void main(String[] args)
    {



        SpringApplication.run(Application.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ  \r\n"+ SecurityUtils.encryptPassword("123456"));
    }
}
