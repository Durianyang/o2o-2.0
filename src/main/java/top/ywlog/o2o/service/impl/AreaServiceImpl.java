package top.ywlog.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.ywlog.o2o.util.RedisUtil;
import top.ywlog.o2o.dao.AreaDao;
import top.ywlog.o2o.entity.Area;
import top.ywlog.o2o.exceptions.AreaOperationException;
import top.ywlog.o2o.service.AreaService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/25 14:49
 * Description: 区域业务实现类
 */
@Service
public class AreaServiceImpl implements AreaService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AreaServiceImpl.class);
    private final AreaDao areaDao;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    public AreaServiceImpl(AreaDao areaDao)
    {
        this.areaDao = areaDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Area> list()
    {
        List<Area> areaList;
        ObjectMapper mapper = new ObjectMapper();
        // 如果key不存在，或者value为空
        if (!redisUtil.hasKey(AREA_LIST_KEY) || redisUtil.get("areaList") == null)
        {
            // redis中不存在，查询后放入redis
            areaList = areaDao.list();
            String jsonValue;
            try
            {
                jsonValue = mapper.writeValueAsString(areaList);
                System.err.println(jsonValue);
            } catch (JsonProcessingException e)
            {
                LOGGER.error("json转换失败！" + e.getMessage());
                throw new AreaOperationException(e.getMessage());
            }
            redisUtil.set(AREA_LIST_KEY, jsonValue);
        } else
        {
            String jsonValue = (String) redisUtil.get("areaList");
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
            try
            {
                areaList = mapper.readValue(jsonValue, javaType);
            } catch (IOException e)
            {
                LOGGER.error("json转换失败！" + e.getMessage());
                throw new AreaOperationException(e.getMessage());
            }

        }
        return areaList;
    }
}
