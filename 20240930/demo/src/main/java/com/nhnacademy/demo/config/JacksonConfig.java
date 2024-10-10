package com.nhnacademy.demo.config;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class JacksonConfig {

    public static class IntegerSerializer extends StdSerializer<Integer> {
        private static final long serialVersionUID = -7524016618355224119L;

        public IntegerSerializer() {
            super(Integer.class);
        }

        @Override
        public void serialize(Integer aInteger, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(aInteger.toString());
        }
    }
}