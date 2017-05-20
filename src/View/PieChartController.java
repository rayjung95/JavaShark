package View;

import Parsing.SharedData;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by Ray_Jung on 2017-04-07.
 */
public class PieChartController implements Initializable {
    private int totalNumber=0;
    private final ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
    private Stage dialogStage;
    @FXML
    private Label label;
    @FXML
    private JFXButton closeButton;
    @FXML
    private PieChart pieChart;
    private HashMap<String, Integer> destinationMap;

    @FXML
    private void doExit (){
            // get a handle to the stage
            Stage stage = (Stage) closeButton.getScene().getWindow();
            // do what you have to do
            stage.close();
    }
    @FXML
    public void saveAsPng() throws IOException {

            WritableImage image = pieChart.snapshot(new SnapshotParameters(), null);
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            // Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    "PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);
        // Show save file dialog
            File file = fileChooser.showSaveDialog(this.dialogStage);
            if (file != null) {
                // Make sure it has the correct extension
                if (!file.getPath().endsWith(".png")) {
                    file = new File(file.getPath() + ".png");
                }
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            }
        }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        destinationMap= (HashMap<String, Integer>) SharedData.getInstance().getInfo().get(1);

        for (HashMap.Entry<String, Integer> entry : destinationMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            data.addAll(new PieChart.Data(key,value));
        }


        for (int i = 0; i< data.size(); i++){
            totalNumber+= data.get(i).getPieValue();
        }
        pieChart.setData(data);

        label.setFont(Font.font("SanSerif", FontWeight.BOLD,15));

        pieChart.getData().stream().forEach(data -> {
            data.getNode().addEventHandler(MouseEvent.ANY, e->{
                label.setText("Destination IP: "+data.getName() + "  Transaction Percentage:  " + Math.round((data.getPieValue()/totalNumber)*100) +
                "%\n" );
            });
        });
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
