package cn.pings.service.sys;

import cn.pings.service.api.sys.entity.User;
import cn.pings.service.api.sys.service.TestService;
import cn.pings.service.api.sys.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

	@Autowired
	private UserService userService;

	@Test
	public void getByUserName(){
		User user = this.userService.getByUserName("pings");
		System.out.println(user);
	}

}

