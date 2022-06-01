package com.easy.boot.core.upload;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.extra.spring.SpringUtil;
import com.easy.boot.core.util.StringUtil;
import com.easy.boot.core.util.bo.FileVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author kai
 */
@Slf4j
@AllArgsConstructor
public class UploadUtil {

    private static final UploadProperties uploadProperties;

    static {
        uploadProperties = SpringUtil.getBean(UploadProperties.class);
    }

    /**
     * 上传文件到本地
     */
    public static FileVO uploadToLocal(MultipartFile file) throws IOException {
        //上传文件存储根目录
        String path = uploadProperties.getPath();
        Assert.notBlank(path, "请配置属性upload.path (上传文件存储根目录)");
        //上传文件上级目录
        String parentDirectory = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")) + File.separator;
        //随机文件名
        String fileName = StringUtil.getUuid() + StringUtil.getFileSuffix(file.getOriginalFilename());
        //创建文件并复制上传文件内容
        File targetFile = FileUtil.touch(path + parentDirectory + fileName);
        try (InputStream inputStream = file.getInputStream()) {
            FileUtil.writeFromStream(inputStream, targetFile);
        }
        FileVO fileVO = new FileVO();
        fileVO.setName(fileName);
        fileVO.setUri(parentDirectory + fileName);
        return fileVO;
    }

    /**
     * 获取上传文件地址前缀
     */
    public static String getBaseUploadUrl() {
        String url = uploadProperties.getUrl();
        if (StringUtils.isBlank(url)) {
            log.warn("请配置属性upload.url (上传文件地址前缀)");
        }
        return url;
    }

}
