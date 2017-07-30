package com.scripting.language.exception;

public class StoneException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6260980449880621734L;

	public StoneException(String m){
		super(m);
	}
	
//	public StoneException(String m,ASTree t){
//		super(m+" "+t.location());
//	}
}
