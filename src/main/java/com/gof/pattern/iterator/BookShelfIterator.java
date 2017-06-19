package com.gof.pattern.iterator;

/**
 * 遍历书架的类
 * @Description:TODO
 * @author gbs
 * @Date 2017年6月19日 下午3:28:15
 */
public class BookShelfIterator implements Iterator {

	private BookShelf bookShelf;
	private int index;

	public BookShelfIterator(BookShelf bookShelf) {
		this.bookShelf = bookShelf;
		this.index = 0;
	}

	@Override
	public boolean hasNext() {
		if (index < bookShelf.getLength()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object next() {
		Book book = bookShelf.getBookAt(index);
		index++;
		return book;
	}

}
