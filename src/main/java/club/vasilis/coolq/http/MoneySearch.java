package club.vasilis.coolq.http;

import cc.moecraft.icq.sender.IcqHttpApi;
import club.vasilis.coolq.Main;
import club.vasilis.coolq.domain.Dd373;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.FormBody;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于dd373的金价查询
 * 传入的服从 area_server表找对应的大区
 *
 * @author Vasilis
 * @date 2019/5/10 -20:33
 */

public class MoneySearch extends baseHttp {
    private static Map<String, String> areaMap = new HashMap<>();
    private static Map<String, String> serverMap = new HashMap<>();
    private static Map<String, List<String>> area_server = new HashMap<>();
    private static long startTime;
    private static long endTime;

    public MoneySearch() {
        super();
        init();
    }

    /**
     * 屎山
     */
    private void init() {

        getAllarea();
        getAllServer();
    }

    /**
     * 获取所有的区
     */
    private void getAllarea() {
        String json = "";
        String url = "http://api.dd373.com/Game/GetGameOther";
        FormBody body = new FormBody.Builder()
                .add("GameId", "c0449518-ab9c-4e5f-b715-d11418cefa78")
                .add("parentId", "c0449518-ab9c-4e5f-b715-d11418cefa78")
                .add("lastLevelType", "1")
                .build();
        try {
            Response response = getresponse(url, body);
            if (response.isSuccessful() && response.body() != null) {
                json = response.body().string();
            } else {
                callAdmin();
            }
        } catch (IOException e) {
            e.printStackTrace();
            callAdmin();
        }
        //手动解析json，放入map
        JSONObject object = new JSONObject(json);
        JSONArray array = object.getJSONArray("GameOther");
        for (int i = 0; i < array.size(); i++) {
            //{"ID":"02949e13-a594-4193-810e-c3e3faa583aa",
            // "GameName":"电信一区","GameIdentify":"km917s","GameNameSpell":"电信一区","IsLast":"False"}
            object = array.getJSONObject(i);
            String key = object.getStr("GameName");
            String value = object.getStr("ID");
            areaMap.put(key, value);
        }

    }

    /**
     * 获取大区下的服务器
     */
    private void getAllServer() {
        startTime = System.currentTimeMillis();
        for (String key : areaMap.keySet()) {
            //存放同大区下所有服务器
            List<String> server = new ArrayList<>();

            String json = "";
            String url = "http://api.dd373.com/Game/GetGameOther";
            FormBody body = new FormBody.Builder()
                    .add("GameId", "c0449518-ab9c-4e5f-b715-d11418cefa78")
                    .add("parentId", areaMap.get(key))
                    .add("lastLevelType", "2")
                    .build();
            try {
                Response response = getresponse(url, body);
                if (response.isSuccessful() && response.body() != null) {
                    json = response.body().string();
                } else {
                    callAdmin();
                }
            } catch (IOException e) {
                callAdmin();
                e.printStackTrace();
            }

            //手动解析json，放入map
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("GameOther");
            for (int i = 0; i < array.size(); i++) {
                //{"ID":"e3fca501-1cd0-440f-ad40-10809b35ee84","GameName":"潮生碧海",
                // "GameIdentify":"74qg2v","GameNameSpell":"rylk日月凌空riyuelingkong","IsLast":"True"}
                object = array.getJSONObject(i);
                String serverName = object.getStr("GameNameSpell");
                String value = object.getStr("ID");
                serverMap.put(serverName, value);
                server.add(serverName);
            }
            area_server.put(key, server);
        }
        IcqHttpApi icqHttpApi = Main.bot.getAccountManager().getNonAccountSpecifiedApi();
        icqHttpApi.sendPrivateMsg(799453724L, "成功找到所有服");

    }

    public Double[] findByserver(long groupId, String temp) {
        //先找到对应的大区
        String areaName = "";
        outterLoop:
        for (String key : area_server.keySet()) {
            for (String serverName : area_server.get(key)) {
                if (serverName.contains(temp)) {
                    areaName = key;
                    break outterLoop;
                }
            }
        }
        if ("".equals(areaName)) {
            return null;
        }

        // 获得大区的id
        String areaId = "";
        for (String key : areaMap.keySet()) {
            if (key.contains(areaName)) {
                areaId = areaMap.get(key);
                break;
            }
        }
        // 获得服务器的id
        String serverId = "";
        for (String key : serverMap.keySet()) {
            if (key.contains(temp)) {
                serverId = serverMap.get(key);
                break;
            }
        }


        String json = "";
        String url = "http://api.dd373.com/Shop/GetShopList";

        FormBody body = new FormBody.Builder()
                .add("GameId", "c0449518-ab9c-4e5f-b715-d11418cefa78")
                .add("GameOtherId", new StringBuffer().append(areaId).append("_").append(serverId).toString())
                .add("GameShopTypeId", "f9565bc7-6e9f-4b96-8dc9-3122537760fa")
                .build();


        try {
            Response response = getresponse(url, body);
            if (response.isSuccessful() && response.body() != null) {
                json = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type type = new TypeToken<List<Dd373>>() {
        }.getType();
        List<Dd373> list = new Gson().fromJson(json, type);

        // 剔除担保的
        for (int i = 0; i < list.size(); i++) {
            Dd373 dd373 = list.get(i);
            if ("担保".equals(dd373.getTrade())) {
                list.remove(dd373);
            }
        }
        Double[] doubles = new Double[2];
        double coumt = 0;
        int size = list.size() < 3 ? list.size() : 3;
        for (int i = 0; i < size; i++) {
            Dd373 dd373 = list.get(i);
            // 最高价格
            if (i == 0) {
                double max = dd373.getAmount() / dd373.getPrice();
                BigDecimal b = new BigDecimal(max);
                max = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                doubles[0] = max;
            }
            coumt += dd373.getAmount() / dd373.getPrice();
        }
        BigDecimal b = new BigDecimal(coumt / size);
        doubles[1] = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        endTime = System.currentTimeMillis();    //获取结束时间
        return doubles;

    }

    private void callAdmin() {
        IcqHttpApi icqHttpApi = Main.bot.getAccountManager().getNonAccountSpecifiedApi();
        icqHttpApi.sendPrivateMsg(799453724L, "网络请求异常");
    }

    public static void main(String[] args) {
        Double[] doubles = new MoneySearch().findByserver(123456L, "绝代天骄");
        for (int i = 0; i < doubles.length; i++) {
            System.out.println(doubles[i]);
        }
        System.out.println();
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
    }

}
