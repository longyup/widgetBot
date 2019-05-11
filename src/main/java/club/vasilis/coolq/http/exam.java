package club.vasilis.coolq.http;

import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 *
 * @author Vasilis
 * @date 2019/5/11 -10:52
 */

public class exam extends baseHttp{

    public exam() {
        super();
    }

    /**
     * 使用茗伊题库
     * url：https://jx3.derzh.com/exam
     * methon get
     * params m=1&q=问题&csrf=
     * @param ques
     * @return
     */
    private String findbyq(String ques){

        String url = "https://jx3.derzh.com/exam/?m=1&q="+ques+"&csrf=";
        String json = "";
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() !=null){
                json = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(json);


        //解析json


        return null;

    }

    public static void main(String[] args) {
        new exam().findbyq("青岩");
    }
}
