package cn.pings.service.sys;

import cn.pings.service.api.sys.entity.User;
import cn.pings.service.api.sys.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestServiceTests {

	@Autowired
	private TestService testService;

	@Test
	public void getById(){
		User user = this.testService.getById(1);
		System.out.println(user);
	}

	@Test
	public void save() {
		User user = new User();
		user.setUserName("test");
		user.setPassword("123456");
		user.setAddWho(1);

		assert this.testService.save(user) == 1;
		System.out.println(user);
	}

}

