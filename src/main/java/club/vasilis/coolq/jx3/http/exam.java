package club.vasilis.coolq.jx3.http;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author Vasilis
 * @date 2019/5/11 -10:52
 */

public class exam extends baseHttp {

  /*  public exam() {
        super();
    }*/

    /**
     * 使用茗伊题库
     * url：https://jx3.derzh.com/exam
     * methon get
     * params m=1&q=问题&csrf=
     *
     * @param q
     * @return
     */
    public static String findbyq(String q) {

        String url = "https://jx3.derzh.com/exam/?m=1&q=" + q + "&csrf=";
        String json = "";
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                json = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(json);


        //解析json
        String reportMsg = "";
        JSONObject object = new JSONObject(json);
        JSONArray result = object.getJSONArray("result");
        for (int i = 0; i < result.size(); i++) {
            //{"ques":"单选题：《青岩诗钞·卷三》中的诗出自何人之手？","answ":"王勃"}
            object = result.getJSONObject(i);
            String ques = object.getStr("ques").replace("单选题：", "").replace("單選題：", "").replace("单选题:", "").replace("單選題:", "");
            String answ = object.getStr("answ");
            reportMsg += "问题:" + ques + "\n答案:" + answ + "\n";
        }
        return reportMsg;

    }

  /*  public static void main(String[] args) {
        new exam().findbyq("青岩");
    }*/
}
