package com.banner.user.config;



import com.banner.common.utils.JacksonObjectMapper;
import com.banner.user.utils.UserTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author rjj
 * @date 2022/9/16 - 8:45
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserTokenInterceptor()).addPathPatterns("/**");
    }

    /**
     * 扩展mvc消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器,底层使用jackson将java对象转换成JSON
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将自定义的消息转换器添加到mvc中转换器集合中(放在前面优先使用)
        converters.add(0, messageConverter);
    }

    //开放swagger与knife4j页面访问
    //因为doc.html是在jar包里的，需要使用资源处理器注册静态资源。
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/doc.html");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
