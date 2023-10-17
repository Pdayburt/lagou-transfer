package com.lagou.edu.factory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;


public class BeanFactory {

    private static HashMap<String, Object> map = new HashMap<>();

    static {
        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            List<Element> beanList = rootElement.selectNodes("//bean");

            for (int i = 0; i < beanList.size(); i++) {
                Element element = beanList.get(i);

                String id = element.attributeValue("id");
                String clazz = element.attributeValue("class");

                Class<?> aClass = Class.forName(clazz);
                Object o = aClass.newInstance();
                map.put(id,o);
            }

            //完成是实例化后维护对象的依赖关系，检查那些对象需要传值（有property子元素的bean）
            List<Element> propertyList = rootElement.selectNodes("//property");
            for (int i = 0; i < propertyList.size(); i++) {
//                <property name="AccountDao" ref="accountDao"></property>
                Element element = propertyList.get(i);
                String name = element.attributeValue("name");
                String ref = element.attributeValue("ref");
                Element parent = element.getParent();
                String parentId = parent.attributeValue("id");
                Object parentObject = map.get(parentId);
                Method[] methods = parentObject.getClass().getMethods();
                for (int j = 0; j < methods.length; j++) {
                    Method method = methods[j];
                    if (method.getName().equalsIgnoreCase("set"+name)) {
                        method.invoke(parentObject,map.get(ref));
                    }
                }

                map.put(parentId,parentObject);

            }


        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getBean(String id){
        return map.get(id);
    }

}
