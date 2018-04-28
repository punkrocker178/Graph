package sample;

import javafx.geometry.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
//
//
//public class Graph {
//
//	private LinkedList<Integer> adjacencyList[];
//	private int Vertex;
//	private String[] VertexName;
//	private Point2D vertexPoint[];
//	private double adjacencyMatrix[][];
//	BufferedReader reader;
//
//
//	public Graph(String fileName){
//		String line = "";
//		String[] st ;
//		Integer NumvNearby,vNearby;
//		try {
//
//			reader = new BufferedReader(new FileReader(fileName));
//			Vertex = Integer.parseInt(reader.readLine());
//			/*adjacencyList size depends on number of vertices*/
//			adjacencyList = new LinkedList[Vertex];
//			adjacencyMatrix = new double[Vertex][Vertex];
//			VertexName = new String[Vertex];
//			vertexPoint = new Point2D[Vertex];
//
//			/*Initialization*/
//			for(int i = 0; i < Vertex ; i++){
//				adjacencyList[i] = new LinkedList<>();
//				vertexPoint[i] = new Point2D(0,0);
//				for(int j=0;j<Vertex;j++) {
//
//						adjacencyMatrix[i][j] = 0;
//
//				}
//			}
//
//			for(int j=0;j<Vertex;j++){
//				line = reader.readLine();
//				st = line.trim().split("\\s+");
//				/*vNearby so luong dinh ke*/
//				 NumvNearby = Integer.parseInt(st[0]);
//				for(int i=0;i<NumvNearby;i++){
//					vNearby =Integer.parseInt(st[i*2+1]);
//					adjacencyList[j].addFirst(vNearby);
//					adjacencyMatrix[j][vNearby] = Double.parseDouble(st[i*2+2]);
//				}
//				/*Vi tri cua ten dinh dua vao so luong dinh ke +1
//				* Vd: 2 dinh thi vi tri cua no la (2*2)+1 = 5*/
//				VertexName[j] = st[NumvNearby*2 +1];
//				/*Tuong tu vi tri cua dinh,voi x ta cong them 2,voi y ta cong them 3*/
//				vertexPoint[j] = vertexPoint[j].add(Double.parseDouble(st[NumvNearby*2+2]),Double.parseDouble(st[NumvNearby*2+3]));
//				/*----------------------*/
//			}
//
//
//
//		}catch(IOException error){
//
//			error.printStackTrace();
//		}
//	}
//	public void print(){
//
//		for(int v = 0; v < this.Vertex; v++)
//		{
//
//			System.out.println("Vertex: "+v+" is "+VertexName[v]+" X:"+this.vertexPoint[v].getX()+" Y"+this.vertexPoint[v].getY());
//
//
//			System.out.print("head");
//			for(Integer pCrawl: this.adjacencyList[v]){
//				System.out.print(" -> "+pCrawl);
//			}
//			System.out.println("\n");
//
//		}
//
//		for(int j=0;j<this.Vertex;j++){
//
//			for (int i=0;i<this.Vertex;i++){
//				System.out.print(adjacencyMatrix[j][i]+" ");
//			}
//			System.out.println();
//		}
//	}
//
//	int minDistance(int dist[], Boolean sptSet[])
//	{
//		// Initialize min value
//		int min = Integer.MAX_VALUE, min_index=-1;
//
//		for (int v = 0; v < this.Vertex; v++)
//			if (sptSet[v] == false && dist[v] <= min)
//			{
//				min = dist[v];
//				min_index = v;
//			}
//
//		return min_index;
//	}
//
//	public int[] find_dijkstra(int src) {
//		int dist[] = new int[this.Vertex];
//		Boolean sptSet[] = new Boolean[this.Vertex];
//		for (int i = 0; i < this.Vertex; i++)
//		{
//			dist[i] = Integer.MAX_VALUE;
//			sptSet[i] = false;
//		}
//		dist[src] = 0;
//		for (int count = 0; count < this.Vertex-1; count++){
//			int u = minDistance(dist, sptSet);
//			sptSet[u] = true;
//			for (int v = 0; v < this.Vertex; v++){
//				if (!sptSet[v] && adjacencyMatrix[u][v]!=0 && dist[u] != Integer.MAX_VALUE && dist[u]+adjacencyMatrix[u][v] < dist[v])
//					dist[v] =  dist[u] + (int)adjacencyMatrix[u][v];
//			}
//		}
//		return dist;
//	}
//
//	public String printSolution(int dist[])
//	{
//		String s="";
//		for (int i = 0; i < this.Vertex; i++)
//			s= s +i+" : "+dist[i]+"\n";
//		return s;
//	}


public class Graph{
	LinkedList<NodeVertex> linkedList[];
	Point2D vertexPoint[];
	int numVertex,numVnearby,neighborV;
	String[] vertexName;


	public Graph(String fileName){
		try{
			/*Initialize*/

			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			this.numVertex = Integer.parseInt(reader.readLine());
			linkedList = new LinkedList[numVertex];
			vertexPoint = new Point2D[numVertex];
			vertexName = new String[numVertex];


			for(int i=0;i<numVertex ;i++ ) {
				linkedList[i] = new LinkedList<>();
				vertexPoint[i] = new Point2D(0,0);
			}

			for(int i=0;i<numVertex;i++){
				String line = reader.readLine();
				String[] st = line.split("\\s+");
				numVnearby = Integer.parseInt(st[0]);
				for(int j=0;j<numVnearby;j++){
					/*						new NodeVertex(int vertex,int weigth)*/
					linkedList[i].addFirst(new NodeVertex(Integer.parseInt(st[j*2+1]),Integer.parseInt(st[j*2+2])));
				}
				vertexName[i] = st[2*numVnearby+1];
				vertexPoint[i] = vertexPoint[i].add(Double.parseDouble(st[2*numVnearby+2]),Double.parseDouble(st[2*numVnearby+3]));

			}

		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void print(){
		for(int i =0;i<this.numVertex;i++){
			System.out.println(vertexName[i]+"\tX:"+vertexPoint[i].getX()+"\tY:"+vertexPoint[i].getY());
			System.out.print("Head");
			for(NodeVertex node:this.linkedList[i]){
				System.out.print(" -> ("+node.vertex+","+node.weight+")");
			}
			System.out.println();
		}
	}

	public int[] findDijkstra(int src){
//		PriorityQueue<NodeVertex> pq = new PriorityQueue<>(this.numVertex);
		LinkedList<NodeVertex> pq = new LinkedList<>();
		int[] distance = new int[this.numVertex];
		int[] parent = new int[this.numVertex];


		/*Initialize*/
		for(int i=0;i<this.numVertex;i++){
			if(i==src)
				pq.addLast(new NodeVertex(src,0));
			else {
				pq.addLast(new NodeVertex(i, 999));
				distance[i] = 999;
			}
		}
		parent[src] = -1;
		distance[src] = 0;


		while(!pq.isEmpty()){

			NodeVertex u = pq.poll();

			for(NodeVertex v: this.linkedList[u.vertex] ){
				if(distance[v.vertex] > distance[u.vertex] + v.weight){
					distance[v.vertex] = distance[u.vertex] + v.weight;
					parent[v.vertex] = u.vertex;
					pq.addFirst(v);
				}

			}
		}
		return distance;
	}

		public String printSolution(int dist[])
	{
		String s="";
		for (int i = 0; i < this.numVertex; i++)
			s= s +i+" : "+dist[i]+"\n";
		return s;
	}

	public double VertexGetX(int vertex){
		return this.vertexPoint[vertex].getX();
	}

	public double VertexGetY(int vertex){
		return this.vertexPoint[vertex].getY();
	}

	public String getVertexName(int vertex){
		return this.vertexName[vertex];
	}

	public int getVertex(){
		return this.numVertex;
	}

/*
public LinkedList<Integer>[] getAdjList(){
return this.adjacencyList;
}

public double[][] getAdjMatrix(){
return this.adjacencyMatrix;
}


public static void main(String[] args){
Graph gr = new Graph("Graph.txt");
//		gr.print();
gr.find_dijkstra(1);

}
*/

}
