package com.chenfu.alogrithm;

import com.chenfu.DefaultSet;
import com.chenfu.pojo.ChessBoard;
import com.chenfu.control.GameController;
import com.chenfu.view.GameView;

public class AImodeThread implements Runnable{


    private GameView gameView;
    private ChessBoard chessBoard;
    private GameController gameController;

    public AImodeThread(GameView gameView, ChessBoard chessBoard, GameController gameController) {
        this.gameView = gameView;
        this.chessBoard = chessBoard;
        this.gameController = gameController;
    }


    @Override
    public void run() {
        while (true){
            gameView.showPlayer('r');
            while (chessBoard.player == 'r') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (gameController.hasWin(chessBoard) == 'r'){
                gameView.showWinner('r');
                break;
            }
            gameView.showPlayer('b');
            int[]pos= gameController.responseMoveChess(chessBoard, gameView);
            gameView.saveBoard();
            chessBoard.player = (chessBoard.player == 'r') ? 'b' : 'r';
            gameView.getKuangLabel().setLocation(DefaultSet.SX_OFFSET + pos[1] * DefaultSet.SX_COE, DefaultSet.SY_OFFSET + pos[0] * DefaultSet.SY_COE);
            gameView.getStepTimer().reStart();
            if (gameController.hasWin(chessBoard) == 'b'){
                gameView.showWinner('b');
                break;
            }
        }
    }
}
