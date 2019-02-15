package cn.pings.service.api.common.entity;

/**
 *********************************************************
 ** @desc  ： 图表预览
 ** @author  Pings
 ** @date    2019/1/31
 ** @version v1.0
 * *******************************************************
 */
public abstract class AbstractReactTreeEntity extends AbstractTreeEntity {

    //**获取树实际的值
    public abstract String getValue();
    //**获取树显示的值
    public abstract String getTitle();
}
