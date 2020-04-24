package com.ningyuan.swagger;

import com.ningyuan.core.Conf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration //必须存在
@EnableSwagger2 //必须存在
@EnableWebMvc //必须存在
@ComponentScan(basePackages = {"com.ningyuan.*.controller"})
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        Logger log = LoggerFactory.getLogger(getClass());
        log.info("扫描到SwaggerConfig这个bean");
        if (Boolean.parseBoolean(Conf.get("swagger.show"))) {
            //添加head参数start
            ParameterBuilder tokenPar = new ParameterBuilder();
            List<Parameter> pars = new ArrayList<Parameter>();
            tokenPar.name("openId")
                    .description("openId")
                    .modelRef(new ModelRef("string"))
                    .parameterType("header").required(false).build();
            pars.add(tokenPar.build());

            return new Docket(DocumentationType.SWAGGER_2).host(Conf.get("swagger.host")).apiInfo(apiInfo()).select()
                    .paths(PathSelectors.regex(Conf.get("swagger.paths.regex")))
                    .build()
                    .globalOperationParameters(pars)
                    ;
        } else {
            return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                    .paths(PathSelectors.none())  //如果是线上环境，添加路径过滤，设置为全部都不符合
                    .build();
        }
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("xxx", "http://www.ningyuanxinxi.com/", "xxx@qq.com");
        return new ApiInfoBuilder().title("API接口").description("API接口").contact(contact).version("1.1.0").build();
    }
}
