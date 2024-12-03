package com.sinoeyes.sync.scheduler;

import com.sinoeyes.sync.service.datax.DataXJobService;
import com.sinoeyes.sync.properties.DataXProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author adonis lau
 * @email adonis.liu@pharmeyes.com
 * @date 2024-12-03
 */
@Slf4j
@Service
public class DataXScheduler {

    @Autowired
    private DataXProperties dataXProperties;

    @Autowired
    private DataXJobService dataXJobService;

    /**
     * 启动 DataX 调度任务
     * fixedRate: 以固定的时间间隔执行任务，单位是毫秒。
     * fixedDelay: 在上一次任务执行完成后，等待指定的时间间隔再执行下一次任务，单位是毫秒。
     * cron: 使用 cron 表达式来指定任务的执行时间。
     */
    @Scheduled(fixedDelayString = "${scheduled.datax.task.fixedDelay}")
    public void runTask() {
        log.info("DataX Scheduler Run, fixedDelay is {}", dataXProperties.getFixedDelay());
        // 在这里添加你希望定时执行的代码
        dataXJobService.execute();
    }
}
