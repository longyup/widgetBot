package club.vasilis.coolq.gomoku;

import java.util.HashMap;
import java.util.Map;

/**
 * 用Map保存所有的对战
 * @author Vasilis
 * @date 2019/9/17 -13:28
 */

public class GomokuFactory {
    private static Map<Long,PlayGround> games = new HashMap<>();

    public static PlayGround getOrCreatePlayGround(long group){
        if (!games.containsKey(group)){
           games.put(group,new PlayGround(group));
        }
        return games.get(group);
    }

    public static void removePlayGround(long group){
        games.remove(group);
    }
}
