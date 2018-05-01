package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Graph{
	LinkedList<NodeVertex> linkedList[];
	Point2D vertexPoint[];
	int numVertex,numVnearby,neighborV;
	int[] parent;
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
		/*This linked list acts like a Queue*/
		LinkedList<NodeVertex> pq = new LinkedList<>();
		int[] distance = new int[this.numVertex];
		parent = new int[this.numVertex];


		/*Initialize*/
		for(int i=0;i<this.numVertex;i++){
			if(i==src) {
				pq.addLast(new NodeVertex(src, 0));

				distance[src] = 0;
			}
			else {
				pq.addLast(new NodeVertex(i, 99999));
				distance[i] = 99999;
			}
			parent[i] = -1;
		}

		while(!pq.isEmpty()){

			NodeVertex u = pq.poll();
			/*Relax edges and update pq*/
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
			s= s +i+" : "+dist[i]+" m\n";
		return s;
	}

	/*Print path recursively */
	public String printPath(int src,int dest){
		String s = "";
		 if(dest == src){
			s = src+"";
			return s;
		}
		s =printPath(src,parent[dest])+"->"+dest;
		return s;

	}

	public void drawPath(int src, int dest, GraphicsContext gc){
		double x = this.vertexPoint[dest].getX();

		double y = this.vertexPoint[dest].getY();
		if(dest == src){
			x = this.vertexPoint[src].getX();
			y = this.vertexPoint[src].getY();
			gc.beginPath();
			gc.moveTo(x,y);
			return;
		}
		drawPath(src,parent[dest],gc);

		gc.setFill(Color.CORAL);
		gc.fillOval(this.vertexPoint[dest].getX(),this.vertexPoint[dest].getY(),15,15);
		gc.setFill(Color.BLACK);
		gc.fillText(this.getVertexName(dest),this.vertexPoint[dest].getX()-15,this.vertexPoint[dest].getY()-2);
		gc.fillText(dest+"/",this.vertexPoint[dest].getX()-30,this.vertexPoint[dest].getY()-2);

		gc.lineTo(x,y);
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



}
