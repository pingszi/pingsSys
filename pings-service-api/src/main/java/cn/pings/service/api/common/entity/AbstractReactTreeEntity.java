package cn.pings.service.api.common.entity;
import java.util.List;
import java.util.Map;

/**
 *********************************************************
 ** @desc  ： 图表预览
 ** @author  Pings
 ** @date    2019/1/31
 ** @version v1.0
 * *******************************************************
 */
public abstract class AbstractReactTreeEntity extends AbstractTreeEntity {

    /**
     *********************************************************
     ** @desc ： 把经过toTreeList转换的树形结构数据转换为react ant树形结构数据
     ** @author Pings
     ** @date   2019/1/31
     ** @param  list   经过toTreeList转换的树形结构数据
     ** @return List
     * *******************************************************
     */
    public static <T extends AbstractReactTreeEntity> List toReactTreeList(List<T> list){
        toReactTree(list);
        return list;
    }

    private static void toReactTree(List list){
        list.forEach(node -> {
            Map<String, Object> newNode = ((AbstractReactTreeEntity)node).toReactTreeMap();
            list.remove(node);
            list.add(newNode);

            List childs = ((AbstractReactTreeEntity) node).getChilds();
            if(childs != null && !childs.isEmpty())
                toReactTree(childs);
        });
    }

    public abstract Map<String, Object> toReactTreeMap();
}
