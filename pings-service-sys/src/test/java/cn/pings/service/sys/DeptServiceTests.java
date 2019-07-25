package cn.pings.service.sys;

import cn.pings.service.api.sys.entity.Dept;
import cn.pings.service.api.sys.service.DeptService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 测试部门信息
 ** @author  Pings
 ** @date    2019/06/10
 ** @version v1.0
 * *******************************************************
 */
public class DeptServiceTests extends AbstractBaseServiceTest<Dept, DeptService> {

	private String code = "test1234567890";

	@Test
	public void getByCode(){
		Dept entity = this.baseService.getByCode(this.code);
		Assert.assertNotNull(entity);
	}

	@Test
	public void findTreeAll(){
		List<Dept> list = this.baseService.findTreeAll();
		Assert.assertNotNull(list);
	}

	@Override
	public void save(){
		Dept entity = new Dept();
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
	protected Dept getTestEntity() {
		Dept entity = new Dept();
		entity.setCode(this.code);
		entity.setName("test");
		entity.setAddWho(1);

		return entity;
	}

}

