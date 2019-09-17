package club.vasilis.coolq.command.jx3;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentAt;
import cc.moecraft.icq.sender.message.components.ComponentEmoji;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import club.vasilis.coolq.jx3.config.J3Config;
import club.vasilis.coolq.jx3.http.MoneySearch;

import java.util.ArrayList;

/**
 * @author Vasilis
 * @date 2019/5/10 -20:30
 */

public class MoneySearchCommand implements GroupCommand {
    @Override
    public String groupMessage(EventGroupMessage eventGroupMessage, GroupUser groupUser, Group group, String s, ArrayList<String> arrayList) {
        if (!J3Config.j3gorupList.contains(group.getId())){
            return "此群没有授权!";
        }
        Double[] doubles = MoneySearch.findByserver(group.getId(), arrayList.get(0));
        if (doubles != null){
            String message = new MessageBuilder().add(new ComponentAt(groupUser.getId()))
                    .newLine()
                    .add("金价")
                    .add(arrayList.get(0))
                    .newLine()
                    .add("最高比例   ")
                    .add(doubles[0])
                    .add("平均比例   ")
                    .add(doubles[1])
                    .newLine()
                    .add(new ComponentEmoji(11088))
                    .add("数据来源于DD373 仅供参考")
                    .add(new ComponentEmoji(11088))
                    .newLine()
                    .add("冷却时间2mins")
                    .toString();

            eventGroupMessage.getHttpApi().sendGroupMsg(group.getId(),message);
        }

       return null;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("moneyserach", "金价", "金价查询");
    }
}
