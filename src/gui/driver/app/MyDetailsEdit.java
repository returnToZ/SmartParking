package gui.driver.app;

/*
 * implementation of screen that will show the functionality of editing details.  
 * 
 * @author Shahar-Y
 * 
 */

import java.util.ArrayList;

import Exceptions.LoginException;
import data.members.StickersColor;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyDetailsEdit extends AbstractWindow {

	public MyDetailsEdit() {
		windowEnum = WindowEnum.MY_DETAILS_EDIT;
		window = new Stage();
		window.getIcons().add(new Image(getClass().getResourceAsStream("Smart_parking_icon.png")));
	}

	public void display(final Stage primaryStage, final WindowEnum prevWindow, final ArrayList<Label> ls,
			final ArrayList<Label> values) {
		// window = primaryStage;
		window.setTitle("Edit My Details");
		final GridPane grid = new GridPane();
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(8);
		grid.setHgap(10);
		grid.setBackground(
				new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(2, 2, 2, 2))));
		window.setWidth(350);
		window.setHeight(340);
		final ArrayList<TextField> newValues = new ArrayList<TextField>();
		int i = 0;
		final int stickerIdx = 4;
		// System.out.println("DME labels.size(): " + labels.size());
		for (; i <= 2; ++i) {

			// System.out.println("DME newValues: " + i + " " +
			// labels.get(i).getText());
			newValues.add(new TextField(values.get(i).getText()));
			GridPane.setConstraints(ls.get(i), 0, i);
			GridPane.setConstraints(newValues.get(i), 1, i);
			grid.getChildren().addAll(ls.get(i), newValues.get(i));
		}

		final String prePN = login.getPhoneNumber().substring(0, 3), endPN = login.getPhoneNumber().substring(3, 10);
		final HBox hboxPhone = new HBox();
		final Label phoneNumber = new Label("Phone Number:");
		final ChoiceBox<String> prefixNumber = new ChoiceBox<>();
		prefixNumber.getItems().addAll("050", "052", "053", "054", "057");
		prefixNumber.setValue(prePN);
		prefixNumber.getStyleClass().add("cb");
		final TextField phoneNumberInput = new TextField();
		phoneNumberInput.setMaxWidth(95);
		// phoneNumberInput.setMaxWidth(50);
		phoneNumberInput.setText(endPN);
		hboxPhone.getChildren().addAll(prefixNumber, phoneNumberInput);
		GridPane.setConstraints(hboxPhone, 1, 3);
		// GridPane.setConstraints(phoneNumberInput, 1, 3);
		GridPane.setColumnSpan(hboxPhone, 2);
		GridPane.setConstraints(phoneNumber, 0, 3);

		final Label sticker = new Label("Sticker Color:");
		final ChoiceBox<String> stickerColor = new ChoiceBox<>();
		stickerColor.getItems().addAll("Blue", "Green", "White", "Red", "Bordeaux", "Yellow");
		stickerColor.setValue(UtilMethods.getStickerColor(login.getSticker()));
		stickerColor.getValue();
		stickerColor.getStyleClass().add("cb");
		GridPane.setConstraints(stickerColor, 1, stickerIdx);
		GridPane.setConstraints(sticker, 0, stickerIdx);
		grid.getChildren().add(sticker);
		grid.getChildren().add(stickerColor);

		final Button doneButton = new Button();
		setButtonGraphic (doneButton, "yes_button.png");
		doneButton.setOnAction(e -> {
			// Save edits
			if (checkChangesLegality(newValues)) {
				final ArrayList<Label> correctedValues = new ArrayList<Label>();
				for (int j = 0; j < newValues.size(); ++j)
					correctedValues.add(new Label(newValues.get(j).getText()));
				correctedValues.add(3, new Label(prefixNumber.getValue() + phoneNumberInput.getText()));
				correctedValues.add(4, new Label(stickerColor.getValue()));

				// printCorrectedValues(correctedValues);

				try {
					// String carNumber, String name, String phoneNumber, String
					// email, String newCar, StickersColor type
					login.userUpdate(login.getCarNumber(), correctedValues.get(1).getText(),
							correctedValues.get(3).getText(), correctedValues.get(0).getText(),
							correctedValues.get(2).getText(),
							StickersColor.valueOf(correctedValues.get(4).getText().toUpperCase()));
					// You can only get here if the last prevWindows is
					// 'MyDetails'!!
					window.close();
					final MyDetails MD = (MyDetails) AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size() - 1);
					AbstractWindow.prevWindows.remove(prevWindows.size() - 1);
					MD.display(primaryStage, prevWindow, ls, correctedValues);
				} catch (final LoginException e1) {
					new AlertBox().display("Sign Up", e1 + "");
				}
				/* Done */
			}
		});
		// System.out.println("MDE 2 size: " + values.size() + " : " + values);
		GridPane.setConstraints(doneButton, 0, values.size());

		final Button backButton = new Button();
		backButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("back_button.png"))));
		backButton.getStyleClass().add("button-go");

		backButton.setOnAction(e -> {
			// move to editing my details
			window.close();
			final MyDetails MD = (MyDetails) AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size() - 1);
			AbstractWindow.prevWindows.remove(prevWindows.size() - 1);
			MD.display(primaryStage, prevWindow, ls, values);
			/*
			 * prevWindows.get(prevWindows.size() - 1).window.show();
			 * prevWindows.remove(prevWindows.size() - 1);
			 */

		});
		GridPane.setConstraints(backButton, 1, values.size());

		grid.getChildren().addAll(doneButton, backButton, hboxPhone, phoneNumber);
		final Scene scene = new Scene(grid);
		scene.getStylesheets().add(getClass().getResource("mainStyle.css").toExternalForm());
		window.setScene(scene);
		window.show();

	}

	public static boolean checkChangesLegality(final ArrayList<TextField> newValues) {

		return true;
	} 
}
