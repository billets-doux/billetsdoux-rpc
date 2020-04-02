package org.billetsdoux.common.spi;

import java.util.ServiceLoader;
/***
* @Description: SPI 的类加载器
* @Param:
* @return:
* @Author: billets-doux
* @Date: 12:39 上午 2020/4/3
*/
public class BaseServiceLoader {

    public static <T> T load(Class<T> serviceClass){
        return ServiceLoader.load(serviceClass).iterator().next();

    }
}
