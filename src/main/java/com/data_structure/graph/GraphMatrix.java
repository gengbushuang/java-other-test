package com.data_structure.graph;

import java.util.ArrayList;
import java.util.List;

public class GraphMatrix implements GraphMethod {

	class Vertex {
		private String lable;
		private boolean b;

		Vertex(String lable) {
			this(lable, false);
		}

		Vertex(String lable, boolean b) {
			this.lable = lable;
			this.b = b;
		}
	}

	private Vertex[] vertexs;
	private int[][] edge;
	private int size = 0;

	public GraphMatrix(int n) {
		vertexs = new Vertex[n];
		edge = new int[n][n];
	}

	public void addVertex(String lable) {
		if (size < vertexs.length) {
			addVertex(lable, size);
			size++;
		}
	}

	public void addVertex(String lable, int index) {
		vertexs[index] = new Vertex(lable);
	}

	public void addEdge(int start, int end) {
		edge[start][end] = 1;
		edge[end][start] = 1;
	}

	public String toString() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == j || edge[i][j] != 1) {
					continue;
				}
				System.out.println("(" + show(i) + "," + show(j) + ")");
			}
		}
		return null;
	}

	public String show(int i) {
		return vertexs[i].lable;
	}

	public void DFS() {
		System.out.println("---dfs----");
		dfsShow(0);
		System.out.println();
		for(int i = 0;i<size;i++){
			vertexs[i].b = false;
		}
	}

	public void dfsShow(int i) {
		if (vertexs[i].b) {
			return;
		}
		System.out.print(show(i) + "->");
		vertexs[i].b = true;
		for (int j = 0; j < size; j++) {
			if (i == j || edge[i][j] != 1 || vertexs[j].b) {
				continue;
			}
			dfsShow(j);
		}
	}

	public void BFS() {
		System.out.println("---bfs----");
		bfsShow(0);
		System.out.println();
		for(int i = 0;i<size;i++){
			vertexs[i].b = false;
		}
	}

	public void bfsShow(int i) {
		vertexs[i].b = true;
//		System.out.print(show(i) + "->");
		List<Integer> l = new ArrayList<Integer>();
		for (int j = 0; j < size; j++) {
			if (i == j || edge[i][j] != 1 || vertexs[j].b) {
				continue;
			}
			l.add(j);
			System.out.print(show(j) + "->");
		}
		
		for(int t :l){
			if(!vertexs[t].b){
				bfsShow(t);
			}
		}
	}

	public static void main(String[] args) {
		GraphMatrix graph = new GraphMatrix(20);
		graph.addVertex("v0");// 0
		graph.addVertex("v1");// 1
		graph.addVertex("v2");// 2
		graph.addVertex("v3");// 3

		graph.addEdge(0, 1);// v0v1
//		graph.addEdge(0, 2);// v0v2
		graph.addEdge(0, 3);// v0v3
		//
		graph.addEdge(1, 2);

//		graph.addEdge(2, 3);

		graph.toString();
		graph.DFS();
		graph.BFS();

	}

}
