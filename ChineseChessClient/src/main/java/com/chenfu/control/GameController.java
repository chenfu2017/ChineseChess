package com.chenfu.control;
import com.chenfu.alogrithm.AlphaBetaNode;
import com.chenfu.alogrithm.SearchModel;
import com.chenfu.chess.ChessBoard;
import com.chenfu.view.GameView;

public class GameController {

    private SearchModel searchModel;

    public GameController() {
        searchModel = new SearchModel(this);
    }

    public void moveChess(String key, int[] position, ChessBoard chessBoard) {
        chessBoard.updatePiece(key, position);
    }


    public int[] responseMoveChess(ChessBoard chessBoard, GameView gameView) {
        AlphaBetaNode result = searchModel.search(chessBoard);
        gameView.movePieceFromAI(result.piece, result.to);
        chessBoard.updatePiece(result.piece, result.to);
        return result.to;
    }


    public char hasWin(ChessBoard chessBoard) {

        boolean isRedWin = chessBoard.stringChessPieceMap.get("bb0") == null;
        boolean isBlackWin = chessBoard.stringChessPieceMap.get("rb0") == null;
        if (isRedWin) return 'r';
        else if (isBlackWin) return 'b';
        else return 'x';
    }
}
