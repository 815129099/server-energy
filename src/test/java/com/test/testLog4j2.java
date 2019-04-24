package com.test;

import com.example.demo.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DemoApplication.class)
@SpringBootTest
public class testLog4j2 {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testLog(){
        logger.info("this is info");
        logger.warn("this is warn");
        logger.debug("this is debug");
        logger.error("this is error");
    }
}
