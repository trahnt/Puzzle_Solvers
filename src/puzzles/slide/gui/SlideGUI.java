package puzzles.slide.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import puzzles.common.Observer;
import puzzles.slide.model.SlideClientData;
import puzzles.slide.model.SlideModel;

import java.io.IOException;

public class SlideGUI extends Application implements Observer<SlideModel, SlideClientData> {
    private SlideModel model;
    private BorderPane borderPane;

    /** The size of all icons, in square dimension */
    private final static int ICON_SIZE = 75;
    /** the font size for labels and buttons */
    private final static int BUTTON_FONT_SIZE = 20;
    private final static int FONT_SIZE = 12;
    private final static int NUMBER_FONT_SIZE = 24;
    /** Colored buttons */
    private final static String EVEN_COLOR = "#ADD8E6";
    private final static String ODD_COLOR = "#FED8B1";
    private final static String EMPTY_COLOR = "#FFFFFF";

    private Stage stage;

    @Override
    public void init() throws IOException {
        // get the file name from the command line
        String filename = getParameters().getRaw().get(0);
        this.model = new SlideModel(filename);
        this.model.addObserver(this);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        borderPane = new BorderPane();
        borderPane.setCenter(makeGridPane());
        Label label = new Label("Loaded: " + getParameters().getRaw().get(0));
        borderPane.setTop(label);
        BorderPane.setAlignment(label, Pos.CENTER);

        Button load = new Button("Load");
        Button reset = new Button("Reset");
        Button hint = new Button("Hint");
        HBox hbox = new HBox();
        hbox.getChildren().addAll(load, reset, hint);
        hbox.setAlignment(Pos.CENTER);
        borderPane.setBottom(hbox);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void update(SlideModel model, SlideClientData data) {
        this.model = model;
        borderPane.setCenter(makeGridPane());

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public GridPane makeGridPane(){
        GridPane gridPane = new GridPane();
        int[][] grid = model.getCurrentConfig().getGrid();
        for (int c = 0; c < grid[0].length; c++){
            for (int r = 0; r < grid.length; r++){
                if (grid[r][c] == grid.length*grid[0].length){
                    Button button = new Button();
                    button.setMinSize(ICON_SIZE, ICON_SIZE);
                    button.setMaxSize(ICON_SIZE, ICON_SIZE);
                    gridPane.add(button, c, r);
                }
                else{
                    String color = ODD_COLOR;
                    if (grid[r][c] % 2 == 0) color = EVEN_COLOR;
                    Button button = new Button();
                    button.setStyle(
                            "-fx-font-family: Arial;" +
                                    "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                    "-fx-background-color: " + color + ";" +
                                    "-fx-font-weight: bold;");
                    button.setText(String.valueOf(grid[r][c]));
                    button.setMinSize(ICON_SIZE, ICON_SIZE);
                    button.setMaxSize(ICON_SIZE, ICON_SIZE);
                    gridPane.add(button, c, r);}
                }}
        return gridPane;
    }
}
