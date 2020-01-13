package top.ywlog.o2o.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.ywlog.o2o.entity.HeadLine;

import java.util.List;

/**
 * Author: Durian
 * Date: 2020/1/13 20:24
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HeadLineServiceTest
{
    @Autowired
    private  HeadLineService headLineService;

    @Test
    public void list()
    {
        HeadLine headLine = new HeadLine();
        List<HeadLine> headLineList = headLineService.listHeadLine(headLine);
        for (HeadLine h : headLineList)
        {
            System.out.println("headLine = " + h);
        }
    }

}
