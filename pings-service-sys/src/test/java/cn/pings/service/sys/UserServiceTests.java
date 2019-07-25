package cn.pings.service.sys;

import cn.pings.service.api.sys.entity.User;
import cn.pings.service.api.sys.service.UserService;
import org.junit.Assert;
import org.junit.Test;

/**
 *********************************************************
 ** @desc  ： 测试用户信息
 ** @author  Pings
 ** @date    2019/6/04
 ** @version v1.0
 * *******************************************************
 */
public class UserServiceTests extends AbstractBaseServiceTest<User, UserService> {

	private String userName = "test9999999999";

	@Test
	public void getByUserName(){
		User user = this.baseService.getByUserName(this.userName);
		Assert.assertNotNull(user);
	}

	@Test
	public void allotRole(){
		int rst = this.baseService.allotRole(this.id, new int[]{999998, 999999}, 1);
		Assert.assertEquals(2, rst);
	}

	@Override
	public void save(){
		User user = new User();
		user.setUserName("test9998888888");
		user.setPassword("123456");
		user.setAddWho(1);

		user = this.baseService.save(user);
		Assert.assertNotNull(user.getId());

		user.setAge(33);
		user = this.baseService.save(user);
		Assert.assertEquals(33, (long)user.getAge());
	}

	@Override
	protected User getTestEntity() {
		User entity = new User();
		entity.setUserName(this.userName);
		entity.setPassword("123456");
		entity.setAddWho(1);

		return entity;
	}
}

