package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;
import java.util.ArrayList;
import java.util.List;

public class ChessMatch {
    
    private Board board;
    private int turn;
    private Color currentPlayer;

    private List<Piece> piecesOnTheBoard;
    private List<Piece> capturedPieces = new ArrayList<>();
    
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;
    
    //inicializando o tabuleiro já no método construtor
    public ChessMatch(){
        board = new Board(8,8);
        turn = 1;
        currentPlayer = Color.WHITE;
        piecesOnTheBoard = new ArrayList<>();
        initialSetup();
    }
    
    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    private void nextTurn(){
        turn++;
        if (currentPlayer == Color.WHITE) currentPlayer = Color.BLACK;
        else currentPlayer = Color.WHITE;
    }
    
    //alocando peças (ainda com valor nulo) no tabuleiro de xadrez
    public ChessPiece[][] getPieces(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for(int i = 0; i < board.getRows(); i++){
            for(int j = 0; j < board.getColumns(); j++){
                mat[i][j] = (ChessPiece) board.piece(i,j);
            }
        }
    return mat;
    }
    
    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition(); //conversão de posição de Xadrez em posição de matriz
        validateSourcePosition(position); 
        //validateTargetPosition(source, target);
        return board.piece(position).possibleMoves();
    }
    
    //Lógica de movimento e/ou captura de peças
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        nextTurn();
        return (ChessPiece) capturedPiece;
    }
    
    //verificando se a posição da peça a ser movida no tabuleiro é válida/está preenchida
    private void validateSourcePosition(Position position) {
        if(!board.thereIsAPiece(position)) throw new ChessException("There is no piece on source position! ");
        if(!board.piece(position).isThereAnyPossibleMove()) throw new ChessException("There is no possible moves for the chosen piece");
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) throw new ChessException("The chosen piece is not yours ");
    }

    //verificando se a posição do tabuleiro para onde o usuário quer mover uma peça é válida/está preenchida
    private void validateTargetPosition(Position source, Position target) {
        if(!board.piece(source).possibleMove(target)) throw new ChessException("The chosen piece can't move to target position ");
    }
    
    //mover uma peça
    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source);
        Piece captured = board.removePiece(target);
        
        if(captured != null){
            piecesOnTheBoard.remove(captured);
            capturedPieces.add(captured);
        }
        
        board.placePiece(p, target);
        return captured;
    }
    
    //Alocando determinada peça no tabuleiro e na lista de peças no tabuleiro
    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column,row).toPosition());
        piecesOnTheBoard.add(piece);
    }
    
    //Colocando todas as peças iniciais no tabuleiro
    private void initialSetup(){
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }

    

}
