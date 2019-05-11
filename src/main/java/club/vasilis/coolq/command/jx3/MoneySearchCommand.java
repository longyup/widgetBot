package club.vasilis.coolq.command.jx3;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentAt;
import cc.moecraft.icq.sender.message.components.ComponentEmoji;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import club.vasilis.coolq.http.MoneySearch;

import java.util.ArrayList;

/**
 * @author Vasilis
 * @date 2019/5/10 -20:30
 */

public class MoneySearchCommand implements GroupCommand {
    @Override
    public String groupMessage(EventGroupMessage eventGroupMessage, GroupUser groupUser, Group group, String s, ArrayList<String> arrayList) {

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
                    .add("冷却时间2mins")
                    .toString();

            eventGroupMessage.getHttpApi().sendGroupMsg(group.getId(),message);
        }




       /* //double byserver = new MoneySearch().findByserver(group,arrayList.get(0));
        if (byserver == 0) {
            return "对不起，查询失败";
        }
        StringBuffer buffer = new StringBuffer().append("金价  ")
                .append(arrayList.get(0))
                .append(byserver)
                .append("数据来自[DD373] 仅供参考");
        return buffer.toString();*/
       return null;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("moneyserach", "金价", "金价查询");
    }
}
