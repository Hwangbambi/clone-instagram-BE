package com.hanghae.cloneinstagram.config.model;

import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.services.cloud9.model.Environment;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Configuration
@EnableSwagger2
@Import (BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

     @Bean
     public Docket api() {
          return new Docket(DocumentationType.SWAGGER_2)
               .select()
               .apis(RequestHandlerSelectors.basePackage("com.hanghae.cloneinstagram")) // 특정 패키지경로를 API문서화 한다. 1차 필터
               .paths(PathSelectors.any()) // apis중에서 특정 path조건 API만 문서화 하는 2차 필터 "/**"
               .build()
               .groupName("API 1.0.0") // group별 명칭을 주어야 한다.
               .pathMapping("/")
               .apiInfo(apiInfo())
               .securityContexts(Arrays.asList(securityContext()))
               .securitySchemes(Arrays.asList(apiKey()))
               .useDefaultResponseMessages(false); // 400,404,500 .. 표기를 ui에서 삭제한다.
     }
     private ApiInfo apiInfo() {
          return new ApiInfoBuilder()
               .title("인스타그램 클론코딩 REST API")
               .description("항해 인스타그램 프로젝트 swagger")
               .version("1.0.0")
               .termsOfServiceUrl("")
               .contact(new Contact("gamemini", "https://github.com/clone-instagram/clone-instagram-BE", "joj1043@kakao.com"))
               .license("")
               .licenseUrl("")
               .build()
               ;
     }

     private ApiKey apiKey() {
          return new ApiKey("Authorization", "Authorization", "header");
     }

     private SecurityContext securityContext() {
          return SecurityContext
               .builder()
               .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
     }

     List<SecurityReference> defaultAuth() {
          AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
          AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
          authorizationScopes[0] = authorizationScope;
          return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
     }
     
//     @Bean
//     public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier
//          , ServletEndpointsSupplier servletEndpointsSupplier
//          , ControllerEndpointsSupplier controllerEndpointsSupplier
//          , EndpointMediaTypes endpointMediaTypes
//          , CorsEndpointProperties corsProperties
//          , WebEndpointProperties webEndpointProperties
//          , Environment environment) {
//          List<ExposableEndpoint<?>> allEndpoints = new ArrayList();
//          Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
//          allEndpoints.addAll(webEndpoints);
//          allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
//          allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
//          String basePath = webEndpointProperties.getBasePath();
//          EndpointMapping endpointMapping = new EndpointMapping(basePath);
//          boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
//          return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration()
//               , new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
//     }
//
//
//     private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
//          return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
//     }
}
