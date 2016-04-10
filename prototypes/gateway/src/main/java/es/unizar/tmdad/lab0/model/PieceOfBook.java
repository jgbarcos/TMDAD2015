package es.unizar.tmdad.lab0.model;

public class PieceOfBook {
	
	String id ;
	String content;
	int numPieces;
	int piece;
	
	
	
	public PieceOfBook(String id, String content, int numPieces, int piece) {
		super();
		this.id = id;
		this.content = content;
		this.numPieces = numPieces;
		this.piece = piece;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getNumPieces() {
		return numPieces;
	}
	public void setNumPieces(int numPieces) {
		this.numPieces = numPieces;
	}
	public int getPiece() {
		return piece;
	}
	public void setPiece(int piece) {
		this.piece = piece;
	}
	
	
	
	
	

}
