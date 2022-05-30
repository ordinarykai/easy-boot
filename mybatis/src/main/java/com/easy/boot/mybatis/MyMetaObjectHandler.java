package com.easy.boot.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * MybatisPlus公共字段插入更新策略
 *
 * @author kai
 * @date 2022/3/12 13:19
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 1、目前的插入更新策略：不管插入字段是否有值，都插入当前值（当前时间）
     * 因此此策略下手动设置字段值是无用的，实际执行的sql都会更新字段值为当前值（当前时间）
     * 2、注释代码的插入更新策略：插入字段有值，插入字段值；没有值，插入当前值（当前时间）
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
//        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
//        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
//        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

}

