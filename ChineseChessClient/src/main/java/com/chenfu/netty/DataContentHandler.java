package com.chenfu.netty;

import com.chenfu.components.InformationBoard;
import com.chenfu.pojo.*;
import com.chenfu.view.GameView;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import javax.swing.*;


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
        Player competitor = null;
        char c = '0';
        int action = dataContent.getAction();
        switch (action){
            case 2:
                ChatMsg chatMsg = (ChatMsg) dataContent.getObject();
                String competitorUsername = gameView.getCompetitor().getUsername();
                String msg = competitorUsername +"对你说:" + chatMsg.getMsg();
                InformationBoard.getInstance().addLog(msg);
                break;
            case 4:
                competitor= (Player)dataContent.getObject();
                String password = competitor.getPassword();
                if(password.equals("ASK")){
                    int i = JOptionPane.showConfirmDialog(gameView, "您是否同意对方的求和请求？", "系统信息", JOptionPane.YES_NO_OPTION);
                    if(i == JOptionPane.YES_OPTION){
                        Client instance = Client.getInstance();
                        Channel channel = instance.getChannel();
                        competitor = gameView.getCompetitor();
                        competitor.setPassword("AGREE");
                        dataContent.setObject(competitor);
                        channel.writeAndFlush(dataContent);
                        gameView.showWinner('p');
                    }
                }else if(password.equals("AGREE")){
                    gameView.showWinner('p');
                }
                gameView.status = GameStatusEnum.NETWORK_MODE.status;
                break;
            case 5:
                gameView.win();
                gameView.status = GameStatusEnum.NETWORK_MODE.status;
                break;
            case 6:
                competitor =(Player) dataContent.getObject();
                gameView.setCompetitor(competitor);
                gameView.status = GameStatusEnum.NETWORK_START.status;
                gameView.getInformationBoard().addLog("匹配成功！");
                c = competitor.getPassword().charAt(0);
                c = (c == 'r') ? 'b' : 'r';
                gameView.newGame(c);
                gameView.showPlayer(c);
                gameView.getJudgeTimer().start();
                gameView.repaint();
                break;
            case 7:
                PieceMsg pieceMsg =(PieceMsg) dataContent.getObject();
                System.out.println(pieceMsg);
                gameView.movePieceFromModel(pieceMsg.getKey(),pieceMsg.getDesPos(),false);
                gameView.getChessBoard().wait = false;
                gameView.getStepTimer().reStart();
                break;
        }
    }
}
