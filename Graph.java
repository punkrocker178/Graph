import javafx.geometry.Point2D;
import org.w3c.dom.Node;

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
	String[] vertexName;


	public Graph(String fileName){
		try{
			/*Initialize*/
		
		BufferedReader reader = new BufferedReader(new FileReader(fileName));

		this.numVertex = Integer.parseInt(reader.readLine());
		linkedList = new LinkedList[numVertex];
		vertexPoint = new Point2D[numVertex];
		vertexName = new String[numVertex];

		// numVertex = 10;

		for(int i=0;i<numVertex ;i++ ) {
			linkedList[i] = new LinkedList<NodeVertex>();
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
				System.out.print(" -> ("+node.vertex+","+node.weigth+")");
			}
			System.out.println();
		}
	}

	public int[] findDijkstra(int src){
        PriorityQueue<NodeVertex> pq = new PriorityQueue<>(this.numVertex);

        int[] distance = new int[this.numVertex];
        int[] parent = new int[this.numVertex];


        /*Initialize*/
        for(int i=0;i<this.numVertex;i++){
            if(i==0)
                pq.add(new NodeVertex(0,0));
            else
                pq.add(new NodeVertex(i,999));
            distance[i] = 999;
        }
        parent[src] = -1;
        distance[src] = 0;

        while(!pq.isEmpty()){
                int i=0;
                NodeVertex u = pq.poll();

                for(NodeVertex v: this.linkedList[u.vertex] ){
                    if(distance[v.vertex] > distance[u.vertex] + v.weigth){
                        distance[v.vertex] = distance[u.vertex] + v.weigth;
                        parent[v.vertex] = u.vertex;
                    }
//                    pq.add(v);

            }
        }
        return distance;
    }

    public void printSolution(int[] distance){

        for (int i = 0; i < this.numVertex; i++)
           System.out.println(i+" : "+distance[i]);
    }


	public static void main(String[] args){
		Graph graph =  new Graph("Graph.txt");
		graph.print();
		graph.printSolution(graph.findDijkstra(0));

	}
}
