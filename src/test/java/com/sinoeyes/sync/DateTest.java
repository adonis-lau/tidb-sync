package com.sinoeyes.sync;

import lombok.extern.slf4j.Slf4j;
import org.eu.cn.apache.utils.DateUtils;
import org.junit.jupiter.api.Test;

/**
 * 时间工具类测试
 *
 * @author adonis lau
 * @email adonis.liu@pharmeyes.com
 * @date 2025-01-20
 */
@Slf4j
public class DateTest {

    @Test
    public void addMinutesTest() {
        String dateTime = DateUtils.dateTime(DateUtils.addMinutes(DateUtils.parseDate("2025-01-01 00:01:01"), -10));
        log.info("dateTime: {}", dateTime);
    }
}
