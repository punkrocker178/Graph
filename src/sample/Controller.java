package sample;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class Controller implements Initializable {
	@FXML
	public Canvas canvas;

	public Pane pane;

	public ImageView img;

	@FXML
	public StackPane stack;

	@FXML
	public Button btn1,btn2,btn3;

	@FXML
	public ScrollPane sp;

	@FXML
	public TextArea txt1;

	@FXML
	public ComboBox combo1,combo2;

	public TextInputDialog dialog;

	final int witdh = 1920;
	final int height = 1080;




	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Graph graph = new Graph("Graph.txt");

//		combo1.getItems().addAll("Vong Xoay CMT8",
//				"CineBox",
//				"DBP",
//				"CMT8",
//				"Phong Vu",
//				"Cong vien Tao Dan");
		for(int i=0;i<graph.numVertex;i++){
			combo1.getItems().add(graph.vertexName[i]);
		}

		combo2.getItems().addAll(combo1.getItems());
		combo1.setEditable(true);
		combo2.setEditable(true);

		Image image= new Image("file:Map\\2.png");
		img.setImage(image);
		sp.setContent(stack);
		GraphicsContext gc = canvas.getGraphicsContext2D();
//		GraphicsContext gc2 = canvas2.getGraphicsContext2D();
//		gc.drawImage(img,0,0,witdh,height);


////		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
////		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//		sp.setFitToHeight(true); //center
//		sp.setFitToWidth(true); //center

		final int[] opt = new int[2];
		btn1.setOnAction(bt1 ->{
			for(int i=0;i<graph.numVertex;i++){
				if(combo1.getValue().equals(graph.vertexName[i])){
					opt[0] = i;
				}
			}
			for(int j=0;j<graph.numVertex;j++){
				if(combo2.getValue().equals(graph.vertexName[j])){
					opt[1] = j;
				}
			}
			fromTo(graph,opt[0],opt[1],gc);

		});

		/*Draw Graph */
		btn3.setOnAction( bt2 ->{
			drawGraph(gc,graph);
		});


		/*Dijkstra button*/
		btn2.setOnAction( bt3 ->{
			dialog = new TextInputDialog();
			dialog.setTitle("Input source");
			dialog.setContentText("Please enter source to find shortest path to all vertices");

			dialog.setHeaderText(null);
			Optional<String> result = dialog.showAndWait();
			gc.beginPath();
			try{
				if(result.isPresent()){
					Integer n = Integer.parseInt(result.get());
					int[] distance ;
					distance = graph.findDijkstra(n);
					gc.moveTo(graph.VertexGetX(n),graph.VertexGetY(n));
					String s = "Shortest path from source: "+n+" to all vertices\n";
					txt1.setText(s+graph.printSolution(distance));

				}

			}catch(NullPointerException error){
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Cannot find vertext");
				alert.setContentText("Vertext not in graph");
				alert.showAndWait();
				error.printStackTrace();
			}catch(ArrayIndexOutOfBoundsException error2){
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Cannot find vertext");
				alert.setContentText("Vertext not in graph");
				alert.showAndWait();
				error2.printStackTrace();
			}

		});

//		canvas.setOnMouseClicked(e -> {
//
//			e.MOUSE_DRAGGED
////			gc.setFill(Color.ORANGE);
////			gc.fillOval(e.getX(),e.getY(),10,10);
////			pointList.add(point2d.add(e.getX(),e.getY()));
//			System.out.println(e.getX()+" "+e.getY());
//			txt1.setText(e.getX()+"");
////			txt2.setText(e.getY()+"");
//
//
//		});

	}
	public void drawGraph(GraphicsContext gc,Graph graph){
		for(int i=0;i< graph.getVertex();i++){
			gc.setFill(Color.CORAL);
			gc.fillOval(graph.VertexGetX(i),graph.VertexGetY(i),15,15);
			gc.setFill(Color.BLACK);
			gc.fillText(graph.getVertexName(i),graph.VertexGetX(i)-20,graph.VertexGetY(i)-5);
			gc.fillText(i+"/",graph.VertexGetX(i)-30,graph.VertexGetY(i)-5);

		}
	}


	/*Show path of the source to destination
	* Show each vertex and lines */
	public void fromTo(Graph graph,int src,int destination,GraphicsContext gc){

		/*Show on mini text area*/
		int[] distance = graph.findDijkstra(src);
		txt1.setText("Distance from source:\n"+combo1.getValue()+" to "+combo2.getValue()+" is "+distance[destination]+" m\n"
				+graph.printPath(src,destination));

		/*Clear old vertex and line each time */
		gc.clearRect(0,0,1920,1080);
		gc.setStroke(Color.TURQUOISE);
		/*Draw the source vertex and then draw the rest
		* I know this code is cheesy
		* SHOULD BE OPTIMIZED IN THE FUTURE*/
		gc.setFill(Color.CORAL);
		gc.fillOval(graph.VertexGetX(src),graph.VertexGetY(src),15,15);
		gc.setFill(Color.BLACK);
		gc.fillText(graph.getVertexName(src),graph.VertexGetX(src)-20,graph.VertexGetY(src)-5);
		gc.fillText(src+"/",graph.VertexGetX(src)-30,graph.VertexGetY(src)-5);

		/*Draw vertex and lines connected to*/
		graph.drawPath(src,destination,gc);
		gc.setLineWidth(2);
		gc.stroke();
		gc.closePath();
	}


}
