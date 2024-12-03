package com.sinoeyes.sync;

import lombok.extern.slf4j.Slf4j;
import org.eu.cn.apache.utils.SystemUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import com.alibaba.fastjson2.JSON;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;

@Slf4j
@EnableScheduling
@EnableConfigurationProperties
@SpringBootApplication
public class TidbSyncApplication {

    public static void main(String[] args) {
        System.setProperty("datax.home", SystemUtils.getProjectRootDirectory() + "/datax");
        SpringApplication.run(TidbSyncApplication.class, args);
    }

}
