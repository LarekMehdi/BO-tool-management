package fr.mehdi.tool_management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerUiWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Swagger UI
        registry.addResourceHandler("/api/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/4.28.0/")
                .resourceChain(false);

        // API docs
        registry.addResourceHandler("/api/v3/api-docs/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .resourceChain(false);
    }
}