package cn.pings.service.sys;

import cn.pings.service.api.sys.entity.Right;
import cn.pings.service.api.sys.service.RightService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 测试权限信息
 ** @author  Pings
 ** @date    2019/06/10
 ** @version v1.0
 * *******************************************************
 */
public class RightServiceTests extends AbstractBaseServiceTest<Right, RightService> {

	private String code = "test1234567890";

	@Test
	public void getByCode(){
		Right entity = this.baseService.getByCode(this.code);
		Assert.assertNotNull(entity);
	}

	@Test
	public void findTreeAll(){
		List<Right> list = this.baseService.findTreeAll();
		Assert.assertNotNull(list);
	}

	@Test
	public void findByRoleId(){
		List<Right> list = this.baseService.findByRoleId(1);
		Assert.assertNotNull(list);
	}

	@Override
	public void save(){
		Right entity = new Right();
		entity.setCode("test12345678901");
		entity.setName("test");
		entity.setAddWho(1);

		entity = this.baseService.save(entity);
		Assert.assertNotNull(entity.getId());

		entity.setDescription("description");
		entity = this.baseService.save(entity);
		Assert.assertEquals("description", entity.getDescription());
	}

	@Override
	protected Right getTestEntity() {
		Right entity = new Right();
		entity.setCode(this.code);
		entity.setName("test");
		entity.setAddWho(1);

		return entity;
	}

}

