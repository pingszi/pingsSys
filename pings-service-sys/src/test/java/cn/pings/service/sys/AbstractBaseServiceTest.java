package cn.pings.service.sys;


import cn.pings.sys.commons.entity.AbstractBaseEntity;
import cn.pings.sys.commons.service.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 *********************************************************
 ** @desc  ： 测试基础信息
 ** @author  Pings
 ** @date    2019/6/03
 ** @version v1.0
 * *******************************************************
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public abstract class AbstractBaseServiceTest<U extends AbstractBaseEntity, T extends BaseService<U>> {

    @Autowired
    protected T baseService;
    private Class<U> entityClass;
    protected Integer id;

    public AbstractBaseServiceTest() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.entityClass = (Class<U>) type.getActualTypeArguments()[0];
    }

    @Before
    public void insertTestData(){
        this.id = this.baseService.save(this.getTestEntity()).getId();
    }

    @After
    public void deleteTestData(){
        this.baseService.deleteById(this.id);
    }

    @Test
    public void getById(){
        U entity = this.baseService.getById(this.id);
        Assert.assertNotNull(entity);
    }

    @Test
    public void findPage() throws IllegalAccessException, InstantiationException {
        Page<U> page = new Page<>();
        page.setSize(10);
        page.setCurrent(1);

        IPage<U> entity = this.baseService.findPage(page, this.entityClass.newInstance());
        Assert.assertNotNull(entity);
    }

    @Test
    public void findAll(){
        List<U> list = this.baseService.findAll();
        Assert.assertNotNull(list);
    }

    @Test
    @Rollback(false)
    public void deleteById(){
        int rst = this.baseService.deleteById(this.id);
        Assert.assertEquals(1, rst);
    }

    @Test
    public abstract void save();

    /**
     *********************************************************
     ** @desc ： 获取一个用于测试的实体对象
     ** @author Pings
     ** @date   2019/06/10
     ** @return entity
     * *******************************************************
     */
    protected abstract U getTestEntity();
}
