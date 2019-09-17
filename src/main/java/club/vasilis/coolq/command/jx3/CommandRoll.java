package club.vasilis.coolq.command.jx3;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Vasilis
 * @date 2019/9/10 -9:50
 */

public class CommandRoll implements GroupCommand {
    @Override
    public String groupMessage(EventGroupMessage eventGroupMessage, GroupUser groupUser, Group group, String s, ArrayList<String> arrayList) {
        Random random = new Random();
        return String.format("Roll了%s点",random.nextInt(100)+1);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("roll");
    }
}
