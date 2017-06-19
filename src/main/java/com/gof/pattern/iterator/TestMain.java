package com.gof.pattern.iterator;

/**
 * 	iterator(迭代器)
 *  该角色负责定义顺序逐个遍历元素的接口.示例中，有Iterator接口扮演的角色，
 *  定义类hasNext和next两个方法。其中，hasNext方法用于判断是否存在下一个元素，
 *  next方法则用于获取该元素。
 *  
 *  ConcreteIterator(具体的迭代器)
 *  该角色负责实现Iterator角色所定义的接口。示例中，由BookShelfIterator类扮演这个角色。
 *  该角色中包含类遍历集合所必须的信息。BookShelf类的实例保存在bookShelf字段中，
 *  被指向的书的下标保存在index字段中。
 *  
 *  Aggregate(集合)
 *  该角色负责定义创建Iterator角色的接口。这个接口是一个方法，
 *  会创建出"按顺序访问保存在我内部元素的人"。示例中。由Aggregate接口扮演这个角色，
 *  它里面定义了iterator方法
 *  
 *  ConcreteAggregate(具体的集合)
 *  该角色负责实现Aggregate角色所定义的接口。它会创建出具体的Iterator角色，
 *  即ConcreteIterator角色。示例中，由BookShelf类扮演这个角色，它实现了iterator方法。
 * 
 * 
 * @Description:TODO
 * @author gbs
 * @Date 2017年6月19日 下午3:30:25
 */
public class TestMain {

	public static void main(String[] args) {
		BookShelf bookShelf = new BookShelf(4);
		bookShelf.appendBook(new Book("a"));
		bookShelf.appendBook(new Book("b"));
		bookShelf.appendBook(new Book("c"));
		bookShelf.appendBook(new Book("d"));
		
		Iterator iterator = bookShelf.iterator();
		while(iterator.hasNext()){
			Book next = (Book)iterator.next();
			System.out.println(next.getName());
		}
	}
}
