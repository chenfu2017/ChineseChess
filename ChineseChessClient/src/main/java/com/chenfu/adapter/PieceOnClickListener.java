package com.chenfu.adapter;

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
        boolean send = false;
        if(gameView.status == GameStatusEnum.NETWORK_START.status){
            send = true;
        }
        int[] pos = chessBoard.stringChessPieceMap.get(key).position;
        String pieceKey = gameView.selectedPieceKey;
        if (pieceKey != null && key.charAt(0) != chessBoard.player) {
            int[] selectedPiecePos = chessBoard.stringChessPieceMap.get(pieceKey).position;
            for (int[] each : Objects.requireNonNull(Rules.getNextMove(pieceKey, selectedPiecePos, chessBoard))) {
                if (Arrays.equals(each, pos)) {
                    AudioPlayer audioPlayer = new AudioPlayer("eat.wav", false);
                    audioPlayer.play();
                    JLabel jLabel = gameView.stringJLabelMap.get(key);
                    jLabel.setVisible(false);
                    gameView.movePieceFromModel(pieceKey, pos,send);
                    System.out.println("Piece{ PieceKey:" + pieceKey + " src:" + Arrays.toString(selectedPiecePos) + " des:" + Arrays.toString(pos)+"}");
                    break;
                }
            }
        } else if (key.charAt(0) == chessBoard.player) {
            AudioPlayer audioPlayer = new AudioPlayer("select.wav", false);
            audioPlayer.play();
            gameView.setSquareLocation(pos);
            gameView.selectedPieceKey = key;
        }
    }
}
