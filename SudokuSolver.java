import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SudokuSolver extends Application {

	TextField[][] t = new TextField[9][9];

	public void start(Stage primaryStage) {
		// Create a pane and set its properties
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setStyle("-fx-background-color: #FFE4C4");
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		pane.setPrefWidth(800);

		// Place nodes in the pane at positions column,row
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				t[i][j] = new TextField("-");
				t[i][j].setStyle("-fx-background-color: #DEB887");
				t[i][j].setFont(Font.font("Verdana", 30)); 
				pane.add(t[i][j], i, j);

				if (j%3==2 && j < 7) 		// Add horizontal lines as the unit lines
					t[i][j].setStyle("-fx-border-color: #8B4513; -fx-border-width: 0 0 5 0; -fx-background-color: #DEB887");
			}
		}

		// Add vertical lines for unit lines
		for (int i =0;i < 9;i++) {
			for (int j = 1; j < 3;j++) {
				Line l = new Line(10,0, 10, 50);
				l.setStyle("-fx-stroke: #8B4513; -fx-stroke-width: 5");
				pane.add(l, 3*j, i);
			}}

		Button solver = new Button("Solve");
		solver.setPrefWidth(100);
		pane.add(solver, 4, 9);
		GridPane.setHalignment(solver, HPos.RIGHT);

		Button clear = new Button("Clear");
		clear.setPrefWidth(100);
		pane.add(clear, 4, 10);
		GridPane.setHalignment(clear, HPos.RIGHT);

		Label a = new Label("Message");
		pane.add(a, 4, 11);
		GridPane.setHalignment(a, HPos.CENTER);

		Label a2 = new Label("Board");
		pane.add(a2, 4, 12);
		GridPane.setHalignment(a2, HPos.CENTER);

		// Create a scene and place it in the stage
		Scene scene = new Scene(pane);
		primaryStage.setTitle("Sudoku Solver");
		primaryStage.setScene(scene);
		primaryStage.show();

		solver.setOnAction(e -> {
			Board b = new Board();

			for (int i = 0; i < 9; i++) 		// Get the input board
				for (int j = 0; j < 9; j++) 
					b.arr[i][j].setLetter(((TextField)getNodeFromGridPane(pane,i,j)).getText());

			if (b.isBoardLegal()) {
				b.solve(0, 0);				// Board gets solved
				a.setText("Board");
				a2.setText("Solved!");
			}
					
			else {
				a.setText("Bad");
				a2.setText("Input!");
			}

			for (int i = 0; i < 9; i++)       // Set text of solved board here
				for (int j = 0; j < 9; j++) 
					(t[i][j]).setText(b.arr[j][i].letter);
		});

		clear.setOnAction(e -> {    			// Reset board to x's
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					(t[i][j]).setText("-");
				}
			}
			a.setText("Message");
			a2.setText("Board");
		});
	}

	// Get the object from the gridpane, primarily for textfield
	private Node getNodeFromGridPane(GridPane gridPane, int r, int c) { 
		for (Node node : gridPane.getChildren()) {
			if (GridPane.getColumnIndex(node) == c && GridPane.getRowIndex(node) == r)
				return node;
		}
		return null;
	}

	// Run the GUI
	public static void main(String[] args) {
		launch(args);
	}
}
