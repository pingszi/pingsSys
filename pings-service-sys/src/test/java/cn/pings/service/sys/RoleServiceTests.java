package cn.pings.service.sys;

import cn.pings.service.api.sys.entity.Role;
import cn.pings.service.api.sys.service.RoleService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 测试角色信息
 ** @author  Pings
 ** @date    2019/06/10
 ** @version v1.0
 * *******************************************************
 */
public class RoleServiceTests extends AbstractBaseServiceTest<Role, RoleService> {

	private String code = "test1234567890";

	@Test
	public void getByCode(){
		Role entity = this.baseService.getByCode(this.code);
		Assert.assertNotNull(entity);
	}

	@Test
	public void findByUserId(){
		List<Role> list = this.baseService.findByUserId(1);
		Assert.assertNotNull(list);
	}

	@Test
	public void allotRight(){
		int rst = this.baseService.allotRight(this.id, new int[]{999998, 999999}, 1);
		Assert.assertEquals(2, rst);
	}

	@Override
	public void save(){
		Role entity = new Role();
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
	protected Role getTestEntity() {
		Role entity = new Role();
		entity.setCode(this.code);
		entity.setName("test");
		entity.setAddWho(1);

		return entity;
	}

}

