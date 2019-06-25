package com.superl.skyDriver.plugin;


import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.log4j.Logger;

import java.util.Properties;

public class MyPlugin implements Interceptor {
    private Logger log = Logger.getLogger(MyPlugin.class);
    private Properties properties = null;
    /**
     * 插件方法，它将替代StatementHandler的prepare方法
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        // 进行绑定
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        Object object = null;
        /*分离代理对象链（由于目标类可能被多个拦截器【插件】拦截，
        从而形成多次代理，通过循环可以分离出原始的目标类）
        * */
        while(metaStatementHandler.hasGetter("h")){
            object = metaStatementHandler.getValue("h");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }
        statementHandler = (StatementHandler) object;
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        Long parameterObject = (Long) metaStatementHandler.getValue("delegate.boundSql.parameterObject");
        log.info("执行的SQL:【"+sql+"】");
        log.info("参数:【"+parameterObject+"】");
        log.info("before..........");
        System.out.println("执行的SQL:【"+sql+"】");
        System.out.println("参数:【"+parameterObject+"】");
        System.out.println("before..........");
        // 如果当前代理的时一个非代理对象，那么它就会调用真实拦截对象的方法
        // 如果不是，那么它会调度下一个插件代理对象的invoke方法
        Object obj = invocation.proceed();
        log.info("after...........");
        System.out.println("after...........");
        return obj;
    }

    /**
     * 生产代理对象
     * @param o
     * @return
     */
    @Override
    public Object plugin(Object o) {
        // 采用系统默认的Plugin.wrap 方法生产
        return Plugin.wrap(o, this);
    }

    /**
     * 设置参数， MyBatis初始化时就会生成插件，并且调用这个方法
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
        log.info("dbType="+this.properties.get("dbType"));
        System.out.println("dbType="+this.properties.get("dbType"));
    }
}
