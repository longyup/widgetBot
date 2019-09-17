package club.vasilis.coolq.jx3.config;

import cc.moecraft.yaml.HyConfig;
import club.vasilis.coolq.Main;
import club.vasilis.coolq.jx3.task.ServerTasks;
import javafx.util.Pair;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vasilis
 * @date 2019/7/25 -10:50
 */

public class J3Config {
    public static List<Long> j3gorupList;
    private static HyConfig config = null;
    public static Map<String, Object> broadcastList = new HashMap<>();

    public static void loadConfig() {
        config = new HyConfig(new File("config/plugins.yml"));

        if (config != null) {
            // 激活的群

            j3gorupList = config.getLongList("j3.j3gorupList");
            Main.bot.getLogger().log(String.format("%s:%s;", "激活的群", j3gorupList));

            broadcastList.clear();
            ConfigurationSection configurationSection = config.getConfigurationSection("j3.startServerBroadcast");
            if (configurationSection != null) {
                broadcastList = configurationSection.getValues(false);
            }

        }
        Main.bot.getLogger().log(String.format("%s:%s", "开服播报", broadcastList));
        startBC();

    }

    private static void startBC() {
        for (Map.Entry<String, Object> m : broadcastList.entrySet()) {
            Pair<Long, String> pair = new Pair<>(Long.valueOf(m.getKey()), String.valueOf(m.getValue()));
            ServerTasks.taskTimrFactory(pair);
        }
    }

    /**
     * 群授权
     *
     * @param groupId
     */
    public static void writegroupList(long groupId) {
        if (config != null) {
            j3gorupList.add(groupId);
            config.set("j3.j3gorupList", j3gorupList);
            config.setEnableWrite(true);
            config.save();
        }
    }

    /**
     * 开服播报设置
     *
     * @param groupId
     * @param serverName
     */
    public static void writegroupbc(long groupId, String serverName) {
        broadcastList.put(String.valueOf(groupId), serverName);
        config.createSection("j3.startServerBroadcast", broadcastList);
        config.setEnableWrite(true);
        config.save();
    }

    public static void main(String[] args) {
        loadConfig();
        writegroupbc(799453724L, "绝代天骄");
    }
}
