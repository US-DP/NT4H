package org.springframework.samples.nt4h.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.FormatterRegistry;
import org.springframework.samples.nt4h.turn.annotations.OnlyCurrentPlayerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


public class WebConfig implements WebMvcConfigurer {

	private final GenericIdToEntityConverter idToEntityConverter;
    private final OnlyCurrentPlayerInterceptor onlyCurrentPlayerInterceptor;

    @Autowired
    public WebConfig(GenericIdToEntityConverter idToEntityConverter, OnlyCurrentPlayerInterceptor onlyCurrentPlayerInterceptor) {
        this.idToEntityConverter = idToEntityConverter;
        this.onlyCurrentPlayerInterceptor = onlyCurrentPlayerInterceptor;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(idToEntityConverter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(onlyCurrentPlayerInterceptor);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        registry.viewResolver(resolver);
    }
}
