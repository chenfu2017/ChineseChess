package com.chenfu;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardStatus {

    private List<String> stringList;

    public ChessBoardStatus() {
        stringList= new ArrayList<String>();
    }
    public void save(ChessPiece[][] chessPieces) {
        StringBuilder s = new StringBuilder();
        for (ChessPiece[] piece : chessPieces) {
            for (ChessPiece chessPiece : piece) {
                if (chessPiece != null) {
                    s.append(Integer.toHexString(chessPiece.getId() - 8));
                } else {
                    s.append(7);
                }
            }
        }
        stringList.add(s.toString());
    }
}
