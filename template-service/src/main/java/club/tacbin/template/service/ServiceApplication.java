package club.tacbin.template.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tacbin
 * @createTime 2020/5/8 20:41
 * @description
 **/
@SpringBootApplication(scanBasePackages = {"club.tacbin"})
@MapperScan(basePackages = "club.tacbin")
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
