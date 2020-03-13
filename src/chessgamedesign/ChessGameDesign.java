package chessgamedesign;

import chess.ChessMatch;

public class ChessGameDesign {

    public static void main(String[] args) {
        ChessMatch ch = new ChessMatch();
        UI.printBoard(ch.getPieces());
    }
    
}
