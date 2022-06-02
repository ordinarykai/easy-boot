package com.easy.boot.mybatis.gennerator;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.easy.boot.mybatis.bo.DataBaseBO;
import com.easy.boot.mybatis.bo.DatabaseDocGeneratorBO;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;

/**
 * 数据库文档生成
 *
 * @author kai
 */
public class DatabaseDocGenerator {

    private static DatabaseDocGeneratorBO databaseDocGeneratorBO;

    /**
     * 文档生成
     */
    public static void execute() {
        init();
        // 配置
        Configuration config = Configuration.builder()
                // 版本
                .version(databaseDocGeneratorBO.getVersion())
                // 描述
                .description(databaseDocGeneratorBO.getDescription())
                // 数据源
                .dataSource(dataSource())
                // 文件生成配置
                .engineConfig(engineConfig())
                // 数据处理
                .produceConfig(processConfig())
                .build();
        // 执行生成
        new DocumentationExecute(config).execute();
    }

    private static void init(){
        databaseDocGeneratorBO = new DatabaseDocGeneratorBO();
    }

    /**
     * 获取数据源
     */
    private static DataSource dataSource() {
        DataBaseBO dataBaseBO = new DataBaseBO();
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dataBaseBO.getUrl());
        hikariConfig.setUsername(dataBaseBO.getUsername());
        hikariConfig.setPassword(dataBaseBO.getPassword());
        // 设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema",
                "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        return new HikariDataSource(hikariConfig);
    }

    /**
     * 文件生成配置
     */
    private static EngineConfig engineConfig() {
        return EngineConfig.builder()
                // 生成文件路径
                .fileOutputDir(databaseDocGeneratorBO.getOutputDir())
                // 打开目录
                .openOutputDir(true)
                // 文件类型
                .fileType(EngineFileType.HTML)
                // 生成模板实现
                .produceType(EngineTemplateType.freemarker)
                // 自定义文件名称
                .fileName(databaseDocGeneratorBO.getFileName()).build();
    }

    /**
     * 数据处理
     */
    private static ProcessConfig processConfig() {
        // TODO: 2022/6/2 指定生成逻辑
        // 当存在指定表、指定表前缀、指定表后缀时，将生成指定表
        // 其余表不生成，并跳过忽略表配置
        return ProcessConfig.builder()
                // 根据名称指定表生成
                .designatedTableName(new ArrayList<>())
                // 根据表前缀生成
                .designatedTablePrefix(new ArrayList<>())
                // 根据表后缀生成
                .designatedTableSuffix(new ArrayList<>())
                // 忽略表名
                .ignoreTableName(new ArrayList<>())
                // 忽略表前缀
                .ignoreTablePrefix(new ArrayList<>())
                // 忽略表后缀
                .ignoreTableSuffix(new ArrayList<>()).build();
    }

}
