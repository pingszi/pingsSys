package cn.pings.sys.commons.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *********************************************************
 ** @desc  ： 图表预览
 ** @author  Pings
 ** @date    2019/1/31
 ** @version v1.0
 * *******************************************************
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractReactTreeEntity extends AbstractTreeEntity {

    //**获取树实际的值
    public abstract String getValue();
    //**获取树显示的值
    public abstract String getTitle();
}
