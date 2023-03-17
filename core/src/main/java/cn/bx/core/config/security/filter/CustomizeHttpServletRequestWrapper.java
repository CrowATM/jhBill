package cn.bx.core.config.security.filter;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/** HttpServletRequest包装类,其父类实现HttpServletRequest接口
 * 通过重写getInputStream和getReader实现request输入流的重复读取
 * @Author zk
 * @CreateDateTime 2022/8/11 9:48
 */
public class CustomizeHttpServletRequestWrapper extends HttpServletRequestWrapper {

    //存储body数据的容器
    private final byte[] body;
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public CustomizeHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        //将body数据存起来
        body = StreamUtils.copyToByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {}

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
