package top.ywlog.o2o;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import top.ywlog.o2o.interceptor.shop.ShopLoginInterceptor;
import top.ywlog.o2o.interceptor.shop.ShopPermissionInterceptor;

@SpringBootApplication
@MapperScan("top.ywlog.o2o.dao")
@ServletComponentScan
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@EnableScheduling
@EnableCaching
@ImportResource(locations = { "classpath:druid-bean.xml" })
@EnableTransactionManagement
public class O2oApplication implements WebMvcConfigurer, ApplicationContextAware
{
    private ApplicationContext applicationContext;

    public static void main(String[] args)
    {
        SpringApplication.run(O2oApplication.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:/o2o/images/upload/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        InterceptorRegistration loginIR = registry.addInterceptor(new ShopLoginInterceptor());
        InterceptorRegistration permissionIR = registry.addInterceptor(new ShopPermissionInterceptor());
        // 配置拦截路径
        loginIR.addPathPatterns("/shop/**");
        loginIR.addPathPatterns("/front/**");
        loginIR.addPathPatterns("/local");
        loginIR.excludePathPatterns("/local/login");
        permissionIR.addPathPatterns("/shop/**");
        permissionIR.excludePathPatterns("/shop/getShopList");
        permissionIR.excludePathPatterns("/shop/shopList");
        permissionIR.excludePathPatterns("/shop/getShopManagementInfo");
        permissionIR.excludePathPatterns("/shop/shopManagement");
        permissionIR.excludePathPatterns("/shop/getShopInitInfo");
        permissionIR.excludePathPatterns("/shop/registerShop");
        permissionIR.excludePathPatterns("/shop/shopOperation");
        permissionIR.excludePathPatterns("/shop/logout");
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean()
    {
        ServletRegistrationBean servlet = new ServletRegistrationBean<>(
                new KaptchaServlet(), "/kaptcha");
        servlet.addInitParameter("kaptcha.border", "no");
        servlet.addInitParameter("kaptcha.textproducer.font.color", "red");
        servlet.addInitParameter("kaptcha.textproducer.char.string", "ACDEFHKPRTWX345679");
        servlet.addInitParameter("kaptcha.image.width", "135");
        servlet.addInitParameter("kaptcha.image.height", "50");
        servlet.addInitParameter("kaptcha.textproducer.font.size", "43");
        servlet.addInitParameter("kaptcha.noise.color", "black");
        servlet.addInitParameter("kaptcha.textproducer.char.length", "4");
        servlet.addInitParameter("kaptcha.textproducer.font.names", "Arial");
        return servlet;
    }
    /**
     * 设置文件上传解析器
     */
    @Bean("multipartResolver")
    public CommonsMultipartResolver createCommonsMultipartResolver()
    {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        // 1024 * 1024 * 1024
        multipartResolver.setMaxUploadSize(20971520);
        multipartResolver.setMaxInMemorySize(20971520);
        return multipartResolver;
    }

    /**
     * 创建视图解析器
     */
    @Bean("viewResolver")
    public ViewResolver createViewResolve()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setApplicationContext(this.applicationContext);
        // 取消缓存
        viewResolver.setCache(false);
        // 设置解析前缀
        viewResolver.setPrefix("/html/");
        // 后缀
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
    }
}
