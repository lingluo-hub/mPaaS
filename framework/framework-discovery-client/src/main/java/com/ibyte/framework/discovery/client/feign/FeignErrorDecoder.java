package com.ibyte.framework.discovery.client.feign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibyte.common.exception.KmssServiceException;
import feign.Response;
import feign.RetryableException;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import static feign.Util.RETRY_AFTER;
import static feign.Util.checkNotNull;
import static java.util.Locale.US;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * feign client 自定义异常处理
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public class FeignErrorDecoder implements ErrorDecoder {

    @Value("${spring.application.name}")
    private String app;

    public FeignErrorDecoder() {

    }

    private final FeignErrorDecoder.RetryAfterDecoder retryAfterDecoder = new FeignErrorDecoder.RetryAfterDecoder();

    /**
     * 保留原有默认异常
     */
    public static class RetryAfterDecoder {

        static final DateFormat RFC822_FORMAT =
                new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", US);
        private final DateFormat rfc822Format;

        RetryAfterDecoder() {
            this(RFC822_FORMAT);
        }

        RetryAfterDecoder(DateFormat rfc822Format) {
            this.rfc822Format = checkNotNull(rfc822Format, "rfc822Format");
        }

        protected long currentTimeMillis() {
            return System.currentTimeMillis();
        }

        /**
         * returns a date that corresponds to the first time a request can be retried.
         *
         * @param retryAfter String in
         *                   <a href="https://tools.ietf.org/html/rfc2616#section-14.37" >Retry-After format</a>
         */
        public Date apply(String retryAfter) {
            if (retryAfter == null) {
                return null;
            }
            if (retryAfter.matches("^[0-9]+$")) {
                long deltaMillis = SECONDS.toMillis(Long.parseLong(retryAfter));
                return new Date(currentTimeMillis() + deltaMillis);
            }
            synchronized (rfc822Format) {
                try {
                    return rfc822Format.parse(retryAfter);
                } catch (ParseException ignored) {
                    return null;
                }
            }
        }
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        String body = null;
        try {
            body = Util.toString(response.body().asReader());
            JSONObject jo = JSON.parseObject(body);
            if(jo.containsKey("code") && jo.containsKey("msg")) {
                return new KmssServiceException(jo.getString("code"), jo.getString("msg"));
            }
        } catch (IOException ex) {
            return new KmssServiceException("errors.unkown", ex);
        }
        //非返回json body ，则按照默认errorDecoder 进行封装
        String msg = contantMsg(methodKey, response.status(), body);
        RuntimeException exception = new RuntimeException(msg);
        Date retryAfter = this.retryAfterDecoder.apply(firstOrNull(response.headers(), RETRY_AFTER));
        return (retryAfter != null ? new RetryableException(response.status(), msg, response.request().httpMethod(), exception, retryAfter) : exception);
    }

    private String contantMsg(String methodKey, int status, String msg) {
        String message = String.format("status %s reading %s", status, methodKey);
        if (msg != null) {
            message = message + "; content:\n" + msg;
        }
        return message;
    }

    @SuppressWarnings("unchecked")
    private <T> T firstOrNull(Map<String, Collection<T>> map, String key) {
        return map.containsKey(key) && !(map.get(key)).isEmpty() ? (T) ((Collection<?>) map.get(key)).iterator().next() : null;
    }
}
