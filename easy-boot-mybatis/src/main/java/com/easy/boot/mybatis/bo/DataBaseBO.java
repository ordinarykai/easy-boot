package com.easy.boot.mybatis.bo;

import com.easy.boot.mybatis.util.YamlUtil;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * @author kai
 */
public class DataBaseBO {

    private String url;

    private String username;

    private String password;

    public DataBaseBO() {
        url = YamlUtil.getPropertyAndAssert("spring.datasource.url");
        username = YamlUtil.getPropertyAndAssert("spring.datasource.username");
        Assert.hasText(username, "please add property: spring.datasource.username");
        password = YamlUtil.getAndAssert("spring.datasource.password").toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataBaseBO that = (DataBaseBO) o;
        return Objects.equals(url, that.url) && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, username, password);
    }

    @Override
    public String toString() {
        return "DataBaseConfig{" +
                "url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
