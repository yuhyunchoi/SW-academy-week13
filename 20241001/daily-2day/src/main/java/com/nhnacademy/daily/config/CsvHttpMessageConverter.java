package com.nhnacademy.daily.config;

import com.nhnacademy.daily.model.Member;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class CsvHttpMessageConverter extends AbstractHttpMessageConverter<Member> {

    public CsvHttpMessageConverter() {
        super(new MediaType("text", "csv"));
    }

    @Override
    protected boolean supports(Class<?> clazz){
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    protected Member readInternal(Class<? extends Member> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(Member myObjects, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        //CSV 데이터를 쓰기 위한 Writer생성
        OutputStream outputStream = outputMessage.getBody();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

        //CSV 헤더 추가
        writer.write("name,age, clazz\n");

        //Member객체의 필드를 CSV 형식으로 출력
        String line = myObjects.getId() + "," + myObjects.getName() + "," + myObjects.getClazz() + "\n";

        //Writer 플러시
        writer.flush();

    }

}
