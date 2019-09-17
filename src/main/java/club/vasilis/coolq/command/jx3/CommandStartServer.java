package club.vasilis.coolq.command.jx3;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import club.vasilis.coolq.jx3.config.J3Config;
import club.vasilis.coolq.jx3.http.StartServer;

import java.util.ArrayList;

/**
 * @author Vasilis
 * @date 2019/6/14 -8:46
 */

public class CommandStartServer implements GroupCommand {
    @Override
    public String groupMessage(EventGroupMessage eventGroupMessage, GroupUser groupUser, Group group, String s, ArrayList<String> arrayList) {
        if (!J3Config.j3gorupList.contains(group.getId())){
            return "此群没有授权!";
        }
        if (arrayList.size() != 1 || "".equals(arrayList.get(0))){
            eventGroupMessage.getHttpApi().sendGroupMsg(group.getId(),"使用方式:\n开服+空格+区服\n例：开服 绝代");
            return  null;
        }
        String startTime = StartServer.findStartTime(arrayList.get(0));
        return startTime;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("开服");
    }
}
