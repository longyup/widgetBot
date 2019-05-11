package club.vasilis.coolq.command.jx3;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;

import java.util.ArrayList;

/**
 * @author Vasilis
 * @date 2019/5/11 -12:39
 */

public class CommandCmdList implements GroupCommand {



    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("cmdlist", "help","功能");
    }

    @Override
    public String groupMessage(EventGroupMessage eventGroupMessage, GroupUser groupUser, Group group, String s, ArrayList<String> arrayList) {
        MessageBuilder builder = new MessageBuilder().add("这个机器人只有这些指令嗯w:").newLine();
        for (String cmdName : eventGroupMessage.getBot().getCommandManager().getCommandNameList())
        {
            builder.add(cmdName).add(", ");
        }
        return builder.toString();
    }
}
