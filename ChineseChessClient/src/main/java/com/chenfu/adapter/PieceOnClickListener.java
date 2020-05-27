package com.chenfu.adapter;

import com.chenfu.chess.ChessBoard;
import com.chenfu.chess.Rules;
import com.chenfu.control.GameController;
import com.chenfu.view.GameView;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Objects;

public class PieceOnClickListener extends MouseAdapter {

    private GameView gameView;
    private GameController gameController;
    private ChessBoard chessBoard;
    private JLayeredPane jLayeredPane;
    private String key;

    public PieceOnClickListener(GameView gameView, GameController gameController, ChessBoard chessBoard, JLayeredPane jLayeredPane, String key) {
        this.gameView = gameView;
        this.gameController = gameController;
        this.chessBoard = chessBoard;
        this.jLayeredPane = jLayeredPane;
        this.key = key;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int[] pos = chessBoard.stringChessPieceMap.get(key).position;
        if (gameView.selectedPieceKey != null && key.charAt(0) != chessBoard.player) {
            int[] selectedPiecePos = chessBoard.stringChessPieceMap.get(gameView.selectedPieceKey).position;
            for (int[] each : Objects.requireNonNull(Rules.getNextMove(gameView.selectedPieceKey, selectedPiecePos, chessBoard))) {
                if (Arrays.equals(each, pos)) {
                    JLabel jLabel = gameView.stringJLabelMap.get(key);
                    jLabel.setVisible(false);
//                    jLayeredPane.remove(gameView.stringJLabelMap.get(key));
//                    gameView.stringJLabelMap.remove(key);
                    gameController.moveChess(gameView.selectedPieceKey, pos, chessBoard);
                    gameView.movePieceFromModel(gameView.selectedPieceKey, pos);
                    break;
                }
            }
        } else if (key.charAt(0) == chessBoard.player) {
            gameView.setSquareLocation(pos);
            gameView.selectedPieceKey = key;
        }
    }
}
