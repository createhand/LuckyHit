package kr.co.toto.util;

import org.apache.commons.lang.StringUtils;

/**
 * 페이징 구성 클래스
 * 
 * @author UC
 *
 */
public class Paging {
	private int totalRows = 0;
	private int currentPage = 1;
	private int pageListSize = 20;
	private int pagingGroupSize = 1;
	private int totalPages;
	private int totalBlocks;
	private int startPageNum;
	private int endPageNum;
	private int currentBlock;

	// for design
	// 처음으로가기 활성화된 링크 이미지 설정
	private String firstLink       = "<img src=\"/inbk/images/ko/button/btn_first.gif\" alt=\"처음으로\" onClick=\"#script#\" class=\"hand paging_fbtn\"/>";
	// 처음으로가기 비활성화된 링크 이미지 설정
	private String firstOffLink    = "<img src=\"/inbk/images/ko/button/btn_first.gif\" alt=\"처음으로\" class=\"paging_fbtn\"/>";
	// 이전으로가기 활성화된 링크 이미지 설정
	private String prevLink        = "<img src=\"/inbk/images/ko/button/btn_prev.gif\" alt=\"이전\" onClick=\"#script#\" class=\"hand paging_pbtn\"/>";
	// 이전으로가기 비활성화된 링크 이미지 설정
	private String prevOffLink     = "<img src=\"/inbk/images/ko/button/btn_prev.gif\" alt=\"이전\" class=\"paging_pbtn\"/>";
	// 다음으로가기 활성화된 링크 이미지 설정
	private String nextLink        = "<img src=\"/inbk/images/ko/button/btn_next.gif\" alt=\"다음\" onClick=\"#script#\" class=\"hand paging_fbtn\"/>";
	// 다음으로가기 비활성화된 링크 이미지 설정
	private String nextOffLink     = "<img src=\"/inbk/images/ko/button/btn_next.gif\" alt=\"다음\" class=\" paging_fbtn\"/>";
	// 마지막으로가기 활성화된 링크 이미지 설정
	private String lastLink        = "<img src=\"/inbk/images/ko/button/btn_lastly.gif\" alt=\"마지막으로\" onClick=\"#script#\" class=\"hand paging_lbtn\"/>";
	// 마지막으로가기 비활성화된 링크 이미지 설정
	private String lastOffLink     = "<img src=\"/inbk/images/ko/button/btn_lastly.gif\" alt=\"마지막으로\" class=\"paging_lbtn\"/>";

	// 페이지별 구분자 
//	private String delimiter = "|";

	// current Page Wrapper
//	private String preWrap = "<b>";
//	private String postWrap = "</b>";

	private String scriptName = "goPage";
	
	public void setFirstLink(String firstLink) {
		this.firstLink = firstLink;
	}

	public void setFirstOffLink(String firstOffLink) {
		this.firstOffLink = firstOffLink;
	}

	public void setPrevLink(String prevLink) {
		this.prevLink = prevLink;
	}

	public void setPrevOffLink(String prevOffLink) {
		this.prevOffLink = prevOffLink;
	}

	public void setNextLink(String nextLink) {
		this.nextLink = nextLink;
	}

	public void setNextOffLink(String nextOffLink) {
		this.nextOffLink = nextOffLink;
	}

	public void setLastLink(String lastLink) {
		this.lastLink = lastLink;
	}

	public void setLastOffLink(String lastOffLink) {
		this.lastOffLink = lastOffLink;
	}

//	public void setDelimiter(String delimiter) {
//		this.delimiter = delimiter;
//	}

//	public void setPreWrap(String preWrap) {
//		this.preWrap = preWrap;
//	}
//
//	public void setPostWrap(String postWrap) {
//		this.postWrap = postWrap;
//	}

	/**
	 * 페이지나 링크버튼을 클릭할 때 호출될 자바스크립트를 설정한다.
	 * 
	 * @param scriptName 스크립트명
	 */
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	// result temp object
	public StringBuffer pageString = new StringBuffer();

	/**
	 * 생성자
	 * 
	 * @param totalRows 전체 리스트 수
	 * @param currentPage 현재 페이지
	 * @param pageListSize 페이지당 출력될 리스트 크기
	 * @param pagingGroupSize 페이징 그룹 크기
	 */
	public Paging(int totalRows, int currentPage, int pageListSize, int pagingGroupSize) {
		this.totalRows = totalRows;
		this.currentPage = currentPage;
		this.pageListSize = pageListSize;
		this.pagingGroupSize = pagingGroupSize;

		if (this.totalRows > 0) {
			initialize();
		}
	}

	private void initialize() {
		this.totalPages = (this.totalRows / this.pageListSize) + (this.totalRows % this.pageListSize == 0 ? 0 : 1);
		this.totalBlocks = (int) Math.ceil((double) this.totalPages / this.pagingGroupSize);
		// numPageGroup = 올림(currentPage/pageGroupSize)
		this.currentBlock = (int) Math.ceil ((double)currentPage/pagingGroupSize);
		this.startPageNum = ((this.currentBlock - 1) * this.pagingGroupSize) + 1;
		this.endPageNum = this.startPageNum + this.pagingGroupSize - 1;
		if (totalPages < endPageNum) endPageNum = totalPages;
	}

	private void prePrint() {
		// set first block link
		if (this.currentBlock > 1)
		    pageString.append(StringUtils.replace(this.firstLink, "#script#", this.scriptName + "(1);") + "\n" );
		else
			pageString.append(this.firstOffLink + "\n" );

		// set prev page link
		if (this.currentPage > 1)
		    pageString.append(StringUtils.replace(this.prevLink, "#script#", this.scriptName + "("+ (this.currentPage - 1) + ");") + "\n" );
		else
			pageString.append(this.prevOffLink + "\n");
	}

	private void postPrint() {
		// set next page link
		if (this.currentPage < this.totalPages)
		    pageString.append(StringUtils.replace(this.nextLink, "#script#", this.scriptName + "("+ (this.currentPage + 1) + ");") + "\n" );
		else
			pageString.append(this.nextOffLink + "\n");

		// set last page link
		if (this.currentBlock < this.totalBlocks)
		    pageString.append(StringUtils.replace(this.lastLink, "#script#", this.scriptName + "("+ (totalPages) + ");") + "\n" );
		else
			pageString.append(this.lastOffLink + "\n");
	}

	private void printList() {
		for (int i = startPageNum; i <= endPageNum; i++) {

			if (i == this.currentPage && i == this.startPageNum) 
			    pageString.append("<span class=\"on first\">"+i+"</span>\n");
		    else if(i == this.currentPage && i == this.endPageNum) 
		        pageString.append("<span class=\"on last\">"+i+"</span>\n");
		    else if(i == this.startPageNum) 
		        pageString.append("<a href=\"javascript:" + this.scriptName + "("+ i + ");\" class=\"first\">" + i+ "</a>\n");
		    else if(i == this.endPageNum)
		        pageString.append("<a href=\"javascript:" + this.scriptName + "("+ i + ");\" class=\"last\">" + i+ "</a>\n");
		    else if(i == this.currentPage)
		        pageString.append("<span class=\"on\">"+i+"</span>\n");
		    else
		        pageString.append("<a href=\"javascript:" + this.scriptName + "("+ i + ");\">" + i+ "</a>\n");
		}
	}
	
//	private void printCurrPage() {
//		pageString.append(" <span class=\"value\">" + currentPage + " / " + totalPages + "</span> ");
//	}

	/**
	 * 작성된 페이징 영역을 출력한다.
	 * @return 페이징 HTML
	 */
	public String print() {
		if (this.totalPages > 1) {
//			pageString.append("				<div class=\"paging\">");
			this.prePrint();
			this.printList();
//			this.printCurrPage();
			this.postPrint();
//			pageString.append("				</div>");
		}

		return (pageString.toString());
	}

	/**
	 * @param args
	 */

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		Paging page = new Paging(2000, 12, 10, 10);
		page.scriptName = "goPage";

		// for design
//		page.firstLink = "<img src=\"/first.gif\">";
//		page.prevLink = "<img src=\"/prev.gif\">";
//		page.nextLink = "<img src=\"/next.gif\">";
//		page.lastLink = "<img src=\"/last.gif\">";
//
//		page.firstOffLink = "[ << ]";
//		page.prevOffLink = "[ < ]";
//		page.nextOffLink = "[ > ]";
//		page.lastOffLink = "[ >> ]";
//
//		page.delimiter = "||";

		// print
		System.out.println(page.print());
	}

}