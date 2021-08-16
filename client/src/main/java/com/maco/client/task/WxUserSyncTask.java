package com.maco.client.task;

import com.maco.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Configuration
public class WxUserSyncTask {
    ExecutorService executorService = Executors.newFixedThreadPool(1);
    @Autowired
    private WxUserService wxUserService;
    @Scheduled(cron = "0 20 21 * * ?")
    private void syncWxUser(){
        executorService.execute(() -> {
            try {
                wxUserService.syncUser();
            } catch (Exception e) {
                log.error("syncUser方法异常", e);
            }
        });
    }
}
