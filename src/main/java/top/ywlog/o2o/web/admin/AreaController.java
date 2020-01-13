package top.ywlog.o2o.web.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ywlog.o2o.dto.JsonMapResult;
import top.ywlog.o2o.entity.Area;
import top.ywlog.o2o.service.AreaService;

import java.util.List;

/**
 * Author: Durian
 * Date: 2019/12/25 15:03
 * Description: Area控制器
 */
@Controller
@RequestMapping("/admin")
public class AreaController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AreaController.class);
    private final AreaService areaService;
    @Autowired
    public AreaController(AreaService areaService)
    {
        this.areaService = areaService;
    }

    @RequestMapping(value = "/listarea",method = RequestMethod.GET)
    @ResponseBody
    public JsonMapResult<Area> listArea()
    {
        LOGGER.info("执行listArea方法");
        JsonMapResult<Area> modelMap = new JsonMapResult<>();
        List<Area> areaList;
        try
        {
            areaList = areaService.list();
            modelMap.setTotal(areaList.size());
            modelMap.setRows(areaList);
            modelMap.setSuccess(true);
        } catch (Exception e)
        {
            LOGGER.error("执行listArea方法失败");
            e.printStackTrace();
            modelMap.setSuccess(false);
            modelMap.setErrMsg(e.toString());
        }
        LOGGER.info("执行listArea方法成功");
        return modelMap;
    }
}
