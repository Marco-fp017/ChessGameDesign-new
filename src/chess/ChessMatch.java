package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.Rook;

public class ChessMatch {
    
    private Board board;
    
    private int turn;
    private Color currentPlayer;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;
    
    public ChessMatch(){
        board = new Board(8,8);
        initialSetup();
    }
    
    public ChessPiece[][] getPieces(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for(int i = 0; i < board.getRows() ; i++){
            for(int j = 0; j < board.getColumns() ; j++){
                mat[i][j] = (ChessPiece) board.piece(i,j);
            }
        }
    return mat;
    }
    
    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column,row).toPosition());
    }
    
    //Método responsável por colocar as peças no tabuleiro, iniciando a partida
    private void initialSetup(){
        // novo teste, passando como parâmetro uma peça já na forma como será mostrada no tabuleiro
        placeNewPiece('b', 6, new Rook(board, Color.BLACK)); 
        board.placePiece( new Rook(board, Color.BLACK), new Position(7,4)); // apenas um teste
    }
  
}
