package cn.mislily.gmall.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "cn.mislily.gmall.manager.mapper")
public class GmallManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallManagerApplication.class, args);
    }
}
