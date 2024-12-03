package com.sinoeyes.sync.service.datax;

import com.alibaba.datax.core.Engine;
import com.sinoeyes.sync.properties.DataXProperties;
import lombok.extern.slf4j.Slf4j;
import org.eu.cn.apache.utils.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author adonis lau
 * @email adonis.liu@pharmeyes.com
 * @date 2024-12-03
 */
@Slf4j
@Service
public class DataXJobService {

    @Autowired
    private DataXProperties dataXProperties;

    /**
     * 获取job目录下的有效任务
     * <p>
     * 符合 *.enable.json 命名规则的文件为有效任务文件
     *
     * @return
     */
    private List<String> getJobs() {
        // 读取 jobPath 目录下的文件
        List<String> jobs = SystemUtils.findEnableJsonFiles(dataXProperties.getJobPath());
        log.info("jobs: {}", jobs);
        return jobs;
    }

    /**
     * 执行 DataX 任务
     */
    public void execute() {
        List<String> jobs = getJobs();
        jobs.forEach(job -> {
            // 执行任务
            log.info("execute job: {}", job);
            String[] datxArgs2 = {"-job", job, "-mode", "standalone", "-jobid", "-1"};
            try {
                Engine.entry(datxArgs2);
            } catch (Throwable e) {
                log.error(e.getLocalizedMessage(), e);
            }
            log.info("job {} exec success", job);
        });
    }
}
