package org.billetsdoux.common.serialization.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.billetsdoux.common.serialization.Serializer;
/***
* @Description: 使用fastjson序列化
* @Param: 
* @return: 
* @Author: billets-doux
* @Date: 12:47 上午 2020/4/3
*/
public class FastjsonSerializer implements Serializer {
    
    @Override
    public <T> byte[] writeObject(T obj) {
        return JSON.toJSONBytes(obj, SerializerFeature.SortField);
    }

    @Override
    public <T> T readObject(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz, Feature.SortFeidFastMatch);
    }
}
