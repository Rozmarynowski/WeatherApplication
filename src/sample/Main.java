package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
private double x,y;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root1 = FXMLLoader.load(getClass().getResource("controller/sample.fxml"));
        primaryStage.setTitle("Weather Application");
        primaryStage.setScene(new Scene(root1));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.show();




        root1.setOnMousePressed(event ->{

            x = event.getSceneX();
            y = event.getSceneY();
        });

        root1.setOnMouseDragged(event -> {

            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });



    }


    public static void main(String[] args) {
        launch(args);

    }
}
