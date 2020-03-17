package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {
    
    private Board board;
    private int turn;
    private Color currentPlayer;
    private boolean check;
    private boolean checkMate;
    
    private List<Piece> piecesOnTheBoard;
    private List<Piece> capturedPieces = new ArrayList<>();
    
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
    
    public boolean getCheck(){
        return check;
    }

    public boolean getCheckMate(){
        return checkMate;
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
        return board.piece(position).possibleMoves();
    }
    
    //Lógica de movimento e/ou captura de peças
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        
        Piece capturedPiece = makeMove(source, target);
        if(testCheck(currentPlayer)){
            undoMove(source, target, capturedPiece); // se o rei for colocado em cheque, desfaz-se o movimento
            throw new ChessException ("You don't can put yourself in check");
        } 
        check = testCheck(opponent(currentPlayer)) ? true : false; // verifica-se se o oponente ficou em cheque 
        if(testCheckMate(opponent(currentPlayer))) checkMate = true;
        else nextTurn();
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
    
    //desfazer a operação de movimento de uma peça 
    private void undoMove(Position source, Position target, Piece capturedPiece){
        Piece p = board.removePiece(target);
        board.placePiece(p, source);
        
        if(capturedPiece != null){
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }

    private Color opponent(Color c){
        if(c == Color.WHITE) return Color.BLACK;
        else return Color.WHITE;
    }
    
    private ChessPiece king(Color c){
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == c).collect(Collectors.toList());
        for (Piece p : list){
            if(p instanceof King) return (ChessPiece)p;
        }
        throw new IllegalStateException("There is no " + c + " king in the board");
    }

    //método para testar se o rei de um dos jogadores está em check
    private boolean testCheck(Color c){
        Position kingPosition = king(c).getChessPosition().toPosition();
        List<Piece> opponentPieces=piecesOnTheBoard.stream().filter(x ->((ChessPiece)x).getColor()==opponent(c)).collect(Collectors.toList());
        for (Piece p : opponentPieces){
            boolean[][] possibleMoviments = p.possibleMoves();
            if(possibleMoviments[kingPosition.getRow()][kingPosition.getColumn()]) return true;
         }
        return false;
    }

    //método para testar se o rei de um dos jogadores está em check-mate
    private boolean testCheckMate(Color c){
        if(!testCheck(c)) return false;
        //Criando uma lista apenas com peças do oponente do rei em cheque
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == c).collect(Collectors.toList());
        
        //Essa lista irá testar cada movimento das suas peças e verificar se alguma dessas tira o rei do check-mate
        for (Piece p : list){  
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++){
                for(int j = 0; j < board.getColumns(); j++){
                    if(mat[i][j]){
                        Position source = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position(i,j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(c);
                        undoMove(source, target, capturedPiece);
                        if(!testCheck) return false;
                    }
                }
            }
        }
        return true;
    }
    
    //Alocando determinada peça no tabuleiro e na lista de peças no tabuleiro
    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column,row).toPosition());
        piecesOnTheBoard.add(piece);
    }
    
    //Colocando todas as peças iniciais no tabuleiro
    private void initialSetup(){
        /*placeNewPiece('a', 1, new Rook(board, Color.WHITE));
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
        */
        placeNewPiece('h', 7, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        
        
        placeNewPiece('b', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 8, new King(board, Color.BLACK));
        
    }
}
