package com.chenfu.adapter;

import com.chenfu.DefaultSet;
import com.chenfu.pojo.ChessBoard;
import com.chenfu.alogrithm.Rules;
import com.chenfu.control.GameController;
import com.chenfu.pojo.GameStatusEnum;
import com.chenfu.utils.AudioPlayer;
import com.chenfu.view.GameView;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Objects;

public class BoardClickListener extends MouseAdapter {

    private GameView gameView;
    private ChessBoard chessBoard;
    private GameController gameController;

    public BoardClickListener(GameView gameView, ChessBoard chessBoard, GameController gameController) {
        this.gameView = gameView;
        this.chessBoard = chessBoard;
        this.gameController = gameController;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(chessBoard.wait){
            return;
        }
        if(gameView.status == GameStatusEnum.AI_START.status){
            String pieceKey = gameView.selectedPieceKey;
            if (pieceKey != null) {
                int[] sPos = new int[]{e.getXOnScreen() - gameView.getX(), e.getYOnScreen() - gameView.getY()};
                int[] pos = gameView.viewToModelConverter(sPos);
                int[] selectedPiecePos = chessBoard.stringChessPieceMap.get(pieceKey).position;
                for (int[] each : Objects.requireNonNull(Rules.getNextMove(pieceKey, selectedPiecePos, chessBoard))) {
                    if (Arrays.equals(each, pos)) {
                        AudioPlayer audioPlayer = new AudioPlayer("go.wav",false);
                        audioPlayer.play();
                        gameView.setSquareLocation(pos);
                        gameView.getStepTimer().reStart();
                        gameView.movePieceFromModel(pieceKey, pos,false);
                        chessBoard.player = (chessBoard.player == 'r') ? 'b' : 'r';
                        System.out.println("ChessBoard{ PieceKey:" + pieceKey + " src:" + Arrays.toString(selectedPiecePos) + " des:" + Arrays.toString(pos)+"}");
                        break;
                    }
                }
            }
        }else if(gameView.status == GameStatusEnum.NETWORK_START.status){
            String pieceKey = gameView.selectedPieceKey;
            if (pieceKey != null) {
                int[] sPos = new int[]{e.getXOnScreen() - gameView.getX(), e.getYOnScreen() - gameView.getY()};
                int[] pos = gameView.viewToModelConverter(sPos);
                int[] selectedPiecePos = chessBoard.stringChessPieceMap.get(pieceKey).position;
                for (int[] each : Objects.requireNonNull(Rules.getNextMove(pieceKey, selectedPiecePos, chessBoard))) {
                    if (Arrays.equals(each, pos)) {
                        AudioPlayer audioPlayer = new AudioPlayer("go.wav",false);
                        audioPlayer.play();
                        gameView.setSquareLocation(pos);
                        gameView.getStepTimer().reStart();
                        gameView.movePieceFromModel(pieceKey, pos,true);
                        System.out.println("ChessBoard{ PieceKey:" + pieceKey + " src:" + Arrays.toString(selectedPiecePos) + " des:" + Arrays.toString(pos)+"}");
                        break;
                    }
                }
            }
        } else {
            gameView.getInformationBoard().AddLog("BoardClick:请选择模式！");
        }

    }
}
