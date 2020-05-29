package com.chenfu.netty;

import com.chenfu.components.InformationBoard;
import com.chenfu.pojo.*;
import com.chenfu.view.GameView;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class DataContentHandler extends SimpleChannelInboundHandler<DataContent> {

    private GameView gameView;

    public DataContentHandler(GameView gameView) {
        this.gameView = gameView;
    }


/*    LOGIN(1, "用户登录"),
    CHATMSG(2,"聊天消息"),
    WITHDRAW(3,"悔棋"),
    ASKDRAW(4,"求和"),
    GIVEUP(5,"认输"),
    NEWGAME(6,"新游戏"),
    PIECELPOS(7,"棋子信息");*/


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DataContent dataContent) throws Exception {
        if(dataContent ==null)
            return;
        int action = dataContent.getAction();
        switch (action){
            case 2:
                ChatMsg chatMsg = (ChatMsg) dataContent.getObject();
                String competitorUsername = gameView.getCompetitor().getUsername();
                String msg = competitorUsername +"对你说:" + chatMsg.getMsg();
                InformationBoard.getInstance().addLog(msg);
                break;
            case 6:
                Player competitor =(Player) dataContent.getObject();
                gameView.setCompetitor(competitor);
                gameView.status = GameStatusEnum.NETWORK_START.status;
                gameView.getInformationBoard().addLog("匹配成功！");
                char c = competitor.getPassword().charAt(0);
                gameView.newGame(c);
                gameView.showPlayer(c);
                gameView.repaint();
                break;
            case 7:
                PieceMsg pieceMsg =(PieceMsg) dataContent.getObject();
                System.out.println(pieceMsg);
                gameView.movePieceFromModel(pieceMsg.getKey(),pieceMsg.getDesPos(),false);
                gameView.getChessBoard().wait = false;
                break;
        }
    }
}
