package Main;

import View.FirstStageController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;




public class Main extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("JAVAFX - Project RAY,DIEGO");
        showMainView();
    }

    //ToDo:First Stage: Select File and Scan File
    private void showMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/View/1Stage.fxml"));
        BorderPane mainLayout = loader.load();
        FirstStageController firstStageController = loader.getController();
        firstStageController.init(primaryStage);
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
        // Todo: quit the whole platform
        primaryStage.setOnCloseRequest(e->{
            e.consume();
            Platform.exit();
        });
    }
    // Todo: Control the Splash stage.
    public static Boolean isSplashLoaded = false;
    // Todo: Show menu stage.
    public static void showMenuStage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/View/2Stage.fxml"));
        BorderPane seeChartStage = loader.load();

        Stage menuStage = new Stage();
        menuStage.setTitle("Analyzing Scanned File");
        menuStage.initModality(Modality.WINDOW_MODAL);
        menuStage.initOwner(primaryStage);
        Scene scene = new Scene(seeChartStage);
        menuStage.setScene(scene);
        menuStage.show();
        //ToDo: close this menuStage.
        menuStage.setOnCloseRequest(e->{
            e.consume();
            closeProgram(menuStage);
        });
    }
    //ToDo: Show the first button chart
    public static void showSourceIPBarchart() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/View/BarChart.fxml"));
        AnchorPane addNewBarChartBorder= loader.load();

        Stage barChartStage = new Stage();
        barChartStage.setTitle("Bar Chart");

        barChartStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(addNewBarChartBorder);
        barChartStage.setScene(scene);
        barChartStage.showAndWait();
        barChartStage.setOnCloseRequest(e->{
            e.consume();
            barChartStage.close();
        });
    }
    public static void showDestinationIPPieChart() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/View/PieChart.fxml"));
        AnchorPane addNewPieChartBorder= loader.load();

        Stage pieChartStage = new Stage();
        pieChartStage.setTitle("Area Chart");
        pieChartStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(addNewPieChartBorder);
        pieChartStage.setScene(scene);
        pieChartStage.showAndWait();
        pieChartStage.setOnCloseRequest(e->{
            e.consume();
            pieChartStage.close();
        });
    }
    public static void showTrafficAreaChart() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/View/AreaChart.fxml"));
        AnchorPane addNewAreaChartBorder= loader.load();

        Stage areaChartStage = new Stage();
        areaChartStage.setTitle("Area Chart");
        areaChartStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(addNewAreaChartBorder);
        areaChartStage.setScene(scene);
        areaChartStage.showAndWait();
        areaChartStage.setOnCloseRequest(e->{
            e.consume();
            areaChartStage.close();
        });
    }
    public static void showOverlapingLineChart() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/View/LineChart.fxml"));
        AnchorPane addNewAreaChartBorder= loader.load();

        Stage areaChartStage = new Stage();
        areaChartStage.setTitle("Line Chart");
        areaChartStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(addNewAreaChartBorder);
        areaChartStage.setScene(scene);
        areaChartStage.showAndWait();
        areaChartStage.setOnCloseRequest(e->{
            e.consume();
            areaChartStage.close();
        });
    }

    private static void closeProgram(Stage stage){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to quit this process?\n\nYou will lose all chart data");
        Optional <ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK){
            stage.close();
        }
    }
}
