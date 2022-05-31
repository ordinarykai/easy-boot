package com.easy.boot.core.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.easy.boot.core.api.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kai
 * @date 2022/3/12 15:33
 */
@Slf4j
public class ExcelUtil {

    // 参考文档：https://alibaba-easyexcel.github.io/

    /**
     * 读取Excel文件，获取数据
     *
     * @param file 上传的Excel文件
     * @param obj  与获取数据同类型的对象
     * @param <T>  数据类型
     * @return 获取的数据集合
     */
    public static <T> List<T> read(MultipartFile file, T obj) {
        List<T> dataList = new ArrayList<>();
        try {
            EasyExcel.read(file.getInputStream(), obj.getClass(), new AnalysisEventListener<T>() {
                @Override
                public void invoke(T data, AnalysisContext analysisContext) {
                    log.info("解析到一条数据:{}", data);
                    dataList.add(data);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                    log.info("读取完毕:{}", analysisContext.readRowHolder().getRowIndex());
                }
            }).doReadAll();
        } catch (IOException e) {
            log.error("读取Excel发生异常", e);
            throw new ApiException("读取Excel失败");
        }
        if (dataList.isEmpty()) {
            throw new ApiException("未读取到数据");
        }
        return dataList;
    }

    /**
     * 写出Excel到流
     *
     * @param response http响应
     * @param dataList 写入Excel的数据
     * @param fileName Excel文件名
     * @param obj      与写入数据同类型的对象
     * @param <T>      写入数据类型
     * @throws IOException
     */
    public static <T> void write(HttpServletResponse response,
                                 String fileName,
                                 List<T> dataList,
                                 T obj) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), obj.getClass())
                .sheet()
                .doWrite(dataList);
    }

}
