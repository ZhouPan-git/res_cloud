package com.yc;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 * @Author zp
 * @Date 2023/8/18 10:51
 * @PackageName:com.yc
 * @ClassName: TestLocalDbInit
 * @Description:
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppMain.class})
@ActiveProfiles("localinit")
public class TestLocalDbInit extends TestCase {
    @Autowired
    private DataSource ds;
    @Test
    public void testInit(){
        Assert.assertNotNull(ds);
        System.out.println(ds);
    }
}