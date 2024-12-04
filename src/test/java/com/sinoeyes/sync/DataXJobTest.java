package com.sinoeyes.sync;

import com.alibaba.datax.core.Engine;
import lombok.extern.slf4j.Slf4j;
import org.eu.cn.apache.utils.DataXUtils;
import org.eu.cn.apache.utils.SystemUtils;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

/**
 * @author adonis lau
 * @email adonis.liu@pharmeyes.com
 * @date 2024-12-03
 */
@Slf4j
public class DataXJobTest {
    @Test
    public void runDataX() {
        log.info("getCurrentClasspath() is {}", SystemUtils.getCurrentClasspath());
        log.info("getProjectRootDirectory() is {}", SystemUtils.getProjectRootDirectory());
        log.debug("getCurrentClasspath() is {}", SystemUtils.getCurrentClasspath());
        log.debug("getProjectRootDirectory() is {}", SystemUtils.getProjectRootDirectory());

//        System.setProperty("datax.home", "F:\\SoftwareInstallationPackages\\bigdata\\DataX\\datax");
        System.setProperty("datax.home", SystemUtils.getProjectRootDirectory() + "/datax");
        String[] datxArgs2 = {"-job", SystemUtils.getProjectRootDirectory() + "/datax/job/datax.demo.local.json", "-mode", "standalone", "-jobid", "-1"};
        try {
            Engine.entry(datxArgs2);
        } catch (Throwable e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    @Test
    public void dataxUtilsTest() throws SQLException, ClassNotFoundException {
        String maxUpdateTime = DataXUtils.getWriterMaxUpdateTime("C:\\Users\\Adonis.liu\\Workspaces\\Code\\tidb-sync\\job\\uat2_proj_0015.fl_std_month_sale_view_sync_table.enable.json");
        log.info("maxUpdateTime: {}", maxUpdateTime);
    }
}
