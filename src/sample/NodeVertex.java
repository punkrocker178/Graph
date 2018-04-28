package sample;


public class NodeVertex implements Comparable<NodeVertex>{
		int vertex;
		int weight;
		public NodeVertex(int vertex,int weigth){
			this.vertex= vertex;
			this.weight = weigth;
		}

		@Override
		public int compareTo(NodeVertex o) {
			return 0;
		}
}

