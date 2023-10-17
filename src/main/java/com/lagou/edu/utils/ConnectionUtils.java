package com.lagou.edu.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {
//    private ConnectionUtils(){}
//    private ConnectionUtils connectionUtils = new ConnectionUtils();
//
//    public ConnectionUtils getConnectionUtils(){
//        return connectionUtils;
//    }

    private static ThreadLocal<Connection> connectionThreadLocal;
    public  Connection getCurrentThreadConn() throws SQLException {

        if (connectionThreadLocal == null) {
            synchronized (ConnectionUtils.class){
                if (connectionThreadLocal == null) {
                    connectionThreadLocal = new ThreadLocal<>();
                    connectionThreadLocal.set(DruidUtils.getInstance().getConnection());
                }
            }
        }
        return connectionThreadLocal.get();
    }
}
