package com.aitschool.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServiceApplication {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GatewayServiceApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("网关地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }
}
