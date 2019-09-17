package club.vasilis.coolq.jx3.http;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vasilis
 * @date 2019/5/11 -20:53
 */

public class Daily extends baseHttp {
    private static List<String> ids = new ArrayList<>();

    static {
        ids.add("13");
        ids.add("14");
        ids.add("15");
        ids.add("16");
        ids.add("17");
        ids.add("18");
        ids.add("19");
        ids.add("21");
        ids.add("22");
        ids.add("23");
        ids.add("24");
        ids.add("25");
        ids.add("26");
        ids.add("31");
        ids.add("40");
        ids.add("41");
        ids.add("93");
        ids.add("59");
    }

    public static String findDaily() {
        String url = "https://www.jx3tong.com/?m=api&c=daily&a=daily_list";
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

        //解析json
        String reportMsg = "";
        JSONObject object = new JSONObject(json);

        String date = object.getStr("update_time");

        JSONArray array = object.getJSONArray("activity_data");
        for (int i = 0; i < array.size(); i++) {
            object = array.getJSONObject(i);
            JSONArray activityList = object.getJSONArray("activity_list");
            for (int j = 0; j < activityList.size(); j++) {
                if (ids.contains(activityList.getJSONObject(j).getStr("id"))) {
                    reportMsg += activityList.getJSONObject(j).getStr("title").trim() + "\n";
                }
            }
        }
        return reportMsg;

    }

    public static String findWeek(int id) {
        String url = "https://www.jx3tong.com/?m=api&c=daily&a=daily_detail&daily_id=" + id;
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

        //解析json
        String reportMsg = "";
        JSONObject object = new JSONObject(json);
        object = object.getJSONObject("detail");
        reportMsg += object.getStr("title") + "\n";
        String result = object.getStr("join_method");
        String[] s = result.split(" ");
        for (String temp : s) {
            reportMsg += temp;
        }

        return reportMsg;

    }

}
