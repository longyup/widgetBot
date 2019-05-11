package club.vasilis.coolq.http;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Vasilis
 * @date 2019/5/11 -10:58
 */

public class baseHttp {
    public static OkHttpClient client;
    static {
        initClient();
    }
    public static void initClient() {
        client = new OkHttpClient.Builder()
                .callTimeout(2000L, TimeUnit.MILLISECONDS)
                .readTimeout(2000L, TimeUnit.MILLISECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .build();
    }

    public static Response getresponse(String url, FormBody body) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request).execute();
    }
}
