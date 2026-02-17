package queens;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {
    private GridPane boardGrid = new GridPane();
    private TextField fileInput = new TextField();
    private Label statusLabel = new Label("Masukkan nama file: ");
    private Map<String, Color> colorMap = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f4f4f4;");

        // Panel Kontrol
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);
        fileInput.setPromptText("Nama file tc...");
        Button solveBtn = new Button("Solve");
        solveBtn.setStyle(
                "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");

        controls.getChildren().addAll(fileInput, solveBtn);

        // Papan Visual
        boardGrid.setAlignment(Pos.CENTER);
        boardGrid.setPadding(new Insets(10));

        solveBtn.setOnAction(e -> handleSolve());

        root.getChildren().addAll(statusLabel, controls, boardGrid);

        Scene scene = new Scene(root, 700, 800);
        primaryStage.setTitle("Tucil 1  Stima - Queens");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleSolve() {
        String fileName = fileInput.getText();

        new Thread(
                        () -> {
                            try {
                                Solver.totalKasus = 0;
                                boolean loaded = Solver.loadBoard("src/main/input/" + fileName);

                                if (!loaded) {
                                    Platform.runLater(
                                            () -> statusLabel.setText("File tidak ditemukan"));
                                    return;
                                }

                                int n = Solver.board.length;
                                Solver.Koordinat[] queenLocation = new Solver.Koordinat[n];
                                for (int i = 0; i < n; i++)
                                    queenLocation[i] = new Solver.Koordinat(i, 0);

                                long startTime = System.currentTimeMillis();

                                boolean solved =
                                        Solver.solve(
                                                queenLocation,
                                                (currentLocation) -> {
                                                    Platform.runLater(
                                                            () -> {
                                                                renderBoard(
                                                                        Solver.board,
                                                                        currentLocation);
                                                                statusLabel.setText(
                                                                        "Banyak kasus yang"
                                                                                + " ditinjau: "
                                                                                + Solver
                                                                                        .totalKasus);
                                                            });
                                                });

                                long endTime = System.currentTimeMillis();

                                Platform.runLater(
                                        () -> {
                                            if (solved) {
                                                statusLabel.setText(
                                                        "Solusi ditemukan dalam "
                                                                + (endTime - startTime)
                                                                + " ms\n"
                                                                + "Banyak kasus yang ditinjau: "
                                                                + Solver.totalKasus);
                                                renderBoard(Solver.board, queenLocation);
                                            } else {
                                                statusLabel.setText("Tidak ada solusi!");
                                            }
                                        });

                            } catch (Exception ex) {
                                Platform.runLater(
                                        () -> statusLabel.setText("Error: " + ex.getMessage()));
                                ex.printStackTrace();
                            }
                        })
                .start();
    }

    private void renderBoard(String[][] board, Solver.Koordinat[] solution) {
        boardGrid.getChildren().clear();
        int n = board.length;

        // Hitung ukuran cell agar muat di layar (misal max 600px total)
        double cellSize = Math.min(600.0 / n, 50.0);
        double fontSize = cellSize * 0.5;

        generateColorMap(board);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                StackPane cell = new StackPane();
                Rectangle rect = new Rectangle(cellSize, cellSize); // Gunakan cellSize dinamis
                rect.setFill(colorMap.getOrDefault(board[i][j], Color.LIGHTGRAY));
                rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(0.2); // Stroke lebih tipis untuk board besar

                cell.getChildren().add(rect);

                for (Solver.Koordinat q : solution) {
                    if (q.x() == i && q.y() == j) {
                        Label queenLabel = new Label("♛");
                        queenLabel.setStyle(
                                "-fx-font-size: " + fontSize + "px; -fx-text-fill: black;");
                        cell.getChildren().add(queenLabel);
                    }
                }
                boardGrid.add(cell, j, i);
            }
        }
    }

    private void generateColorMap(String[][] board) {
        colorMap.clear();
        // Palet 26 warna yang kontras untuk mendukung region A-Z
        Color[] palette = {
            Color.LIGHTPINK, Color.LIGHTSKYBLUE, Color.LIGHTGREEN, Color.LIGHTCORAL,
            Color.LIGHTGOLDENRODYELLOW, Color.MEDIUMPURPLE, Color.ORANGE, Color.AQUAMARINE,
            Color.TOMATO, Color.THISTLE, Color.YELLOWGREEN, Color.CHARTREUSE,
            Color.DEEPSKYBLUE, Color.GOLD, Color.HOTPINK, Color.INDIANRED,
            Color.KHAKI, Color.LAVENDER, Color.LIGHTCYAN, Color.LIGHTSALMON,
            Color.LIGHTSEAGREEN, Color.MISTYROSE, Color.PALEGREEN, Color.POWDERBLUE,
            Color.SANDYBROWN, Color.WHEAT
        };

        int colorIdx = 0;
        for (String[] row : board) {
            for (String cell : row) {
                if (!colorMap.containsKey(cell)) {
                    // Mengambil warna dari palet, jika lebih dari 26 akan looping (modulo)
                    colorMap.put(cell, palette[colorIdx % palette.length]);
                    colorIdx++;
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
