package com.lagou.edu.utils;

import java.sql.SQLException;

public class TransactionManager {
//    private TransactionManager(){}
    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    public  void beginTransaction() throws SQLException {
//        ConnectionUtils.getCurrentThreadConn().setAutoCommit(false);
        connectionUtils.getCurrentThreadConn().setAutoCommit(false);
    }
    public  void commit() throws SQLException {
//        ConnectionUtils.getCurrentThreadConn().commit();
        connectionUtils.getCurrentThreadConn().commit();
    }
    public  void rollback() throws SQLException {
//        ConnectionUtils.getCurrentThreadConn().rollback();
        connectionUtils.getCurrentThreadConn().rollback();
    }
}
