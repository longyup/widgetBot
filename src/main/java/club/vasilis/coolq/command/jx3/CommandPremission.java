package club.vasilis.coolq.command.jx3;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.PrivateCommand;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.user.User;
import club.vasilis.coolq.jx3.config.J3Config;

import java.util.ArrayList;

/**
 * @author Vasilis
 * @date 2019/7/28 -20:12
 */

public class CommandPremission implements PrivateCommand {
    @Override
    public String privateMessage(EventPrivateMessage eventPrivateMessage, User user, String s, ArrayList<String> arrayList) {
        String param =arrayList.get(0);
        if (arrayList.size() != 1 || "".equals(param)) {
            return "格式错误";
        }
        if (user.getId() != 799453724L) {
            return "没有权限";
        }
        if (J3Config.j3gorupList.contains(Long.valueOf(param))){
            return "该群已授权";
        }
        J3Config.writegroupList(Long.parseLong(arrayList.get(0)));
        return "授权成功";
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("授权");
    }
}
