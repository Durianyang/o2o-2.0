package top.ywlog.o2o.service;

import top.ywlog.o2o.entity.HeadLine;

import java.util.List;

/**
 * Author: Durian
 * Date: 2020/1/2 14:48
 * Description:
 */
public interface HeadLineService
{
    String HEADLINE_LIST_KEY = "headLineList";

    /**
     * 根据条件查询头条
     *
     * @param headLineCondition 查询条件
     * @return List<HeadLine>
     */
    List<HeadLine> listHeadLine(HeadLine headLineCondition);
}
