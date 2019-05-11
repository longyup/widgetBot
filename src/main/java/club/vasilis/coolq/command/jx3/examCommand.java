package club.vasilis.coolq.command.jx3;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;

/**
 * @author Vasilis
 * @date 2019/5/11 -10:50
 */

public class examCommand implements GroupCommand {

    @Override
    public String groupMessage(EventGroupMessage eventGroupMessage, GroupUser groupUser, Group group, String s, ArrayList<String> arrayList) {
        if (arrayList.size() !=1){
            eventGroupMessage.getHttpApi().sendGroupMsg(group.getId(),"科举使用方式:\n科举+空格+问题关键字\n科举 青岩");
            return  null;
        }
        String question = arrayList.get(0);

        return null;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("exam", "科举", "科举查询");
    }
}
