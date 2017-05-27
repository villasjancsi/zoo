package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import model.*;

public class MainController {
	public Stage primaryStage;
	public BorderPane rootView;
	public GameMainView gameMainView;
	
	private Map<ImageView, Building> buildings = new HashMap<>();
	private Map<Animal, ImageView> animals = new HashMap<>();
	private List<ImageView> visitors = new ArrayList<>();
	
	
	@SuppressWarnings("deprecation")
	public void initialize(List<AnimalType> animalTypes, List<BuildingType> buildingTypes, List<EmployeeType> employeeTypes, List<Building> buildings) {
		gameMainView.createMenuButton().setOnMouseClicked(e -> handleButton(e));
		Map<String, Node> menuComponents = gameMainView.createMenu();

		/*
		 * ANIMAL TYPES
		*/
		double x = 10.0;
		for(AnimalType at : animalTypes) {
			ImageView animalImageView = gameMainView.createAnimalType(at.getName(), at.getPictureUrl(), at.getWidth(), at.getHeight(), x, 10.0);
			animalImageView.setOnDragDetected(e -> handleDragDetection(e, animalImageView));
			((AnchorPane)(menuComponents.get("animalsAnchorPane"))).getChildren().add(animalImageView);
			((AnchorPane)(menuComponents.get("animalsAnchorPane"))).getChildren().add(gameMainView.createLabel(Service.moneyFormatter(at.getPrice()), x, 10.0));
			animalImageView.setOnMouseEntered(event -> handleAnimalOnMouseHover(event, at));
			animalImageView.setOnMouseExited(event -> handleOnMouseLeave(event));
			x += 100.0;
		}

		
		/*
		 * BUILDING TYPES
		*/
		x = 10.0;
		for(BuildingType bt : buildingTypes) {
			ImageView buildingImageView = gameMainView.createBuildingType(bt.getName(), bt.getPictureUrl(), x, 10.0);
			buildingImageView.setOnDragDetected(e -> handleDragDetection(e, buildingImageView));
			((AnchorPane)(menuComponents.get("buildingsAnchorPane"))).getChildren().add(buildingImageView);
			((AnchorPane)(menuComponents.get("buildingsAnchorPane"))).getChildren().add(gameMainView.createLabel(Service.moneyFormatter(bt.getPrice()), x, 10.0));
			buildingImageView.setOnMouseEntered(event -> handleBuildingOnMouseHover(event, bt));
			buildingImageView.setOnMouseExited(event -> handleOnMouseLeave(event));
			x += 160.0;
		}
		
		
		/*
		 * EMPLOYEE TYPES
		*/
		x = 10.0;
		for(EmployeeType et : employeeTypes) {
			ImageView employeeImageView = gameMainView.createEmployeeType(et.getName(), et.getPictureUrl(), x, 10.0);
			employeeImageView.setOnDragDetected(e -> handleDragDetection(e, employeeImageView));
			((AnchorPane)(menuComponents.get("employeeAnchorPane"))).getChildren().add(employeeImageView);
			((AnchorPane)(menuComponents.get("employeeAnchorPane"))).getChildren().add(gameMainView.createLabel(Service.moneyFormatter(et.getPrice()), x, 10.0));
			employeeImageView.setOnMouseEntered(event -> handleEmployeeOnMouseHover(event, et));
			employeeImageView.setOnMouseExited(event -> handleOnMouseLeave(event));
			x += 100.0;
		}
		
		((Label)menuComponents.get("animalsLabel")).setOnMouseClicked(e -> changeMenuTab(e, (ScrollPane)menuComponents.get("scrollPane"), (AnchorPane)menuComponents.get("animalsAnchorPane")));
		((Label)menuComponents.get("buildingsLabel")).setOnMouseClicked(e -> changeMenuTab(e, (ScrollPane)menuComponents.get("scrollPane"), (AnchorPane)menuComponents.get("buildingsAnchorPane")));
		((Label)menuComponents.get("employeeLabel")).setOnMouseClicked(e -> changeMenuTab(e, (ScrollPane)menuComponents.get("scrollPane"), (AnchorPane)menuComponents.get("employeeAnchorPane")));
		
		
		List<ImageView> statusImageViews = gameMainView.createStatus();
		statusImageViews.get(0).setOnMouseClicked(event -> Service.getZooManagementThread().resume());
		statusImageViews.get(1).setOnMouseClicked(event -> Service.getZooManagementThread().suspend());
		
		gameMainView.updateMoneyLabel(Service.getZoo().getMoney());
		
		gameMainView.getMainPane().setOnDragOver(e -> handleBuildingDragOver(e));
		gameMainView.getMainPane().setOnDragDropped(e -> handleBuildingDragDrop(e));
		primaryStage.setOnCloseRequest(e -> handleClose(e));
		
		/*
		 * BUILDINGS with ANIMALS
		 */
		for(Building building : buildings) {
			addBuildingToGUI(building.getType(), building);
			for(Animal animal : building.getAnimals()) {
				ImageView animalView = addAnimalToGUI(animal.getType(), building.getType(), building);
				animals.put(animal, animalView);
				animalView.setOnMouseEntered(event -> handleExistAnimalOnMouseHover(event, animal));
				animalView.setOnMouseExited(event -> handleOnMouseLeave(event));
			}
		}
	}
	
	
	
	/*
	 * MENU MANAGEMENT
	 */

	public void createAnimalType(AnimalType animalType, double x, double y) {
		ImageView iv = gameMainView.createAnimalType(
				animalType.getName(), 
				animalType.getPictureUrl(),
				animalType.getWidth(),
				animalType.getHeight(), 
				x,
				y);
		iv.setOnDragDetected(e -> handleDragDetection(e, iv));
	}
	
	public void createBuildingType(BuildingType buildingType, double x, double y) {
		ImageView iv = gameMainView.createBuildingType(
				buildingType.getName(), 
				buildingType.getPictureUrl(), 
				x, 
				y);
		iv.setOnDragDetected(e -> handleDragDetection(e, iv));
	}
	
	public void createEmployeeType(EmployeeType employeeType, double x, double y) {
		ImageView iv = gameMainView.createEmployeeType(
				employeeType.getName(), 
				employeeType.getPictureUrl(), 
				x, 
				y);
		iv.setOnDragDetected(e -> handleDragDetection(e, iv));
	}
	
	public void updateTime(String time) {
		gameMainView.updateTimeLabel(time);
	}
	
	
	
	/*
	 * ZOO MANAGEMENT
	 */
	
	public void addBuildingToGUI(BuildingType buildingType, Building building) {
		ImageView buildingView = gameMainView.addBuilding(
				buildingType.getName(),
				buildingType.getPictureUrl(),
				buildingType.getWidth(),
				buildingType.getHeight(),
				building.getLayoutX(),
				building.getLayoutY());
		buildingView.setOnDragOver(event -> handleAnimalDragOver(event));
		buildingView.setOnDragDropped(event -> handleAnimalDragDrop(event, (ImageView)event.getSource()));
		buildingAnimation(buildingView);
		buildings.put(buildingView, building);
	}
	
	public void addBuilding(String type, double x, double y) {
		BuildingType buildingType = Service.getBuildingType(type);
		Zoo zoo = Service.getZoo();
		
		//Check whether the user has enough money
		if(!zoo.hasEnoughMoney(buildingType.getPrice()))
			return;
		
		//Add new instance of building to the zoo
		Building building = new Building(buildingType, x, y);
		zoo.addBuilding(building);
		
		addBuildingToGUI(buildingType, building);
		
		gameMainView.updateMoneyLabel(zoo.decreaseMoney(buildingType.getPrice()));
		gameMainView.updatePopularityLabel(zoo.getPopularity());
	}
	
	public ImageView addAnimalToGUI(AnimalType animalType, BuildingType buildingType, Building building) {
		double x = building.getPosX();
		if(animalType.getWidth() == 110.0)
			x -= 30;
		if(animalType.getWidth() == 80.0)
			x -= 15;
		ImageView animalView = gameMainView.addAnimal(
				animalType.getName(),
				animalType.getPictureUrl(),
				animalType.getWidth(),
				animalType.getHeight(),
				x*Service.ratio, 
				building.getLayoutY()+(buildingType.getHeight()/2 - animalType.getHeight()/2));

		animalAnimation(animalView);
		building.setPosX(x + 30 + animalType.getWidth());
		return animalView;
	}
	
	public void addAnimal(Building building, String type, boolean isNew) {
		AnimalType animalType = Service.getAnimalType(type);
		BuildingType buildingType = building.getType();
		Zoo zoo = Service.getZoo();
		
		//Check whether the user has enough money
		if(!zoo.hasEnoughMoney(animalType.getPrice()))
			return;
		//Check whether the building can contain the animal
		if(!buildingType.canAcceptAnimal(animalType))
			return;
		//Check whether the building has free space
		if(building.getAnimalCount() >= buildingType.getMaxAnimals())
			return;
		
		//Add the new instance of Animal to building 
		Animal animal = new Animal(animalType);
		building.addAnimal(animal);
		
		//Add animal to the GUI
		ImageView animalView = addAnimalToGUI(animalType, buildingType, building);
		animals.put(animal, animalView);
		animalView.setOnMouseEntered(event -> handleExistAnimalOnMouseHover(event, animal));
		animalView.setOnMouseExited(event -> handleOnMouseLeave(event));
		
		//Refresh GUI
		if(isNew) {
			gameMainView.updateMoneyLabel(zoo.decreaseMoney(animalType.getPrice()));
			gameMainView.updatePopularityLabel(zoo.getPopularity());
		}
	}
	
	public void addEmployee(String type, double x, double y) {
		Zoo zoo = Service.getZoo();
		EmployeeType employeeType = Service.getEmployeeType(type);
		if(!zoo.hasEnoughMoney(employeeType.getPrice()))
			return;
		Employee employee = new Employee(employeeType, 100);
		Service.getZoo().hireEmployee(employee);
		gameMainView.updateMoneyLabel(zoo.decreaseMoney(employeeType.getPrice()));
	}
	
	public void deadAnimal(Building building, Animal animal) {
		for(Animal cAnimal : building.getAnimals()) {
			ImageView animalView = animals.get(cAnimal);
			gameMainView.removeImageView(animalView);
			animals.remove(cAnimal);
		}
		building.setPosX(building.getLayoutX() + building.getType().getStartX());
		for(Animal ani : building.getAnimals()) {
			if(ani != animal) {
				ImageView animalImageView = addAnimalToGUI(ani.getType(), building.getType(), building); 
				animals.put(ani, animalImageView);
				animalImageView.setOnMouseEntered(event -> handleExistAnimalOnMouseHover(event, ani));
				animalImageView.setOnMouseExited(event -> handleOnMouseLeave(event));
			}
		}
	}
	
	public void updatePopulationDensity(double density) {
		Zoo zoo = Service.getZoo();
		//double max = density*50*zoo.getPopularity()*20;
		double popularity = zoo.getPopularity();
		double max = popularity*50;
		if(Double.compare(density, 0.0) == 0) {
			//visitors.clear();
			//zoo.clearVisitors();
		//} else if (visitors.size() < density*50*zoo.getPopularity()*5) {
		} else {
			Random r = new Random();
			//max -= visitors.size();
			for(int i = 0; i <= max; i++) {
				double money = (0.1+r.nextDouble())*27.5*zoo.getMaxValue()/5.0;
				if(money < zoo.getTicketPrice())
					continue;
				money -= zoo.getTicketPrice();
				double x, y;
				x = 20.0;
				y = 200.0;
				/*
				do {
					x = (70+r.nextDouble()*1780)*Service.ratio;
					y = (100+r.nextDouble()*680)*Service.ratio;
				} while(checkCollision(false, x, y, 35, 70, 0, 0, -50, 0));*/
				
				
				ImageView visitorView = gameMainView.addVisitor(r.nextInt(3), x+((r.nextInt(3)-1)*r.nextInt(30)), y+((r.nextInt(3)-1)*r.nextInt(30)), 35, 70);
				visitorAnimation(visitorView, r.nextInt(3), r.nextInt(1500));
				visitors.add(visitorView);
				
				zoo.addVisitor(new Visitor(money));
				//Every new visitor pays for the ticket
				zoo.increaseMoney(zoo.getTicketPrice());
			}
		}
		visitors.stream().sorted((c1, c2) -> Double.compare(c1.getLayoutY(), c2.getLayoutY())).forEach(ImageView::toFront);
		gameMainView.infoPane.toFront();
		
	}
	
	/*
	 * GUI MANAGEMENT
	 */
	
	public void updateGUI() {
		Zoo zoo = Service.getZoo();
		gameMainView.updateMoneyLabel(zoo.getMoney());
		gameMainView.updatePopularityLabel(zoo.getPopularity());
	}
	
	public boolean checkCollision(boolean isBuilding, double x, double y, double width, double height, double marginTop, double marginRight, double marginBottom, double marginLeft) {
		for(ImageView iv : gameMainView.getActiveImageViews()) {
			if(isBuilding && !iv.getId().startsWith("B_"))
				continue;
			//left top
			if((iv.getLayoutX()-marginLeft <= x && iv.getLayoutX()+iv.getFitWidth()+marginRight >= x) && 
					(iv.getLayoutY()-marginTop <= y && iv.getLayoutY()+iv.getFitHeight()+marginBottom >= y))
				return true;
			//right top
			if((iv.getLayoutX()-marginLeft <= x+width && iv.getLayoutX()+iv.getFitWidth()+marginRight >= x+width) && 
					(iv.getLayoutY()-marginTop <= y && iv.getLayoutY()+iv.getFitHeight()+marginBottom >= y))
				return true;
			//left bottom
			if((iv.getLayoutX()-marginLeft <= x && iv.getLayoutX()+iv.getFitWidth()+marginRight >= x) && 
					(iv.getLayoutY()-marginTop <= y+height && iv.getLayoutY()+iv.getFitHeight()+marginBottom >= y+height))
				return true;
			//right bottom
			if((iv.getLayoutX()-marginLeft <= x+width && iv.getLayoutX()+iv.getFitWidth()+marginRight >= x+width) && 
					(iv.getLayoutY()-marginTop <= y+height && iv.getLayoutY()+iv.getFitHeight()+marginBottom >= y+height))
				return true;
		}
		return false;
	}
	
	public void visitorAnimation(ImageView visitorView, int speed, int delay) {
		FadeTransition animation0 = new FadeTransition(Duration.millis(speed*500), visitorView);
		animation0.setDelay(Duration.millis(delay));
		animation0.setFromValue(0.0);
		animation0.setToValue(1.0);
		
		double x = visitorView.getLayoutX();
		double y = visitorView.getLayoutY();
		double time = speed*500+delay;
		Random random = new Random();
		List<TranslateTransition> animations = new ArrayList<>();
		
		TranslateTransition animation;
		animation = new TranslateTransition(Duration.millis(speed*1000), visitorView);
		animation.setToX(460);
		animations.add(animation);
		x=460;
		
		if(random.nextBoolean()) {
			animation = new TranslateTransition(Duration.millis(speed*700), visitorView);
			animation.setToY(-150);
			animations.add(animation);
			y=-150;
			
			animation = new TranslateTransition(Duration.millis(speed*700), visitorView);
			animation.setFromY(y);
			animation.setToY(0);
			animations.add(animation);
			y=0;
		}
		
		if(random.nextBoolean()) {
			animation = new TranslateTransition(Duration.millis(speed*1000), visitorView);
			animation.setFromX(x);
			animation.setToX(940);
			animations.add(animation);
			x=940;
			if(random.nextBoolean()) {
				animation = new TranslateTransition(Duration.millis(speed*700), visitorView);
				animation.setToY(-150);
				animations.add(animation);
				y=-150;
				
				animation = new TranslateTransition(Duration.millis(speed*700), visitorView);
				animation.setFromY(y);
				animation.setToY(0);
				animations.add(animation);
				y=0;
			}
		}
		
		if(random.nextBoolean()) {
			animation = new TranslateTransition(Duration.millis(speed*2000), visitorView);
			animation.setFromX(x);
			animation.setToX(1420);
			animations.add(animation);
			x=1420;
			if(random.nextBoolean()) {
				animation = new TranslateTransition(Duration.millis(speed*700), visitorView);
				animation.setToY(-150);
				animations.add(animation);
				y=-150;
				
				animation = new TranslateTransition(Duration.millis(speed*700), visitorView);
				animation.setFromY(y);
				animation.setToY(0);
				animations.add(animation);
				y=0;
			}
			
			if(random.nextBoolean()) {
				animation = new TranslateTransition(Duration.millis(speed*1500), visitorView);
				animation.setFromX(x);
				animation.setToX(1700);
				animations.add(animation);
				x=1700;
				
				animation = new TranslateTransition(Duration.millis(speed*1500), visitorView);
				animation.setFromX(x);
				animation.setToX(1420);
				animations.add(animation);
				x=1420;
			}
		}

		animation = new TranslateTransition(Duration.millis(speed*1000), visitorView);
		animation.setToY(270);
		animations.add(animation);
		y=270;
		
		if(random.nextBoolean()) {
			animation = new TranslateTransition(Duration.millis(speed*700), visitorView);
			animation.setFromY(y);
			animation.setToY(540);
			animations.add(animation);
			y=540;
		}
		
		if(x == 460) {
			if(y == 540 || y == 270) {
				animation = new TranslateTransition(Duration.millis(speed*1500), visitorView);
				animation.setFromX(x);
				animation.setToX(100);
				animations.add(animation);
				x=100;
				
				animation = new TranslateTransition(Duration.millis(speed*1500), visitorView);
				animation.setFromX(x);
				animation.setToX(460);
				animations.add(animation);
				x=460;
			}
		}
		
		if(y == 540) {
			animation = new TranslateTransition(Duration.millis(speed*2000), visitorView);
			animation.setFromX(x);
			animation.setToX(1420);
			animations.add(animation);
			x=1420;
			
			if(random.nextBoolean()) {
				animation = new TranslateTransition(Duration.millis(speed*1000), visitorView);
				animation.setFromX(x);
				animation.setToX(1700);
				animations.add(animation);
				x=1700;
				
				animation = new TranslateTransition(Duration.millis(speed*1000), visitorView);
				animation.setFromX(x);
				animation.setToX(1420);
				animations.add(animation);
				x=1420;
			}
			animation = new TranslateTransition(Duration.millis(speed*1000), visitorView);
			animation.setFromY(y);
			animation.setToY(270);
			animations.add(animation);
			y=270;
			
		}
		animation = new TranslateTransition(Duration.millis(speed*(1830-x)*2), visitorView);
		animation.setFromX(x);
		animation.setToX(1830);
		animation.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				visitorCleanUp(visitorView);
			}
		});
		animations.add(animation);
		x=1850;
		
		x = visitorView.getLayoutX();
		y = visitorView.getLayoutY();

		animation0.play();
		for(TranslateTransition ani : animations) {
			ani.setDelay(Duration.millis(time));
			time += ani.getDuration().toMillis();
			ani.play();
		}
	}
	
	public void visitorCleanUp(ImageView visitorView) {
		gameMainView.removeImageView(visitorView);
		visitors.remove(visitorView);
		if(Service.getZoo().getVisitors().size() > 0)
			Service.getZoo().removeVisitor(0);
	}
	
	public void buildingAnimation(ImageView buildingView) {
		FadeTransition animation = new FadeTransition(Duration.millis(500), buildingView);
		animation.setFromValue(0.0);
		animation.setToValue(1.0);
		animation.play();
	}
	
	public void animalAnimation(ImageView animalView) {
		FadeTransition animation = new FadeTransition(Duration.millis(500), animalView);
		animation.setFromValue(0.0);
		animation.setToValue(1.0);
		animation.play();
	}
	
	/*
	 * EVENT HANDLING
	 */
	
	private void handleClose(WindowEvent event) {
		Service.close();
	}
	
	private void handleButton(Event e) {
		MouseEvent me = (MouseEvent)e;
		if (!(me.getButton().equals(MouseButton.PRIMARY)))
			return;
		gameMainView.toggleMenu();
	}
	
	private void handleExistAnimalOnMouseHover(MouseEvent event, Animal animal) {
		double x = event.getScreenX()+20;
		double y = event.getScreenY()-200;
		if(y <= 100.0)
			y += 200;
		StringBuilder sb = new StringBuilder();
		sb.append("Fajta: ");
		sb.append(animal.getType().getName());
		sb.append("\nÉrtékkategória: ");
		sb.append(animal.getType().getValue());
		sb.append("\nÓránkénti költség: $");
		sb.append(animal.getType().getHourlyCost());
		sb.append("\nEgészség: ");
		sb.append(Service.numberFormatter(animal.getHealth()));
		sb.append("%");
		sb.append("\nÉletkor: ");
		sb.append(Service.numberFormatter(animal.getAge()/(double)(animal.getType().getMaxAge())*100));
		sb.append("%");
		gameMainView.showInfo(sb.toString(), x, y);
	}

	private void handleAnimalOnMouseHover(MouseEvent event, AnimalType animalType) {
		double x = event.getScreenX()+20;
		double y = event.getScreenY()-200;
		StringBuilder sb = new StringBuilder();
		sb.append("Név: ");
		sb.append(animalType.getName());
		sb.append("\nÉrtékkategória: ");
		sb.append(animalType.getValue());
		sb.append("\nÓránkénti költség: $");
		sb.append(animalType.getHourlyCost());
		gameMainView.showInfo(sb.toString(), x, y);
	}
	
	private void handleBuildingOnMouseHover(MouseEvent event, BuildingType buildingType) {
		double x = event.getScreenX()+20;
		double y = event.getScreenY()-200;
		StringBuilder sb = new StringBuilder();
		sb.append("Név: ");
		sb.append(buildingType.getName());
		sb.append("\nFérõhely: ");
		sb.append(buildingType.getMaxAnimals());
		sb.append(" db\nElfogadott állatok: ");
		for(String str : buildingType.getValidAnimalTypes()) {
			sb.append("\n - ");
			sb.append(str);
		}
		gameMainView.showInfo(sb.toString(), x, y);
		
	}
	
	private void handleEmployeeOnMouseHover(MouseEvent event, EmployeeType employeeType) {
		double x = event.getScreenX()+20;
		double y = event.getScreenY()-200;
		StringBuilder sb = new StringBuilder();
		sb.append("Név: ");
		sb.append(employeeType.getName());
		sb.append("\n");
		sb.append(employeeType.getValue());
		sb.append(". szintû állatok kezelése\nÓránkénti költség: $");
		sb.append(employeeType.getHourlyCost());
		sb.append("\nKezelhetõ állatok: 5/ó");
		gameMainView.showInfo(sb.toString(), x, y);
		
	}

	private void handleOnMouseLeave(MouseEvent event) {
		gameMainView.hideInfo();
	}
	
	public void changeMenuTab(MouseEvent event, ScrollPane scrollPane, AnchorPane anchorPane) {
		scrollPane.setContent(anchorPane);
	}
	
	private void handleDragDetection(MouseEvent event, ImageView imageView) {
		if(!event.isPrimaryButtonDown())
			return;
		Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
		
		ClipboardContent cb = new ClipboardContent();
		cb.putString(imageView.getId());
		
		db.setContent(cb);
		db.setDragView(imageView.getImage());
				
		event.consume();
		gameMainView.toggleMenu();
	}
	
	private void handleAnimalDragOver(DragEvent event) {
		if(event.getDragboard().hasString() && !event.getDragboard().getString().startsWith("B_"))
			event.acceptTransferModes(TransferMode.ANY);
	}
	
	private void handleAnimalDragDrop(DragEvent event, ImageView imageView) {
		String[] str = event.getDragboard().getString().split(";");
		boolean isNew = str[1].compareTo("new") == 0;
		if(str[0].startsWith("B_")) {
			event.consume();
			return;
		}
		Building building = buildings.get((ImageView)event.getTarget());
		addAnimal(building, str[0], isNew);
		
	}
	
	private void handleBuildingDragOver(DragEvent event) {
		if(event.getTarget() != gameMainView.getMainPane())
			return;
		if(event.getDragboard().hasString() && event.getDragboard().getString().startsWith("B_") || event.getDragboard().getString().startsWith("E_"))
			event.acceptTransferModes(TransferMode.ANY);
	}
	
	private void handleBuildingDragDrop(DragEvent event) {
		String str = event.getDragboard().getString();
		double x = event.getX();
		double y = event.getY();
		if(str.startsWith("E_")) {
			addEmployee(str, x, y);
			return;
		}
		Point2D closest = Service.getBuildingLocation(x, y);
		
		x = closest.getX();
		y = closest.getY();
		
		
		BuildingType buildingType = Service.getBuildingType(str);
		if(y+buildingType.getHeight() > rootView.getHeight())
			return;
		
		if(!checkCollision(true, x, y, buildingType.getWidth(), buildingType.getHeight(), 0, 0, 0, 0))
			addBuilding(str, x, y);
	}
	
	
	
	/*
	 * GETTERS AND SETTERS
	 */
	
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	public BorderPane getRootView() {
		return rootView;
	}
	public void setRootView(BorderPane rootView) {
		this.rootView = rootView;
	}
	public GameMainView getGameMainView() {
		return gameMainView;
	}
	public void setGameMainView(GameMainView gameMainView) {
		this.gameMainView = gameMainView;
	}

	
	
}
