package com.chenfu.adapter;

import com.chenfu.DefaultSet;
import com.chenfu.chess.ChessBoard;
import com.chenfu.chess.Rules;
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

    private int[] modelToViewConverter(int[] pos) {
        int sx = pos[1] * DefaultSet.SX_COE + DefaultSet.SX_OFFSET, sy = pos[0] * DefaultSet.SY_COE + DefaultSet.SY_OFFSET;
        return new int[]{sx, sy};
    }

    private int[] viewToModelConverter(int[] sPos) {
        int ADDITIONAL_SY_OFFSET = 25;
        int y = (sPos[0] - DefaultSet.SX_OFFSET) / DefaultSet.SX_COE, x = (sPos[1] - DefaultSet.SY_OFFSET - ADDITIONAL_SY_OFFSET) / DefaultSet.SY_COE;
        return new int[]{x, y};
    }

    public void movePieceFromModel(String pieceKey, int[] to) {
        JLabel pieceObject = gameView.stringJLabelMap.get(pieceKey);
        int[] sPos = modelToViewConverter(to);
        pieceObject.setLocation(sPos[0], sPos[1]);
        gameView.selectedPieceKey = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(gameView.status == GameStatusEnum.AI_START.status){
            if (gameView.selectedPieceKey != null) {
                int[] sPos = new int[]{e.getXOnScreen() - gameView.getX(), e.getYOnScreen() - gameView.getY()};
                int[] pos = viewToModelConverter(sPos);
                System.out.println(pos[0]+" "+pos[1]);
                int[] selectedPiecePos = chessBoard.stringChessPieceMap.get(gameView.selectedPieceKey).position;
                for (int[] each : Objects.requireNonNull(Rules.getNextMove(gameView.selectedPieceKey, selectedPiecePos, chessBoard))) {
                    if (Arrays.equals(each, pos)) {
                        AudioPlayer audioPlayer = new AudioPlayer("go.wav",false);
                        audioPlayer.play();
                        gameController.moveChess(gameView.selectedPieceKey, pos, chessBoard);
                        gameView.setSquareLocation(pos);
                        gameView.getStepTimer().reStart();
                        movePieceFromModel(gameView.selectedPieceKey, pos);
                        break;
                    }
                }
            }
        }else {
            gameView.getInformationBoard().AddLog("请选择模式！");
        }

    }
}
