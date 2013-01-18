package com.thinkgem.webeffect.hibernate;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页类
 * @author WangZhen <thinkgem@163.com>
 * @version $Id: Pagination.java 2658 2010-05-29 13:14:02Z wzhen $
 */
public class Pagination {
	
	private int pageIndex; // 当前页索引
	private int pageSize; // 页面大小
	
	private int count;// 总记录数
	
	private int first;// 首页索引
	private int last;// 尾页索引
	private int prev;// 上一页索引
	private int next;// 下一页索引
	
	private boolean firstPage;//是否是第一页
	private boolean lastPage;//是否是最后一页

	private int length = 8;// 显示页面长度
	private int slider = 1;// 前后显示页面长度
	
	@SuppressWarnings("unchecked")
	private List list;

	public Pagination(){
		
	}
	
	@SuppressWarnings("unchecked")
	public Pagination(int pageIndex, int pageSize, int count) {
		this(pageIndex, pageSize, count, new ArrayList());
	}
	
	@SuppressWarnings("unchecked")
	public Pagination(int pageIndex, int pageSize, int count, List list) {
		this.count = count;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.list = list;
				
		//1
		this.first = 1;
		
		this.last = (count / (this.pageSize < 1 ? 20 : this.pageSize) + first - 1);
		
		if (count % pageSize != 0 || this.last == 0) {
			this.last++;
		}

		if (this.last < this.first) {
			this.last = this.first;
		}
		
		if (this.pageIndex <= 1) {
			this.pageIndex = this.first;
			this.firstPage=true;
		}

		if (this.pageIndex >= this.last) {
			this.pageIndex = this.last;
			this.lastPage=true;
		}

		if (this.pageIndex < this.last - 1) {
			this.next = this.pageIndex + 1;
		} else {
			this.next = this.last;
		}

		if (this.pageIndex > 1) {
			this.prev = this.pageIndex - 1;
		} else {
			this.prev = this.first;
		}
		
		//2
		if (this.pageIndex < this.first) {// 如果当前页小于首页
			this.pageIndex = this.first;
		}

		if (this.pageIndex > this.last) {// 如果当前页大于尾页
			this.pageIndex = this.last;
		}
		
	}
	
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

//		sb.append("<div class=\""+(cssClass==null||cssClass==""?"pagination":cssClass)+"\">\n");
		
		sb.append("<ul>\n");
		
		//1

		if (pageIndex == first) {// 如果是首页
			sb.append("<li class=\"disabled\">&#171; 上一页</li>\n");
		} else {
			sb.append("<li><a href=\"javascript:page("+prev+");\">" + "&#171; 上一页</a></li>\n");
		}

		//2
		
		int begin = pageIndex - (length / 2);

		if (begin < first) {
			begin = first;
		}

		int end = begin + length - 1;

		if (end >= last) {
			end = last;
			begin = end - length + 1;
			if (begin < first) {
				begin = first;
			}
		}

		if (begin > first) {
			int i = 0;
			for (i = first; i < first + slider && i < begin; i++) {
				sb.append("<li><a href=\"javascript:page("+i+");\">"
						+ (i + 1 - first) + "</a></li>\n");
			}
			if (i < begin) {
				sb.append("<li class=\"none\">...</li>\n");
			}
		}

		for (int i = begin; i <= end; i++) {
			if (i == pageIndex) {
				sb.append("<li class=\"current\">" + (i + 1 - first)
						+ "</li>\n");
			} else {
				sb.append("<li><a href=\"javascript:page("+i+");\">"
						+ (i + 1 - first) + "</a></li>\n");
			}
		}

		if (last - end > slider) {
			sb.append("<li class=\"none\">...</li>\n");
			end = last - slider;
		}

		for (int i = end + 1; i <= last; i++) {
			sb.append("<li><a href=\"javascript:page("+i+");\">"
					+ (i + 1 - first) + "</a></li>\n");
		}

		if (pageIndex == last) {
			sb.append("<li class=\"disabled\">下一页 &#187;</li>\n");
		} else {
			sb.append("<li><a href=\"javascript:page("+next+");\">"
					+ "下一页 &#187;</a></li>\n");
		}

		sb.append("</ul>\n");

		sb.append("<p>");
		
		sb.append("&nbsp;共 " + count + " 条");
		
		sb.append("</p>");

//		 sb.append("</div>\n");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Pagination p = new Pagination(3, 3, 25);		
		System.out.println(p);
		System.out.println("首页："+p.getFirst());
		System.out.println("尾页："+p.getLast());
		System.out.println("上页："+p.getPrev());
		System.out.println("下页："+p.getNext());
	}

	public int getCount() {
		return count;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getFirst() {
		return first;
	}

	public int getLast() {
		return last;
	}
	
	public int getTotalPage() {
		return last;
	}

	public boolean isFirstPage() {
		return firstPage;
	}

	public boolean isLastPage() {
		return lastPage;
	}
		
	public int getPrev() {
		if (isFirstPage()) {
			return pageIndex;
		} else {
			return pageIndex - 1;
		}
	}

	public int getNext() {
		if (isLastPage()) {
			return pageIndex;
		} else {
			return pageIndex + 1;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List getList() {
		return list;
	}

	@SuppressWarnings("unchecked")
	public void setList(List list) {
		this.list = list;
	}
}
