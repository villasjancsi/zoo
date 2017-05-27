import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.DAO;
import model.Service;
import view.MainController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	MainController mainController = new MainController();
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Zoo Z");
		mainController.setPrimaryStage(primaryStage);

		loadRootView();
		loadMainView();
		Service.setMainController(mainController);
		DAO dao = Service.initialize();
		
		mainController.initialize(dao.getAnimalTypesList(), dao.getBuildingTypesList(), dao.getEmployeeTypesList(), dao.getZoo().getBuildings());
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void loadRootView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootView.fxml"));
			mainController.setRootView((BorderPane) loader.load());
			Scene scene = new Scene(mainController.getRootView());
			mainController.getPrimaryStage().setScene(scene);
			mainController.getPrimaryStage().show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadMainView() {
		try{
			/*FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/GameMainView2.fxml"));
			ScrollPane scrollPane = (ScrollPane) loader.load();
			AnchorPane mainPane = (AnchorPane) (scrollPane.getContent());
			mainController.getRootView().setCenter(scrollPane);
			mainController.setGameMainView(loader.getController());
			mainController.getGameMainView().setMainPane(mainPane);*/
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/GameMainView.fxml"));
			AnchorPane mainPane = (AnchorPane) loader.load();
			mainController.getRootView().setCenter(mainPane);
			mainController.setGameMainView(loader.getController());
			mainController.getGameMainView().setMainPane(mainPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
