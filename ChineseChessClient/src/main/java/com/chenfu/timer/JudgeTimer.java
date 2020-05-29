package com.chenfu.timer;
import com.chenfu.control.GameController;
import com.chenfu.pojo.ChessBoard;
import com.chenfu.view.GameView;

import java.util.Timer;
import java.util.TimerTask;

public class JudgeTimer {

    private GameView gameView;
    private ChessBoard chessBoard;
    private GameController gameController;


    public JudgeTimer(GameView gameView, ChessBoard chessBoard, GameController gameController) {
        this.gameView = gameView;
        this.chessBoard = chessBoard;
        this.gameController = gameController;
    }

    public void start() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                char c = gameView.getCompetitor().getPassword().charAt(0);
                if(chessBoard.inverse){
                    if(chessBoard.wait){
                        gameView.showPlayer(c);
                    }else {
                        gameView.showPlayer(c=='r'?'b':'r');
                    }
                }else {
                    if(!chessBoard.wait){
                        gameView.showPlayer(c);
                    }else {
                        gameView.showPlayer(c=='r'?'b':'r');
                    }
                }
                boolean isover = gameView.showWinner(gameController.hasWin(chessBoard));
                if(isover){
                    cancel();
                }
            }
        },0,1000);
    }
}
