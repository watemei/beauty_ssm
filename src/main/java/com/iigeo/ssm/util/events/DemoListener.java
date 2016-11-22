package com.iigeo.ssm.util.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DemoListener {
    @EventListener //注意此处
    public void handleDemoEvent(DemoEvent event){
        System.out.println("我监听到了pulisher发布的message为:"+event.getMsg());

    }

}