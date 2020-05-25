package com.chenfu.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


/**
 * @Description: 自定义响应结构, 转换类
 */
public class JsonUtils {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string+"$";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param clazz 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String findObject(String json){
        int index = json.indexOf('{');
        if(index==-1){
            return json;
        }
        return json.substring(index,json.length());
    }

    public static String findInnerObject(String json) {
        char[] chars = json.toCharArray();
        int i =0;
        int j =chars.length-1;
        boolean flag=true;
        while (i<j) {
            if (chars[i]=='{'){
                if(flag==true) {
                    flag =false;
                }else {
                    break;
                }
            }
            i++;
        }
        while (i < j) {
            if (chars[j]=='}'){
                if(flag==false) {
                    flag =true;
                }else {
                    break;
                }
            }
            j--;
        }
        if (i >= j) {
            return null;
        }
        return json.substring(i,j+1);
    }

}
