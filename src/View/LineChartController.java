package View;

import Parsing.ShareData_compare;
import Parsing.SharedData;
import com.jfoenix.controls.JFXButton;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

/**
 * Created by Ray_Jung on 2017-04-07.
 */
public class LineChartController implements Initializable {
    private Stage dialogStage;
    @FXML
    private JFXButton closeButton;
    @FXML
    private LineChart lineChart;
    private LinkedHashMap<String, Integer> overlapMap1;
    private LinkedHashMap<String, Integer> overlapMap2;

    @FXML
    private void doExit (){
            // get a handle to the stage
            Stage stage = (Stage) closeButton.getScene().getWindow();
            // do what you have to do
            stage.close();
    }
    @FXML
    public void saveAsPng() throws IOException {
            WritableImage image = lineChart.snapshot(new SnapshotParameters(), null);
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
        overlapMap1= (LinkedHashMap<String, Integer>) SharedData.getInstance().getInfo().get(2);
        XYChart.Series<String, Number> set1 = new XYChart.Series<String, Number>();
        for (HashMap.Entry<String, Integer> entry : overlapMap1.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            set1.getData().add(new XYChart.Data<String,Number>(key,value));
        }
        set1.setName("First File");


        overlapMap2= (LinkedHashMap<String, Integer>) ShareData_compare.getInstance().getInfo().get(2);
        XYChart.Series<String, Number> set2 = new XYChart.Series<String, Number>();
        for (HashMap.Entry<String, Integer> entry : overlapMap2.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            set2.getData().add(new XYChart.Data<String,Number>(key,value));
        }
        set2.setName("Second File");
        lineChart.getData().addAll(set1,set2);

    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

}
