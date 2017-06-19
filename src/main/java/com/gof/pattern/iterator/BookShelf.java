package com.gof.pattern.iterator;

/**
 * 表示书架的类
 * @Description:TODO
 * @author gbs
 * @Date 2017年6月19日 下午3:29:10
 */
public class BookShelf implements Aggregate {

	private Book[] books;
	private int last = 0;
	
	public BookShelf(int maxsize){
		this.books = new Book[maxsize];
	}
	
	public Book getBookAt(int index){
		return books[index];
	}
	
	public void appendBook(Book book){
		this.books[last] = book;
		last++;
	}
	
	public int getLength(){
		return last;
	}
	
	@Override
	public Iterator iterator() {
		return new BookShelfIterator(this);
	}

}
