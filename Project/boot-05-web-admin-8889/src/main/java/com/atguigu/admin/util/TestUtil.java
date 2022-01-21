package com.atguigu.admin.util;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TestUtil {

    public static int a = 6;
    @Async
    public void executeTask(int b){
        System.out.println(a);
        try {
            Thread.sleep((long) ((Math.random() + 1) * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a = a + b;
        System.out.println("最终结果" + a);
    }
}