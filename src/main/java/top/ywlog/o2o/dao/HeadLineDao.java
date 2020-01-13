package top.ywlog.o2o.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.ywlog.o2o.entity.HeadLine;

import java.util.List;

/**
 * Author: Durian
 * Date: 2020/1/2 14:37
 * Description: 头条dao接口
 */
@Repository
public interface HeadLineDao
{
    /**
     * 根据条件查询头条
     *
     * @param headLineCondition 查询条件
     * @return List<HeadLine>
     */
    List<HeadLine> listHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
