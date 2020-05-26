package com.chenfu.adapter;

import com.chenfu.DefultSet;
import com.chenfu.chess.ChessBoard;
import com.chenfu.chess.Rules;
import com.chenfu.control.GameController;
import com.chenfu.view.GameView;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class BoardClickListener extends MouseAdapter {

    private GameView gameView;
    private ChessBoard chessBoard;
    private GameController controller;

    public BoardClickListener(GameView gameView, ChessBoard chessBoard, GameController controller) {
        this.gameView = gameView;
        this.chessBoard = chessBoard;
        this.controller = controller;
    }

    private int[] modelToViewConverter(int pos[]) {
        int sx = pos[1] * DefultSet.SX_COE + DefultSet.SX_OFFSET, sy = pos[0] * DefultSet.SY_COE + DefultSet.SY_OFFSET;
        return new int[]{sx, sy};
    }

    private int[] viewToModelConverter(int sPos[]) {
        int ADDITIONAL_SY_OFFSET = 25;
        int y = (sPos[0] - DefultSet.SX_OFFSET) / DefultSet.SX_COE, x = (sPos[1] - DefultSet.SY_OFFSET - ADDITIONAL_SY_OFFSET) / DefultSet.SY_COE;
        return new int[]{x, y};
    }

    public void movePieceFromModel(String pieceKey, int[] to) {
        JLabel pieceObject = gameView.pieceObjects.get(pieceKey);
        int[] sPos = modelToViewConverter(to);
        pieceObject.setLocation(sPos[0], sPos[1]);
        gameView.selectedPieceKey = null;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (gameView.selectedPieceKey != null) {
            int[] sPos = new int[]{e.getXOnScreen() - gameView.getX(), e.getYOnScreen() - gameView.getY()};
            int[] pos = viewToModelConverter(sPos);
            System.out.println(pos[0]+" "+pos[1]);
            int[] selectedPiecePos = chessBoard.pieces.get(gameView.selectedPieceKey).position;
            for (int[] each : Rules.getNextMove(gameView.selectedPieceKey, selectedPiecePos, chessBoard)) {
                if (Arrays.equals(each, pos)) {
                    controller.moveChess(gameView.selectedPieceKey, pos, chessBoard);
                    movePieceFromModel(gameView.selectedPieceKey, pos);
                    break;
                }
            }
        }
    }
}