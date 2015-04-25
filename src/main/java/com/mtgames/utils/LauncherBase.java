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

import java.util.Objects;

abstract public class LauncherBase extends Application{
	private static final VBox      options        = new VBox();
	static final TextArea  consoleOut     = new TextArea();
	private static final TextField consoleIn      = new TextField();
	static final String    appName        = Manifests.getName();
	private static final Insets    margins        = new Insets(8, 8, 8, 8);
	private static final Insets    optionMargins  = new Insets(4, 4, 4, 4);
	protected static     boolean   consoleEnabled = false;

	protected static void addOption(String labelText, Node option) {
		Label label = new Label(labelText + ": ");
		HBox hBox = new HBox();
		hBox.getChildren().addAll(label, option);
		hBox.setAlignment(Pos.CENTER);
		HBox.setMargin(option, optionMargins);

		options.getChildren().add(hBox);
	}

	@Override public void start(Stage stage) throws Exception {
		Label debugLabel = new Label("Debug mode: ");
		CheckBox debug = new CheckBox();
		Button launch = new Button("Launch");
		Button log = new Button("Create log");

		//		Create TabPane
		TabPane root = new TabPane();
		root.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

//		Create tabs
		Tab optionsTab = new Tab("Options");
		Tab consoleTab = new Tab("Console");

//		Add tabs to TabPane
		root.getTabs().addAll(optionsTab, consoleTab);

//		Create panes
//		Debug panes
		BorderPane optionsPane = new BorderPane();
			HBox debugHBox = new HBox();
//		Console panes
		BorderPane consolePane = new BorderPane();
			HBox consoleHBox = new HBox();

//		Add panes to correct tab
		optionsTab.setContent(optionsPane);
		consoleTab.setContent(consolePane);

//		Adding options
		optionsPane.setCenter(options);
		options.getChildren().add(debugHBox);
			debugHBox.getChildren().addAll(debugLabel, debug);

//		Adding console stuff
		consoleOut.setEditable(false);
		consolePane.setCenter(consoleOut);
		consolePane.setBottom(consoleHBox);
			consoleHBox.getChildren().addAll(consoleIn, log);
			consoleIn.setDisable(true);
			log.setDisable(true);

//		Adding launch button
		optionsPane.setBottom(launch);

//		Set alignment
		options.setAlignment(Pos.TOP_CENTER);
		debugHBox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(launch, Pos.CENTER);
		BorderPane.setAlignment(consoleHBox, Pos.CENTER);
		consoleHBox.setAlignment(Pos.CENTER);

//		Set margins
		BorderPane.setMargin(options, margins);
			HBox.setMargin(debug, optionMargins);
		BorderPane.setMargin(launch, margins);

//		Size settings
		launch.setPrefSize(120, 60);
		consoleIn.setPrefWidth(1000);
		log.setMinWidth(100);
		stage.setMinWidth(400);
		stage.setMaxWidth(800);
		stage.setMinHeight(300);
		stage.setMaxHeight(600);

		Scene scene = new Scene(root, 400, 300);
		stage.setScene(scene);
		stage.setTitle(appName + " launcher");
		stage.show();

		if (System.getProperty("com.mtgames.debug") != null) {
			debug.setSelected(Integer.getInteger("com.mtgames.debug") == 0);
		}

		launch.requestFocus();

		consoleIn.setOnAction(event -> {
			if (!Objects.equals(consoleIn.getText(), "")) {
				consoleCommand(consoleIn.getText());
				consoleIn.setText("");
			}
		});

		log.setOnAction(event -> Debug.createLog());

		launch.setOnAction(event -> {
			System.setProperty("com.mtgames.debug", String.valueOf(debug.isSelected() ? Debug.DEBUG : Debug.WARNING));
			Debug.log("Launching: " + appName, Debug.LAUNCHER);
			root.getSelectionModel().select(consoleTab);
			optionsPane.setDisable(true);
			log.setDisable(false);
			if (debug.isSelected() && consoleEnabled) {
				consoleIn.setDisable(false);
			}
			run();
		});

		if (Boolean.getBoolean("com.mtgames.launch")) {
			System.setProperty("com.mtgames.debug", String.valueOf(debug.isSelected() ? Debug.DEBUG : Debug.WARNING));
			Debug.log("Launching: " + appName, Debug.LAUNCHER);
			root.getSelectionModel().select(consoleTab);
			optionsPane.setDisable(true);
			log.setDisable(false);
			if (debug.isSelected() && consoleEnabled) {
				consoleIn.setDisable(false);
			}
			run();
		}

	}

	protected abstract void run();
	protected abstract void consoleCommand(String command);
}
