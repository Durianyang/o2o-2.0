package top.ywlog.o2o.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ywlog.o2o.dao.AreaDao;
import top.ywlog.o2o.entity.Area;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Author: Durian
 * Date: 2020/1/13 17:34
 * Description:
 */
@RestController
public class TestController
{
    private final AreaDao areaDao;
    @Autowired
    public TestController(AreaDao areaDao)
    {
        this.areaDao = areaDao;
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request)
    {
        return request.getContextPath();
    }

}
