package chessgamedesign;

import chess.ChessMatch;
import chess.ChessPiece;

public class UI {
    public static void printBoard(ChessPiece[][] ch){
        for (int i = 0; i < ch.length ; i++){
            System.out.print((8 - i) + " ");
            for (int j = 0; j < ch.length ; j++){
                printPiece(ch[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }
    
    private static void printPiece(ChessPiece ch){
        if(ch == null) System.out.print("-");
        else System.out.print(ch);
        System.out.print(" ");
    }
}
