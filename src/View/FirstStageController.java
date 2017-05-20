package View;

import Main.Main;
import Parsing.Parse;
import Parsing.SharedData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Ray_Jung on 2017-04-06.
 */
public class FirstStageController {
    private ArrayList<Map> alldataMap;
    private Main main;
    private Stage stage;
    public void init(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private TextField fileName;
    @FXML
    private void scanButton() throws IOException {
        main.showMenuStage();
    }
    @FXML
    public void selectFile(){
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
        fileName.setText(String.valueOf(file));
        String inputFile = String.valueOf(file);
        //Todo: get data from input and pass it to Singleton.
        Parse parse = new Parse();
        alldataMap =parse.getData(inputFile);
        SharedData.getInstance().setInfo(alldataMap);

    }
}
