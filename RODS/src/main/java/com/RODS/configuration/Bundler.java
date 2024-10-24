package com.RODS.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration
public class Bundler {
    private final MessageSource messageSource;
    private final MessageSource logSource;
    @Autowired
    public Bundler(MessageSource messageSource, MessageSource logSource) {
        this.messageSource = messageSource;
        this.logSource = logSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    public String getMsg(String msg) {
        return messageSource.getMessage(msg, null, LocaleContextHolder.getLocale());
    }
    public String getLogMsg(String msg) {
        return logSource.getMessage(msg, null, Locale.ENGLISH);
    }
}
