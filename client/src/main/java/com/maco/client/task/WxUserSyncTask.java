package com.maco.client.task;

import com.maco.service.WxUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class WxUserSyncTask {
    private static final Logger logger = LoggerFactory.getLogger(WxUserSyncTask.class);
    ExecutorService executorService = Executors.newFixedThreadPool(1);
    @Autowired
    private WxUserService wxUserService;
    @Scheduled(cron = "0 20 21 * * ?")
    private void syncWxUser(){
        executorService.execute(() -> {
            try {
                wxUserService.syncUser();
            } catch (Exception e) {
                logger.error("syncUser方法异常", e);
            }
        });
    }
}
