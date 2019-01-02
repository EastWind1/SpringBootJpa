package test.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import test.interceptor.TestInterceptor;

import java.util.Arrays;
import java.util.List;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    /**
     * 配置静态资源路径
     */

    @Value("${filedir.linux}")
    private String linuxdir;

    @Value("${filedir.windows}")
    private String windowsdir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String filedir = "";
        if("Windows_NT".equals(System.getenv("OS"))) {
            filedir = windowsdir;
        } else {
            filedir = linuxdir;
        }
        registry.addResourceHandler("file:" + filedir);
    }
    /**
     * MVC层跨域配置，即使在SpringSecurity中配置cors后也必须添加
     * @param registry
     */

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowCredentials(true)
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS");

    }

    /**
     * fastjson配置
     * @param converters
     */

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect
        );
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss"); // 此处在@RestController下有问题，尽量在pojo中使用@JsonFormat
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastJsonHttpMessageConverter);
    }

    /**
     * 拦截器配置
     * @param registry
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TestInterceptor());
    }


}
