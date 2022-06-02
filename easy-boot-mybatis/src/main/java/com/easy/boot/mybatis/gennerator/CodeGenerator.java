package com.easy.boot.mybatis.gennerator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.easy.boot.mybatis.bo.CodeGeneratorBO;
import com.easy.boot.mybatis.bo.DataBaseBO;

/**
 * @author kai
 * @date 2022/3/12 14:41
 */
public class CodeGenerator {

    public static void execute() {

        CodeGeneratorBO codeGeneratorBO = new CodeGeneratorBO();
        DataBaseBO dataBaseBO = codeGeneratorBO.getDataBaseConfig();

        FastAutoGenerator.create(dataBaseBO.getUrl(), dataBaseBO.getUsername(), dataBaseBO.getPassword())
                .globalConfig(builder -> {
                    // 设置作者
                    builder.author(codeGeneratorBO.getAuthor())
                            // 开启 swagger 模式
                            .enableSwagger()
                            // 指定输出目录
                            .outputDir(codeGeneratorBO.getOutputDir());
                })
                .packageConfig(builder -> {
                    // 设置父包名
                    builder.parent(codeGeneratorBO.getParent())
                            // 设置父包模块名
                            .moduleName(codeGeneratorBO.getModuleName());
                })
                .strategyConfig(builder -> {
                    // 设置需要生成的表名
                    builder.addInclude(codeGeneratorBO.getIncludes())
                            // 设置过滤表前缀
                            .addTablePrefix(codeGeneratorBO.getTablePrefix())
                            // RestController控制器
                            .controllerBuilder().enableRestStyle()
                            // 开启lombok模型
                            .entityBuilder().enableLombok()
                            // 开启链式模型
                            .enableChainModel()
                            // 开启ActiveRecord模式
                            .enableActiveRecord();
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

}