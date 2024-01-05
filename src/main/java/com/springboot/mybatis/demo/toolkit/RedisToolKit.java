package com.springboot.mybatis.demo.toolkit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
@Component
public class RedisToolKit implements InitToolKit {

    private final static Logger logger = LoggerFactory.getLogger(RedisToolKit.class);

    @Value("${spring.redis.port}")
    private String port;
    @Override
    public void init() {
        try {
            InputStream in = getClass().getResourceAsStream("/application.properties");
            Properties prop = new Properties();
            prop.load(in);
        } catch (IOException e) {
            logger.error("获取application.properties失败！", e);
        }
    }
}
