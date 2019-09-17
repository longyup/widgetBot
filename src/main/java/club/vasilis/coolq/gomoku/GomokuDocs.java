package club.vasilis.coolq.gomoku;

/**
 * @author Vasilis
 * @date 2019/9/17 -11:24
 */

public class GomokuDocs {

    @Override
    public String toString() {

       StringBuilder  sb = new StringBuilder();
        sb.append("Gomoku/五子棋指令:\n");
        sb.append("    /gj 加入五子棋\n");
        sb.append("    /ge 退出五子棋");
        sb.append("    x坐标y坐标(先后顺序不固定,0a和a0效果等同) 落子\n");
        sb.append("    /gf 投降\n");
//        sb.append("    /ve 投票结束游戏\n");
//        sb.append("    /gc 查看自己的gomoku credit");
/*        sb.append("    /gsf <群号> 强制结束该群的游戏 (仅限管理员)");
        sb.append("    /gcset <qq号> <分数> 设置该玩家的Gomoku Credit (仅限管理员)");
        sb.append("    /op <qq号> 设置该用户为管理员 (仅限管理员)");
        sb.Append("    /unop <qq号> 取消该用户的管理员资格 (仅限机器人所有者)");*/
        return sb.toString();
    }
}
