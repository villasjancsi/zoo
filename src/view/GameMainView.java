package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Service;

public class GameMainView {
	private AnchorPane mainPane;
	
	private List<ImageView> activeImageViews = new ArrayList<>();
	
	private List<Node> menuNodes = new ArrayList<>();
	private boolean menuVisible = false;
	
	public AnchorPane infoPane;
	private Label infoLabel;
	
	private Label moneyLabel;
	private Label popularityLabel;
	private Label timeLabel;
	
	private ImageView menuButton;
	
	@FXML
	private Button button;
	
	public void setMainPane(AnchorPane pane) {
		mainPane = pane;
		//Image img = new Image(GameMainView.class.getResource("../resources/map.jpg").toString());
		mainPane.setStyle("-fx-background-image: url(" + GameMainView.class.getResource("../resources/map.jpg").toString() + ");");
	}
	
	public AnchorPane getMainPane() {
		return mainPane;
	}
	
	public List<ImageView> getActiveImageViews() {
		return activeImageViews;
	}
	
	public ImageView createMenuButton() {
		/*if(false) {
			for(int i = 0; i <= 1030; i+=50) {
				Line l = new Line();
				l.setEndX(1920);
				l.setEndY(i);
				l.setStartX(0);
				l.setStartY(i);
				mainPane.getChildren().add(l);
			}
			for(int i = 0; i <= 1870; i+=50) {
				Line l = new Line();
				l.setEndX(i);
				l.setEndY(1000);
				l.setStartX(i);
				l.setStartY(0);
				mainPane.getChildren().add(l);
			}
		}*/
		
		
		menuButton = new ImageView();
		menuButton.setFitWidth(100*Service.ratio);
		menuButton.setFitHeight(100*Service.ratio);
		AnchorPane.setBottomAnchor(menuButton, 20*Service.ratio);
		AnchorPane.setLeftAnchor(menuButton, 20*Service.ratio);
		menuButton.setImage(new Image(GameMainView.class.getResource("../resources/mainbutton.png").toString()));
		menuButton.setCursor(Cursor.HAND);
		mainPane.getChildren().add(menuButton);
		return menuButton;
	}
	
	public Map<String, Node> createMenu() {
		
		infoPane = new AnchorPane();
		infoPane.setPrefHeight(150);
		infoPane.setPrefWidth(150);
		infoPane.setStyle("-fx-background-image: url(" + GameMainView.class.getResource("../resources/info.png").toString() + ");");
		infoPane.setVisible(false);
		mainPane.getChildren().add(infoPane);
		
		infoLabel = new Label();
		infoLabel.setLayoutX(5);
		infoLabel.setLayoutY(5);
		infoLabel.setTextFill(Color.WHITE);
		infoPane.getChildren().add(infoLabel);
		
		Map<String, Node> result = new HashMap<>();
		
		ImageView iv = new ImageView();
		iv.setFitWidth(200*Service.ratio);
		iv.setFitHeight(140*Service.ratio);
		AnchorPane.setBottomAnchor(iv, 0.0);
		AnchorPane.setLeftAnchor(iv, 0.0);
		iv.setImage(new Image(GameMainView.class.getResource("../resources/menuTri.png").toString()));
		iv.setVisible(false);
		mainPane.getChildren().add(iv);
		menuNodes.add(iv);
		result.put("triangle", iv);
		
		AnchorPane ap = new AnchorPane();
		ap.setPrefSize(220*Service.ratio, 140*Service.ratio);
		AnchorPane.setBottomAnchor(ap, 0*Service.ratio);
		AnchorPane.setLeftAnchor(ap, 180*Service.ratio);
		ap.setStyle("-fx-background-image: url(" + GameMainView.class.getResource("../resources/menuTitle.png").toString() + ");");
		ap.setVisible(false);
		mainPane.getChildren().add(ap);
		menuNodes.add(ap);
		result.put("titleAnchorPane", ap);

		ScrollPane sp = new ScrollPane();
		sp.setPrefSize(700*Service.ratio, 140*Service.ratio);
		AnchorPane.setBottomAnchor(sp, 0*Service.ratio);
		AnchorPane.setLeftAnchor(sp, 400*Service.ratio);
		sp.setStyle("-fx-background-image: url(" + GameMainView.class.getResource("../resources/menuContent.png").toString() + ");");
		sp.setFitToHeight(true);
		sp.setFitToWidth(false);
		sp.setVisible(false);
		mainPane.getChildren().add(sp);
		menuNodes.add(sp);
		result.put("scrollPane", sp);

		AnchorPane spap_buildings = new AnchorPane();
		spap_buildings.setPrefSize(698*Service.ratio, 140*Service.ratio);
		spap_buildings.setStyle("-fx-background-color: a5f5ff;");
		result.put("buildingsAnchorPane", spap_buildings);

		Label lab1 = new Label();
		lab1.setText("Buildings");
		lab1.setLayoutX(25.0*Service.ratio);
		lab1.setLayoutY(40.0*Service.ratio);
		lab1.setFont(new Font(20.0*Service.ratio));
		lab1.setCursor(Cursor.HAND);
		ap.getChildren().add(lab1);
		result.put("buildingsLabel", lab1);

		AnchorPane spap_animals = new AnchorPane();
		spap_animals.setPrefSize(698*Service.ratio, 140*Service.ratio);
		spap_animals.setStyle("-fx-background-color: a5f5ff;");
		sp.setContent(spap_animals);
		result.put("animalsAnchorPane", spap_animals);
		
		Label lab2 = new Label();
		lab2.setText("Animals");
		lab2.setLayoutX(25.0*Service.ratio);
		lab2.setLayoutY(10.0*Service.ratio);
		lab2.setFont(new Font(20.0*Service.ratio));
		lab2.setCursor(Cursor.HAND);
		ap.getChildren().add(lab2);
		result.put("animalsLabel", lab2);

		AnchorPane spap_staff = new AnchorPane();
		spap_staff.setPrefSize(698*Service.ratio, 140*Service.ratio);
		spap_staff.setStyle("-fx-background-color: a5f5ff;");
		result.put("employeeAnchorPane", spap_staff);

		Label lab3 = new Label();
		lab3.setText("Staff");
		lab3.setLayoutX(25.0*Service.ratio);
		lab3.setLayoutY(70.0*Service.ratio);
		lab3.setFont(new Font(20.0*Service.ratio));
		lab3.setCursor(Cursor.HAND);
		ap.getChildren().add(lab3);
		result.put("employeeLabel", lab3);
		
		return result;
	}

	public List<ImageView> createStatus() {
		List<ImageView> ret = new ArrayList<>();
		
		AnchorPane ap = new AnchorPane();
		ap.setPrefSize(300*Service.ratio, 140*Service.ratio);
		AnchorPane.setBottomAnchor(ap, 0*Service.ratio);
		AnchorPane.setRightAnchor(ap, 50*Service.ratio);
		ap.setStyle("-fx-background-image: url(" + GameMainView.class.getResource("../resources/statusBg.png").toString() + ");");
		ap.setVisible(true);
		mainPane.getChildren().add(ap);
		
		moneyLabel = new Label();
		moneyLabel.setLayoutX(180*Service.ratio);
		moneyLabel.setLayoutY(25*Service.ratio);
		moneyLabel.setText("$0,00");
		moneyLabel.setFont(new Font(12.0*Service.ratio));
		moneyLabel.setTextFill(Color.WHITE);
		moneyLabel.autosize();
		ap.getChildren().add(moneyLabel);
		
		popularityLabel = new Label();
		popularityLabel.setLayoutX(180*Service.ratio);
		popularityLabel.setLayoutY(67*Service.ratio);
		popularityLabel.setText("0%");
		popularityLabel.setFont(new Font(12.0*Service.ratio));
		popularityLabel.autosize();
		popularityLabel.setTextFill(Color.WHITE);
		ap.getChildren().add(popularityLabel);
		
		timeLabel = new Label();
		timeLabel.setLayoutX(180*Service.ratio);
		timeLabel.setLayoutY(108*Service.ratio);
		timeLabel.setText("00:00");
		timeLabel.setFont(new Font(12.0*Service.ratio));
		timeLabel.autosize();
		timeLabel.setTextFill(Color.WHITE);
		ap.getChildren().add(timeLabel);

		ImageView playImage = new ImageView();
		playImage.setFitWidth(100);
		playImage.setFitHeight(50);
		playImage.setLayoutX(35);
		playImage.setLayoutY(20);
		playImage.setImage(new Image(GameMainView.class.getResource("../resources/playButton.png").toString()));
		ap.getChildren().add(playImage);
		ret.add(playImage);
		
		ImageView pauseImage = new ImageView();
		pauseImage.setFitWidth(100);
		pauseImage.setFitHeight(50);
		pauseImage.setLayoutX(35);
		pauseImage.setLayoutY(73);
		pauseImage.setImage(new Image(GameMainView.class.getResource("../resources/pauseButton.png").toString()));
		ap.getChildren().add(pauseImage);
		ret.add(pauseImage);
		
		return ret;
	}
	
	public void toggleMenu() {
		menuVisible = !menuVisible;
		for (Node n : menuNodes) {
			n.toFront();
			n.setVisible(menuVisible);
		}
	}
	
	public ImageView createAnimalType(String type, String pictureUrl, double width, double height, double x, double y) {
		ImageView iv = new ImageView();
		iv.setId(type+";new");
		iv.setFitWidth(60*Service.ratio);
		iv.setFitHeight(60*Service.ratio);
		if(width < 60) {
			iv.setScaleX(width/60);
			iv.setScaleY(width/60);
		}
		iv.setLayoutX(x*Service.ratio);
		iv.setLayoutY(y*Service.ratio);
		iv.setImage(new Image(pictureUrl));
		iv.setCursor(Cursor.OPEN_HAND);
		return iv;
	}
	
	public ImageView createBuildingType(String type, String pictureUrl, double x, double y) {
		ImageView iv = new ImageView();
		iv.setId(type);
		iv.setFitWidth(150*Service.ratio);
		iv.setFitHeight(75*Service.ratio);
		iv.setLayoutX(x*Service.ratio);
		iv.setLayoutY(y*Service.ratio);
		iv.setImage(new Image(pictureUrl));
		iv.setCursor(Cursor.OPEN_HAND);
		return iv;
	}
	
	public ImageView createEmployeeType(String type, String pictureUrl, double x, double y) {
		ImageView iv = new ImageView();
		iv.setId(type);
		iv.setFitWidth(80*Service.ratio);
		iv.setFitHeight(80*Service.ratio);
		iv.setLayoutX(x*Service.ratio);
		iv.setLayoutY(y*Service.ratio);
		iv.setImage(new Image(pictureUrl));
		iv.setCursor(Cursor.OPEN_HAND);
		return iv;
	}
	
	public Node createLabel(String text, double x, double y) {
		Label l = new Label();
		l.setText(text);
		l.setLayoutX((x+10)*Service.ratio);
		l.setLayoutY((y+80)*Service.ratio);
		l.setFont(new Font(14.0*Service.ratio));
		l.autosize();
		return l;
	}
	
	public void updateMoneyLabel(double value) {
		moneyLabel.setText(Service.moneyFormatter(value));
	}
	
	public void updatePopularityLabel(double value) {
		StringBuilder sb = new StringBuilder();
		sb.append(Service.numberFormatter(value*100));
		sb.append("%");
		popularityLabel.setText(sb.toString());		
	}
	
	public void updateTimeLabel(String time) {
		timeLabel.setText(time);
	}
	
	public void showInfo(String text, double x, double y) {
		infoPane.toFront();
		infoPane.setLayoutX(x);
		infoPane.setLayoutY(y);
		infoPane.setVisible(true);
		infoLabel.setText(text);
	}
	
	public void hideInfo() {
		infoPane.setVisible(false);
	}
	
	public ImageView addVisitor(int type, double x, double y, double width, double height) {
		ImageView imageView = new ImageView();
		imageView.setId(Integer.toString(type));
		imageView.setLayoutX(x);
		imageView.setLayoutY(y);
		imageView.setFitWidth(width*Service.ratio);
		imageView.setFitHeight(height*Service.ratio);
		imageView.setImage(new Image(GameMainView.class.getResource("../resources/person"+type+".png").toString()));
		imageView.toBack();
		mainPane.getChildren().add(imageView);
		activeImageViews.add(imageView);
		return imageView;
	}

	public void removeImageView(ImageView imageView) {
		mainPane.getChildren().remove(imageView);
		activeImageViews.remove(imageView);
	}
	
	public ImageView addAnimal(String id, String pictureUrl, double width, double height, double x, double y) {
		ImageView iv = new ImageView();
		iv.setId(id+";old");
		iv.setLayoutX(x);
		iv.setLayoutY(y);
		iv.setFitWidth(width*Service.ratio);
		iv.setFitHeight(height*Service.ratio);
		iv.setImage(new Image(pictureUrl));
		activeImageViews.add(iv);
		mainPane.getChildren().add(iv);
		return iv;
	}
	
	public ImageView addBuilding(String id, String pictureUrl,double width, double height, double x, double y) {
		ImageView iv = new ImageView();
		iv.setId(id);
		iv.setLayoutX(x);
		iv.setLayoutY(y);
		iv.setFitWidth(width*Service.ratio);
		iv.setFitHeight(height*Service.ratio);
		iv.setImage(new Image(pictureUrl));
		activeImageViews.add(iv);
		mainPane.getChildren().add(iv);
		menuButton.toFront();
		return iv;
	}	
}
