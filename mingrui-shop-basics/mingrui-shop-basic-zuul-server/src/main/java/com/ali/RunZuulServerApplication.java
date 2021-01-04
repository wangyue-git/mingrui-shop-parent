package com.ali;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @ClassName RunZuulServerApplication
 * @Description: TODO
 * @Author wangyue
 * @Date 2020/12/23
 * @Version V1.0
 **/
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class RunZuulServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunZuulServerApplication.class);
    }
}
