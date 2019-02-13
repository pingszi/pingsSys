package cn.pings.service.api.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 *********************************************************
 ** @desc  ： 树形结构实体类
 ** @author  Pings
 ** @date    2019/1/31
 ** @version v1.0
 * *******************************************************
 */
public abstract class AbstractTreeEntity extends AbstractBaseEntity {

    //**默认的根结点父节点ID
    public static int DEFAULT_ROOT_PARENT_ID = -1;

    //**父节点ID
    private Integer parentId;
    //**子节点列表
    @TableField(exist = false)
    private List<AbstractTreeEntity> childs;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<AbstractTreeEntity> getChilds() {
        return childs;
    }

    public void setChilds(List<AbstractTreeEntity> childs) {
        this.childs = childs;
    }

    public AbstractTreeEntity instance() {
        return this;
    }

    /**
     *********************************************************
     ** @desc ： 转换为树形结构
     ** @author Pings
     ** @date   2019/1/31
     ** @param  list   需要转换的列表
     ** @return List
     * *******************************************************
     */
    public static <T extends AbstractTreeEntity> List<T> toTreeList(List<T> list){
        return toTreeList(list, DEFAULT_ROOT_PARENT_ID);
    }

    /**
     *********************************************************
     ** @desc ： 转换为树形结构
     ** @author Pings
     ** @date   2019/1/31
     ** @param  list    需要转换的列表
     ** @param  rootId  根结点ID
     ** @return List
     * *******************************************************
     */
    public static <T extends AbstractTreeEntity> List<T> toTreeList(List<T> list, int rootId){
        Map<Integer, AbstractTreeEntity> map = list.stream().collect(toMap(AbstractTreeEntity::getId, AbstractTreeEntity::instance));
        map.values().stream()
                .filter(node -> node.getParentId() != rootId)
                .forEach(node -> {
                    AbstractTreeEntity parentNode = map.get(node.getParentId());
                    if(parentNode != null){
                        parentNode.setChilds(new ArrayList<>());
                        parentNode.getChilds().add(node);
                    }
                });
        return map.values().stream()
                .filter(node -> node.getParentId() == rootId)
                .map(node -> (T)node)
                .collect(toList());
    }
}
