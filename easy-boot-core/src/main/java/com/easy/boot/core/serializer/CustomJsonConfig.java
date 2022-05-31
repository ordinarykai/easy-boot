package com.easy.boot.core.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.util.List;

/**
 * null值处理
 * 数组/集合为空返回 []
 * map类型返回 {}
 * String类型返回 ""
 * 其它类型暂时不管
 *
 * @author kai
 */
@Configuration
public class CustomJsonConfig {

    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = converter.getObjectMapper();
        // 为mapper注册一个带有SerializerModifier的Factory，此modifier主要做的事情为null值处理
        // 数组/集合为空返回 [] , map类型返回 {} , String类型返回 "" , 其它类型暂时不管
        mapper.setSerializerFactory(mapper.getSerializerFactory().withSerializerModifier(new CustomBeanSerializerModifier()));
        return converter;
    }

    /**
     * 空数组/集合序列化处理
     */
    public static class NullArrayJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (obj == null) {
                jsonGenerator.writeStartArray();
                jsonGenerator.writeEndArray();
            }
        }
    }

    /**
     * 空Map序列化处理
     */
    public static class NullMapJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (obj == null) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeEndObject();
            }
        }
    }

    /**
     * 空字符串序列化处理
     */
    public static class NullStringJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (obj == null) {
                jsonGenerator.writeString("");
            }
        }
    }

    public static class CustomBeanSerializerModifier extends BeanSerializerModifier {
        /**
         * 数组类型
         */
        private final JsonSerializer<Object> nullArrayJsonSerializer = new NullArrayJsonSerializer();
        /**
         * map类型
         */
        private final JsonSerializer<Object> nullMapJsonSerializer = new NullMapJsonSerializer();
        /**
         * 字符串类型
         */
        private final JsonSerializer<Object> nullStringJsonSerializer = new NullStringJsonSerializer();

        @Override
        public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                         List<BeanPropertyWriter> beanProperties) {
            for (BeanPropertyWriter writer : beanProperties) {
                // 判断字段的类型，进行不同的空值处理
                if (isArrayType(writer)) {
                    writer.assignNullSerializer(this.nullArrayJsonSerializer);
                } else if (isMapType(writer)) {
                    writer.assignNullSerializer(this.nullMapJsonSerializer);
                } else if (isStringType(writer)) {
                    writer.assignNullSerializer(this.nullStringJsonSerializer);
                }
            }
            return beanProperties;
        }

        private boolean isStringType(BeanPropertyWriter writer) {
            return writer.getType().getRawClass().equals(String.class);
        }

        private boolean isMapType(BeanPropertyWriter writer) {
            return writer.getType().isMapLikeType();
        }

        protected boolean isArrayType(BeanPropertyWriter writer) {
            JavaType javaType = writer.getType();
            return javaType.isArrayType() || javaType.isCollectionLikeType();
        }
    }

}