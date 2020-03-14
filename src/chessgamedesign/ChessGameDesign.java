package chessgamedesign;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.Scanner;

public class ChessGameDesign {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChessMatch ch = new ChessMatch();
        
        while(1 == 1){
            UI.printBoard(ch.getPieces());
            System.out.println();
            System.out.print("Source: ");
            ChessPosition source = UI.readChessPosition(sc);
            
            System.out.print("Target: ");
            ChessPosition target = UI.readChessPosition(sc);
            
            ChessPiece capturedPiece = ch.performChessMove(source, target);
            System.out.println();
        }
    }
    
}
