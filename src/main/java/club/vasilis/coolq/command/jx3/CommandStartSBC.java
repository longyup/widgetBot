package club.vasilis.coolq.command.jx3;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import club.vasilis.coolq.jx3.config.J3Config;
import club.vasilis.coolq.jx3.config.ServerList;
import club.vasilis.coolq.jx3.task.ServerTasks;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * @author Vasilis
 * @date 2019/7/28 -22:43
 */

public class CommandStartSBC implements GroupCommand {
    @Override
    public String groupMessage(EventGroupMessage eventGroupMessage, GroupUser groupUser, Group group, String s, ArrayList<String> arrayList) {
        String param = arrayList.get(0);
        if (arrayList.size() != 1 || "".equals(param)) {
            return "格式错误";
        }

        if (!J3Config.j3gorupList.contains(group.getId())) {
            return "此群没有授权!";
        }

        if (!(groupUser.getId() == 799453724L || groupUser.isAdmin())) {
            return "没有权限";
        }
        int num = 0;
        for (String serverName : ServerList.serverlist.keySet()) {
            if (serverName.contains("绝代")) {
               num++;
               break;
            }
        }
        if (num == 0){
            return "对不起，没有找到服务器 (づ╥﹏╥)づ";
        }
        String temp = (String) J3Config.broadcastList.get(String.valueOf(group.getId()));
        if (temp.contains(param) || param.contains(temp)) {
            return "已绑定该服务器";
        }
        J3Config.writegroupbc(group.getId(), param);
        Pair<Long, String> pair = new Pair<>(group.getId(), param);

        ServerTasks.taskTimrFactory(pair);
        return "绑定成功";
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("开服播报绑定");
    }

    public static void main(String[] args) {

    }
}
