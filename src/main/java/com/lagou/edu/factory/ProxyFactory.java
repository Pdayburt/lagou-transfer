package com.lagou.edu.factory;

import com.lagou.edu.pojo.Account;
import com.lagou.edu.utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    private TransactionManager transactionManager;

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

//    private ProxyFactory(){}
//    private static ProxyFactory proxyFactory = new ProxyFactory();
//    public static ProxyFactory getProxyFactory(){
//        return proxyFactory;
//    }

    public Object getProxy(Object obj){
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object result=null;
                        try {
                            transactionManager.beginTransaction();
                            result = method.invoke(obj,args);
                            transactionManager.commit();
                        }catch (Exception e) {
                            transactionManager.rollback();
                            throw e;
                        }
                        return result;
                    }
                });
    }
}
