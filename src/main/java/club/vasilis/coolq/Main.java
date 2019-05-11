package club.vasilis.coolq;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import cc.moecraft.icq.command.interfaces.IcqCommand;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.logger.environments.ColorSupportLevel;
import club.vasilis.coolq.command.jx3.CommandCmdList;
import club.vasilis.coolq.command.jx3.CommandDaily;
import club.vasilis.coolq.command.jx3.MoneySearchCommand;
import club.vasilis.coolq.command.jx3.examCommand;
import club.vasilis.coolq.listener.ExceptionListener;
import club.vasilis.coolq.listener.RequestListener;
import club.vasilis.coolq.listener.SimpleTextLoggingListener;
import club.vasilis.coolq.listener.TestFilter;

/**
 * @author Vasilis
 * @date 2019/5/10 -19:06
 */

public class Main {
    public static PicqBotX bot = null;

    /**
     * 要注册的指令
     */
    private static IcqCommand[] commands = new IcqCommand[]{
            new MoneySearchCommand(),
            new examCommand(),
            new CommandCmdList(),
            new CommandDaily()
    };
    /**
     * 要注册的监听器
     */
    private static IcqListener[] listeners = new IcqListener[]{
            new TestFilter(),
            new RequestListener(),
            new SimpleTextLoggingListener(),
            new ExceptionListener()
    };

    public static void main(String[] args) {

        // 创建机器人配置 ( 传入Picq端口 )
        PicqConfig config = new PicqConfig(31092)
                .setDebug(true)
                .setColorSupportLevel(ColorSupportLevel.OS_DEPENDENT);
        // 创建机器人对象 ( 传入机器人配置对象 )
        bot = new PicqBotX(config);

        // 添加一个机器人账户 ( 传入名字, 酷Q URL, 酷Q端口 )
        bot.addAccount("小P", "127.0.0.1", 31091);

        // 设置异步
        bot.getConfig().setUseAsyncCommands(true);

        // 注册事件监听器, 可以注册多个监听器
        bot.getEventManager().registerListeners(listeners);

        // 在没有Debug的时候加上这个消息日志监听器
        if (!bot.getConfig().isDebug())
            bot.getEventManager().registerListener(new SimpleTextLoggingListener());
        bot.enableCommandManager("bot -", "!", "/", "~", "！", "我以令咒命之，", "我以令咒命之, ", "test -");
        // 注册指令
        bot.getCommandManager().registerCommands(commands);

        // Debug输出所有已注册的指令
        bot.getLogger().debug(bot.getCommandManager().getCommands().toString());

        // 启动机器人, 不会占用主线程
        bot.startBot();
    }
}
