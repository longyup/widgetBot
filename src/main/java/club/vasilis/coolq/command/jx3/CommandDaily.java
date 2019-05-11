package club.vasilis.coolq.command.jx3;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 剑网三日常查询
 * 使用了剑网三导航站的数据
 * @author Vasilis
 * @date 2019/5/11 -20:46
 */

public class CommandDaily implements GroupCommand {


    @Override
    public String groupMessage(EventGroupMessage eventGroupMessage, GroupUser groupUser, Group group, String s, ArrayList<String> arrayList) {
        MessageBuilder builder = new MessageBuilder();

        try {
            Document doc = Jsoup.connect("https://www.jx3dh.com/api/ii.php").get();
            String select = doc.select("table table td").first().toString();
            String day = select.substring(9, select.indexOf("<br>"));
            builder.add(day);
            System.out.println(day);
            select = select.substring(select.lastIndexOf("</h3>") + 5, select.lastIndexOf("</td>"));
            String[] split = select.split("<br>");
            for (String result : split) {
                result = result.replace("&lt;-- ", "").replace(" --&gt;", "");
                builder.newLine()
                        .add(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        eventGroupMessage.getHttpApi().sendGroupMsg(group.getId(), builder.toString());


        return null;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Daily", "日常", "日常查询");
    }

    public static void main(String[] args) {


    }
}
