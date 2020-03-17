package chessgamedesign;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ChessGameDesign {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChessMatch ch = new ChessMatch();
        List<ChessPiece> cp = new ArrayList<>();
        
        while(1 == 1){
            try{
                UI.clearScreen();
                UI.printMatch(ch, cp);
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(sc);
                
                boolean [][] possibleMoves = ch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(ch.getPieces(), possibleMoves);
                System.out.println();

                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece capturedPiece = ch.performChessMove(source, target);
                if(capturedPiece != null) cp.add(capturedPiece);
                
            } catch(ChessException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println(e.getMessage());
                sc.nextLine();                
            } 
        }
    }
    
}
