package com.sinoeyes.sync.service.datax;

import com.alibaba.datax.core.Engine;
import com.sinoeyes.sync.properties.DataXProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eu.cn.apache.utils.DataXUtils;
import org.eu.cn.apache.utils.DateUtils;
import org.eu.cn.apache.utils.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
            try {
                // 执行任务
                log.info("execute job: {}", job);
                String writerMaxUpdateTime = DataXUtils.getWriterMaxUpdateTime(job);
                if (StringUtils.isBlank(writerMaxUpdateTime)) {
                    String readerMinUpdateTime = DataXUtils.getReaderMinUpdateTime(job);
                    if (StringUtils.isBlank(readerMinUpdateTime)) {
                        throw new Exception(String.format("readerMinUpdateTime is null, job: %s", job));
                    }
                    writerMaxUpdateTime = DateUtils.dateTime(DateUtils.addDays(Objects.requireNonNull(DateUtils.parseDate(readerMinUpdateTime)), -1));
                }
                System.setProperty("writerMaxUpdateTime", writerMaxUpdateTime);
                String[] datxArgs2 = {"-job", job, "-mode", "standalone", "-jobid", "-1"};

                Engine.entry(datxArgs2);
            } catch (Throwable e) {
                log.error(e.getLocalizedMessage(), e);
            }
            log.info("job {} exec success", job);
        });
    }
}
