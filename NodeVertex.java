public class NodeVertex implements Comparable<NodeVertex>{
	int vertex;
	int weigth;
	public NodeVertex(int vertex,int weigth){
		this.vertex= vertex;
		this.weigth = weigth;
	}

	@Override
	public int compareTo(NodeVertex o) {
		return 0;
	}
}