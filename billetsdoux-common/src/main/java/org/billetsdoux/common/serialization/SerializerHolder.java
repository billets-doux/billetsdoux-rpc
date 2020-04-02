package org.billetsdoux.common.serialization;

import org.billetsdoux.common.spi.BaseServiceLoader;
/***
* @Description: 基于SPI方式的序列化
* @Param:
* @return:
* @Author: billets-doux
* @Date: 12:43 上午 2020/4/3
*/
public final class SerializerHolder {
    private static final Serializer serializer = BaseServiceLoader.load(Serializer.class);

    /***
    * @Description: 返回一个提供序列化方式的对象
    * @Param: []
    * @return: org.billetsdoux.common.serialization.Serializer
    * @Author: billets-doux
    * @Date: 12:45 上午 2020/4/3
    */
    public static Serializer serializer(){
        return serializer;
    }
}
