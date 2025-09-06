package website.marcosfernandes.ecommerce.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class I18nConfig {
    @Bean
    public MessageSource messageSource() {
        var src = new ReloadableResourceBundleMessageSource();
        src.setBasename("classpath:messages,classpath:ValidationMessages");
        src.setDefaultEncoding("UTF-8");
        src.setFallbackToSystemLocale(false);
        return src;
    }

    @Bean
    public LocaleResolver localeResolver() {
        var r = new AcceptHeaderLocaleResolver();
        r.setDefaultLocale(Locale.ENGLISH);
        return r;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        var i = new LocaleChangeInterceptor();
        i.setParamName("lang");
        return i;
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(LocaleChangeInterceptor lci) {
        return new WebMvcConfigurer() {
            @Override public void addInterceptors(InterceptorRegistry reg) {
                reg.addInterceptor(lci);
            }
        };
    }
}