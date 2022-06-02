package com.easy.boot.mybatis.bo;

import com.easy.boot.mybatis.util.YamlUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author kai
 * @date 2022/3/12 14:41
 */
public class CodeGeneratorBO {

    private String author;

    private String outputDir;

    private String parent;

    private String moduleName;

    private List<String> tablePrefix;

    private List<String> includes;

    private DataBaseBO dataBaseBO;

    public CodeGeneratorBO() {
        dataBaseBO = new DataBaseBO();
        author = YamlUtil.getPropertyAndAssert("generator.code.author");
        outputDir = YamlUtil.getPropertyAndAssert("generator.code.output-dir");
        parent = YamlUtil.getPropertyAndAssert("generator.code.parent");
        moduleName = YamlUtil.getProperty("generator.code.module-name", "");
        //noinspection unchecked
        tablePrefix = (List<String>) YamlUtil.get("generator.code.table-prefix", Collections.emptyList());
        //noinspection unchecked
        includes = (List<String>) YamlUtil.get("generator.code.include", Collections.emptyList());
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<String> getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(List<String> tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public List<String> getIncludes() {
        return includes;
    }

    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }

    public DataBaseBO getDataBaseConfig() {
        return dataBaseBO;
    }

    public void setDataBaseConfig(DataBaseBO dataBaseBO) {
        this.dataBaseBO = dataBaseBO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CodeGeneratorBO that = (CodeGeneratorBO) o;
        return Objects.equals(author, that.author) && Objects.equals(outputDir, that.outputDir) && Objects.equals(parent, that.parent) && Objects.equals(moduleName, that.moduleName) && Objects.equals(tablePrefix, that.tablePrefix) && Objects.equals(includes, that.includes) && Objects.equals(dataBaseBO, that.dataBaseBO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, outputDir, parent, moduleName, tablePrefix, includes, dataBaseBO);
    }

    @Override
    public String toString() {
        return "CodeGeneratorConfig{" +
                "author='" + author + '\'' +
                ", outputDir='" + outputDir + '\'' +
                ", parent='" + parent + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", tablePrefix='" + tablePrefix + '\'' +
                ", includes='" + includes + '\'' +
                ", dataBaseConfig=" + dataBaseBO +
                '}';
    }

}