package com.data_structure.graph;

public class GraphTable {

	private class Vertex {
		private String lable;
		private boolean b;
		private Vertex next;

		Vertex(String lable) {
			this(lable, false);
		}

		Vertex(String lable, boolean b) {
			this.lable = lable;
			this.b = b;
		}
	}
	
	private Vertex[] vertexs;
	private int size=0;
	public GraphTable(int n){
		vertexs = new Vertex[n];
	}
	
	public void addVertex(String lable){
		if(size<vertexs.length){
			addVertex(lable, size);
			size++;
		}
	}
	
	public void addVertex(String lable,int index){
		vertexs[index] = new Vertex(lable);
	}
	
	public void addEdge(int start,int end){
		Vertex p = vertexs[start];
		Vertex c = vertexs[start].next;
		for(;;){
			if(c == null){
				c = new Vertex(vertexs[end].lable);
				p.next = c;
				break;
			}
			p = c;
			c = p.next;
		}
	}
	
	public String toString(){
		for(int i = 0;i<size;i++){
			show(vertexs[i]);
		}
		return null;
	}
	
	public void show(Vertex v){
		Vertex c = v.next;
		String s = v.lable;
		for(;;){
			if(c == null){
				break;
			}
			System.out.println("("+s+","+c.lable+")");
			c = c.next;
		}
	}
	
	public String show(int i){
		return vertexs[i].lable;
	}
	
	public static void main(String[] args) {
		GraphTable graph = new GraphTable(20);
		graph.addVertex("v0");//0
		graph.addVertex("v1");//1
		graph.addVertex("v2");//2
		graph.addVertex("v3");//3
		
		graph.addEdge(0, 1);//v0v1
		graph.addEdge(0, 2);//v0v2
		graph.addEdge(0, 3);//v0v3
		
		graph.addEdge(1, 2);
		
		graph.addEdge(2, 3);
		
		graph.toString();
	}
	
}
