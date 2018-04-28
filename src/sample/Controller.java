package sample;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class Controller implements Initializable {
	@FXML
	public Canvas canvas;

	@FXML
	public Button btn1,btn2,btn3;

	@FXML
	public ScrollPane sp;

	@FXML
	public TextArea txt1;

	@FXML
	public ComboBox combo1,combo2;

	public TextInputDialog dialog;




	@Override
	public void initialize(URL location, ResourceBundle resources) {

//		Point2D point2d = new Point2D(0,0);
//		ArrayList<Point2D> pointList = new ArrayList<Point2D>();
		Graph graph = new Graph("Graph.txt");

		combo1.getItems().addAll("Vong Xoay CMT8","CineBox","DBP","CMT8","Phong Vu","Cong vien Tao Dan");
		combo2.getItems().addAll(combo1.getItems());
		combo1.setEditable(true);
		combo2.setEditable(true);




		Image img = new Image("file:Map\\2.png");
		GraphicsContext gc = canvas.getGraphicsContext2D();
		GraphicsContext gc2 = canvas.getGraphicsContext2D();
		gc.drawImage(img,0,0,1920,1080);

		sp.setContent(canvas);
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		sp.setFitToHeight(true); //center
		sp.setFitToWidth(true); //center

		btn3.setOnAction( bt2 ->{
			for(int i=0;i< graph.getVertex();i++){

				gc.setFill(Color.ORANGE);
				gc.fillOval(graph.VertexGetX(i),graph.VertexGetY(i),15,15);
				gc.strokeText(graph.getVertexName(i),graph.VertexGetX(i)-20,graph.VertexGetY(i)-5);
				gc.strokeText(i+"/",graph.VertexGetX(i)-30,graph.VertexGetY(i)-5);

			}
		});

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
			}

		});


//		canvas.setOnMouseClicked(e -> {
//
////			gc.setFill(Color.ORANGE);
////			gc.fillOval(e.getX(),e.getY(),10,10);
//			pointList.add(point2d.add(e.getX(),e.getY()));
//			System.out.println(e.getX()+" "+e.getY());
//			txt1.setText(e.getX()+"");
//			txt2.setText(e.getY()+"");
//
//
//		});









	}
}
