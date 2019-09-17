package club.vasilis.coolq.jx3.http;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author Vasilis
 * @date 2019/6/14 -8:48
 */

public class StartServer extends baseHttp {
    public static String findStartTime(String serverName) {
        String url = "https://www.jx3tong.com/?m=api&c=user&a=server_list&parent=8";
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

        JSONObject object = new JSONObject(json);
        JSONArray array = object.getJSONArray("data");
        for (int i = 0; i < array.size(); i++) {
            object = array.getJSONObject(i);
            if (object.getStr("name").contains(serverName)){
                if ("0".equals(object.getStr("state"))){
                    return "未开服";
                }else if ("1".equals(object.getStr("state"))){
                    return "已开服，\n上次开服时间为"+object.getStr("last_open_time");
                }
            }
        }
        return "未查到服务器";

    }
}
