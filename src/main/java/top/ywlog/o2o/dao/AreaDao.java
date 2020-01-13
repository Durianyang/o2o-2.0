package top.ywlog.o2o.dao;

import org.springframework.stereotype.Repository;
import top.ywlog.o2o.entity.Area;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/25 14:21
 * Description: AreaDao
 */
@Repository
public interface AreaDao
{
    /**
     * 查询全部区域列表
     *
     * @return List<Area>
     */
    List<Area> list();
}
