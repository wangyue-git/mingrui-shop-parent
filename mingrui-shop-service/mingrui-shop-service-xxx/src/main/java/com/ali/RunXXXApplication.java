package com.ali;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName RunXXXApplication
 * @Description: TODO
 * @Author wangyue
 * @Date 2020/12/23
 * @Version V1.0
 **/
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.ali.shop.mapper")//引入mapper
public class RunXXXApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunXXXApplication.class);
    }
}
