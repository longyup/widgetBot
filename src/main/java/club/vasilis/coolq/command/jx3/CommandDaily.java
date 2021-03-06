package club.vasilis.coolq.command.jx3;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentEmoji;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import club.vasilis.coolq.jx3.config.J3Config;
import club.vasilis.coolq.jx3.dao.DailyCoolDao;
import club.vasilis.coolq.jx3.http.Daily;
import club.vasilis.coolq.util.TimeUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * 剑网三日常查询
 * 使用了剑网三导航站的数据
 * @author Vasilis
 * @date 2019/5/11 -20:46
 */

public class CommandDaily implements GroupCommand {
    @Override
    public String groupMessage(EventGroupMessage eventGroupMessage, GroupUser groupUser, Group group, String s, ArrayList<String> arrayList) {
        if (!J3Config.j3gorupList.contains(group.getId())){
            return "此群没有授权!";
        }
        int temp = 30;
        //先判断时间
        DailyCoolDao dao = new DailyCoolDao();
        Map<String, Object> map = null;
        try {
            map = dao.getNextEvent(group.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 先判断有没有这条数据
        if (map != null){
            // 如果时间没到直接返回
            long nextTime = Long.valueOf(String.valueOf(map.get("nextEvent"))) ;
            if ((System.currentTimeMillis() - nextTime)<1000*temp){
               return null;
            }else{
                try {
                    new DailyCoolDao().update(group.getId(),System.currentTimeMillis(),System.currentTimeMillis()+1000*temp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }else {
            try {
                new DailyCoolDao().create(group.getId(),System.currentTimeMillis(),System.currentTimeMillis()+1000*temp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        String daily = Daily.findDaily();


        String message = new MessageBuilder().add("今天是")
                .add(TimeUtils.getDayAndWeek())
                .newLine()
                .add(daily)
                .add(new ComponentEmoji(11088))
                .add("日常模块有30S冷却时间")
                .toString();

        eventGroupMessage.getHttpApi().sendGroupMsg(group.getId(), message);
        return null;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Daily", "日常", "日常查询");
    }
}
