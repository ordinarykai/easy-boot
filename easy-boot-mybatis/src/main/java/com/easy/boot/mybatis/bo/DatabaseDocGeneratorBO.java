package com.easy.boot.mybatis.bo;

import com.easy.boot.mybatis.util.YamlUtil;

import java.util.Objects;

/**
 * 数据库文档生成
 *
 * @author kai
 */
public class DatabaseDocGeneratorBO {

    private String outputDir;

    private String fileName;

    private String version;

    private String description;

    private DataBaseBO dataBaseBO;

    public DatabaseDocGeneratorBO() {
        outputDir = YamlUtil.getPropertyAndAssert("generator.doc.output-dir");
        fileName = YamlUtil.getProperty("generator.doc.file-name", "database-doc");
        version = YamlUtil.getProperty("generator.doc.version", "1.0.0");
        description = YamlUtil.getProperty("generator.doc.description", "数据库文档");
        dataBaseBO = new DataBaseBO();
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataBaseBO getDataBaseBO() {
        return dataBaseBO;
    }

    public void setDataBaseBO(DataBaseBO dataBaseBO) {
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
        DatabaseDocGeneratorBO that = (DatabaseDocGeneratorBO) o;
        return Objects.equals(outputDir, that.outputDir) && Objects.equals(fileName, that.fileName) && Objects.equals(version, that.version) && Objects.equals(description, that.description) && Objects.equals(dataBaseBO, that.dataBaseBO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outputDir, fileName, version, description, dataBaseBO);
    }

    @Override
    public String toString() {
        return "DatabaseDocGeneratorBO{" +
                "outputDir='" + outputDir + '\'' +
                ", fileName='" + fileName + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", dataBaseBO=" + dataBaseBO +
                '}';
    }

}
