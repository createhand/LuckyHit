/*--------------------------------------------------------------------------*/
/*	클래스명			: */
/*--------------------------------------------------------------------------*/
/* 01.	업무명			: */
/* 02.	클래스기능		: */
/* 03.	프로그램명		: */
/* 04.	작성자			: */
/* 05.	작성일자			: */
/* 06.	Argument Type		: */
/* 07.	Inheritance Class	: */
/* 08.	Implements Interface    : */
/* 09.	작업주기			: 수시                                       */
/* 10.	사용부서			:                                           */
/* 11.	REMARKS 사항		: 												*/
/*--------------------------------------------------------------------------*/
/*	HISTORY 사항															*/
/*	------------															*/
/*	                           					    */
/*--------------------------------------------------------------------------*/

package kr.co.toto.comn.exception;

public class SocketTimeoutException extends Exception {
	private Exception e;
	
	public SocketTimeoutException(Exception ex) {
		super(ex.getMessage()); 
		this.e = ex;
	}
	
	public SocketTimeoutException(String msg) {
		super(msg);
	}
	
	public Exception getException() {
		return e;
	}
}
