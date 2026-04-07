package com.printing_shop.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This helps your browser see the images at http://localhost:8081/uploads/filename.jpg
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/"); // Pointing to the root uploads folder
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // FIX FOR THE 500 ERROR:
        // This allows Spring to read the "request" part as JSON even if Swagger sends it as octet-stream
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
        converters.add(converter);
    }
}