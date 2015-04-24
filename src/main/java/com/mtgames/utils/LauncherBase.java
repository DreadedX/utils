package com.mtgames.utils;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

abstract public class LauncherBase extends Application{
	private static final VBox   options = new VBox();
	private static final String appName = Manifests.getName();
	private static final Insets margins = new Insets(8, 8, 8, 8);
	private static final Insets optionMargins = new Insets(4, 4, 4, 4);

	protected static void addOption(String labelText, Node option) {
		Label label = new Label(labelText + ": ");

		HBox hBox = new HBox();
		hBox.getChildren().addAll(label, option);
		hBox.setAlignment(Pos.CENTER);
		HBox.setMargin(option, optionMargins);

		options.getChildren().add(hBox);
	}

	protected abstract void run();

	@Override public void start(Stage stage) throws Exception {
		Label title = new Label(appName + " settings");
		Label debugLabel = new Label("Debug mode: ");
		CheckBox debug = new CheckBox();
		Button launch = new Button("Launch");

		BorderPane root = new BorderPane();
		HBox debugHBox = new HBox();
		//		HBox scaleHBox = new HBox();

//		Adding title
		root.setTop(title);

//		Adding options
		root.setCenter(options);
		options.getChildren().add(debugHBox);
			debugHBox.getChildren().addAll(debugLabel, debug);

//		Adding launch button
		root.setBottom(launch);

//		Set alignment
		BorderPane.setAlignment(title, Pos.CENTER);
		options.setAlignment(Pos.TOP_CENTER);
		debugHBox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(launch, Pos.CENTER);

//		Set margins
		BorderPane.setMargin(title, margins);
		BorderPane.setMargin(options, margins);
			HBox.setMargin(debug, optionMargins);
		BorderPane.setMargin(launch, margins);

		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setTitle(appName + " launcher");
		stage.show();

		if (System.getProperty("com.mtgames.debug") != null) {
			debug.setSelected(Integer.getInteger("com.mtgames.debug") == 0);
		}

		launch.setDefaultButton(true);
		launch.requestFocus();

		title.getStyleClass().add("launcherTitle");
		launch.getStyleClass().add("launchButton");

		launch.setOnAction(event -> {
			System.setProperty("com.mtgames.debug", String.valueOf(debug.isSelected() ? Debug.DEBUG : Debug.WARNING));
			Debug.log("Launching: " + appName, Debug.LAUNCHER);
			stage.close();
			run();
		});
	}
}
