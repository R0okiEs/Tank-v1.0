package com.hub.tank;

import java.io.IOException;
import java.util.Properties;

/**
 * @Auther: Hub
 * @Date: 2020-11-16 - 11 - 16 - 9:25
 * @Description: com.hub.tank
 * @version: 1.0
 */
public class PropertyMgr {
    static  Properties props = new Properties();
    private PropertyMgr(){}
   static {
       try {
           props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
    public static int getIntProps (String key){
           return Integer.parseInt(props.getProperty(key));
    }
    public static String getStringProps (String key){
        return props.getProperty(key);
    }

}
