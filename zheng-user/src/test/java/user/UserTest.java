package user;

import com.zheng.user.service.UcenterUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by acer on 2017/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext*.xml")
public class UserTest {
    @Autowired
    UcenterUserService ucenterUserService;

    @Test
    public void test(){
        ucenterUserService.loginPasswd("15906716507", "123456");
    }
}
