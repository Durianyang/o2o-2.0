package top.ywlog.o2o.service;

import top.ywlog.o2o.entity.Area;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/25 14:49
 * Description:
 */
public interface AreaService
{
    /** redis数据 库的key值 */
    String AREA_LIST_KEY = "areaList";
    /**
     * 查询所有区域信息
     * @return 区域信息
     */
    List<Area> list();
}
