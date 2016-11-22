package com.iigeo.ssm.util.events;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DemoService {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = 
                  new AnnotationConfigApplicationContext("com.iigeo.ssm.util.events");
        DemoService main =context.getBean(DemoService.class);
        main.pulish(context);
        context.close();
    }
 public void pulish(ApplicationContext context){
        context.publishEvent(new DemoEvent(this, "22"));
    }
}