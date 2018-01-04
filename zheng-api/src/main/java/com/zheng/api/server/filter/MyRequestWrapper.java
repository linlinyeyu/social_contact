package com.zheng.api.server.filter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * Created by acer on 2017/12/15.
 */
public class MyRequestWrapper extends HttpServletRequestWrapper {


    private final String body;
    public MyRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
        body = stringBuilder.toString();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new     ByteArrayInputStream(body.getBytes());

        ServletInputStream servletInputStream = new ServletInputStream() {
            private boolean isFinish = false;

            private boolean isReady = true;
            @Override
            public boolean isFinished() {

                System.out.print("isFinished:");
                return isFinish;
            }

            @Override
            public boolean isReady() {
                System.out.print("isReady:");
                return isReady;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                System.out.println("setReadListener");
            }

            @Override
            public int read() throws IOException {
                int i = byteArrayInputStream.read();
                isFinish = i == -1;
                isReady = i != -1;
                return i;
            }
        };
        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getBody() {
        return this.body;
    }
}
