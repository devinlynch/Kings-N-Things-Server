package com.kings.model;

public class Move {
	private String moveMadeByID;
	private String destinationID;
	private String originID;
	private String pieceID;
	
	public Move(String moveMadeById, String destinationID, String originID, String pieceID) {
		this.moveMadeByID=moveMadeById;
		this.destinationID=destinationID;
		this.originID=originID;
		this.setPieceID(pieceID);
	}
	
	public String getMoveMadeByID() {
		return moveMadeByID;
	}
	public void setMoveMadeByID(String moveMadeByID) {
		this.moveMadeByID = moveMadeByID;
	}
	public String getDestinationID() {
		return destinationID;
	}
	public void setDestinationID(String destinationID) {
		this.destinationID = destinationID;
	}
	public String getOriginID() {
		return originID;
	}
	public void setOriginID(String originID) {
		this.originID = originID;
	}

	public String getPieceID() {
		return pieceID;
	}

	public void setPieceID(String pieceID) {
		this.pieceID = pieceID;
	}
}
