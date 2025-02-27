package com.sinoeyes.sync.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author adonis lau
 * @email adonis.liu@pharmeyes.com
 * @date 2024-12-03
 */
@Data
@Component
@ConfigurationProperties(prefix = "scheduled.datax.task")
public class DataXProperties {

    /**
     * DataX 任务路径
     */
    private String jobPath;

    /**
     * DataX 任务后续时间间隔
     */
    private long fixedDelay;

    /**
     * DataX 任务调度表达式
     */
    private String cron;
}
