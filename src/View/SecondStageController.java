package View;

import Main.Main;
import Parsing.Parse;
import Parsing.ShareData_compare;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Ray_Jung on 2017-04-06.
 */
public class SecondStageController implements Initializable {

    private Main main;
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;
    private Window stage;
    private ArrayList<Map> alldataMap2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!main.isSplashLoaded){
            loadSplashScreen();
        }
        try {
            VBox vbox = FXMLLoader.load(getClass().getResource("Drawer.fxml"));
            drawer.setSidePane(vbox);
            //Todo: make buttons clickable
            for (Node node : vbox.getChildren()){
                if (node.getAccessibleText() != null){
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED,e->{
                        switch (node.getAccessibleText()){
                            case "Button1" :
                                //Show BarChart about Source IP
                                try {
                                    main.showSourceIPBarchart();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                break;
                            case "Button2" :
                                //show BarChart about Destination IP
                                try {
                                    main.showDestinationIPPieChart();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                break;
                            case "Button3" :
                                //show not sure
                                try {
                                    main.showTrafficAreaChart();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                break;
                            case "Button4" :
                                //show chart
                                try {
                                    selectFile();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                break;
                            case "Button5" :
                                //show chart
                                break;
                        }
                    });
                }
            }
            //Todo: make hamburger button to work.
            HamburgerBackArrowBasicTransition burgerTask = new HamburgerBackArrowBasicTransition(hamburger);
            burgerTask.setRate(-1);
            hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
                burgerTask.setRate(burgerTask.getRate() * -1);
                burgerTask.play();
                if (drawer.isShown()) {
                    drawer.close();
                }else {
                    drawer.open();
                }
                hamburger.toFront();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadSplashScreen(){
        try {
            main.isSplashLoaded = true;
            StackPane pane = FXMLLoader.load(getClass().getResource("splash.fxml"));
            borderPane.setLeft(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3),pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3),pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished(e->{
                try {
                    BorderPane borderPane1 = FXMLLoader.load(getClass().getResource("/View/2Stage.fxml"));
                    borderPane.getChildren().setAll(borderPane1);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            hamburger.toFront();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void selectFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files","*.*"),
                new FileChooser.ExtensionFilter("Pcap Files","*.pcap"),
                new FileChooser.ExtensionFilter("Text Files","*.txt")
        );
        File file = fileChooser.showOpenDialog(stage);
        //Todo: show the file name on text field.

        String inputFile = String.valueOf(file);
        //Todo: get data from input and pass it to Singleton.
        Parse parse = new Parse();
        alldataMap2 =parse.getData(inputFile);
        ShareData_compare.getInstance().setInfo(alldataMap2);

        main.showOverlapingLineChart();

    }


}
