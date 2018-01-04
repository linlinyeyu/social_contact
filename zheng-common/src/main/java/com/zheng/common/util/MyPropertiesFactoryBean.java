package com.zheng.common.util;

import org.springframework.beans.factory.config.PropertiesFactoryBean;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by acer on 2017/12/17.
 */
public class MyPropertiesFactoryBean extends PropertiesFactoryBean {

    @Override
    protected Properties createProperties() throws IOException {
        Properties properties = super.createProperties();
        Enumeration<String> propertyNames = (Enumeration<String>) properties.propertyNames();
        while(propertyNames.hasMoreElements()){
            String name = propertyNames.nextElement();
            if(name.matches("\\w+-\\w+")){
                String rawName = name;
                String[] split = rawName.split("-");
                Integer start = Integer.valueOf(split[0].substring(2));
                Integer end = Integer.valueOf(split[1].substring(2));
                for (int i = start; i <= end; i++) {
                    properties.setProperty("RF" + i, properties.getProperty(rawName));
                }
            }
        }
        return properties;
    }
}
