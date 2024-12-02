/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



/**
 *
 * @author abdulwadudabdulkadir
 */
package com.mycompany.namehistory;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class NameHistory extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Main layout
        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(15);
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #f4f4f9;"); // Soft background color

        // Title
        Label title = new Label("Name History");
        title.setFont(new Font("Arial", 30));
        title.setTextFill(Color.web("#2c3e50")); // Dark color for the title

        // Search bar container
        HBox searchBarContainer = new HBox();
        searchBarContainer.setAlignment(Pos.CENTER);
        searchBarContainer.setSpacing(10);

        // Search bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Enter a name...");
        searchBar.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 5 10;");
        searchBar.setPrefHeight(40);
        searchBar.setPrefWidth(300);

        // Enter button
        Button enterButton = new Button("Enter");
        enterButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 15;");
        enterButton.setFont(new Font("Arial", 14));
        enterButton.setPrefHeight(40);
        enterButton.setPrefWidth(80);

        searchBarContainer.getChildren().addAll(searchBar, enterButton);

        // Result container
        VBox resultContainer = new VBox();
        resultContainer.setSpacing(10);
        resultContainer.setAlignment(Pos.CENTER);
        resultContainer.setPadding(new Insets(10));
        resultContainer.setStyle(
                "-fx-background-color: #ffffff; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: #dcdde1;");
        resultContainer.setPrefWidth(400);

        // Default result
        Label resultTitle = new Label("Name: Default Name");
        resultTitle.setFont(new Font("Arial", 18));
        resultTitle.setTextFill(Color.web("#2c3e50"));

        Label resultDescription = new Label(
                "Description: A placeholder description. This will display the history and details of the name.");
        resultDescription.setWrapText(true);
        resultDescription.setFont(new Font("Arial", 14));
        resultDescription.setTextFill(Color.web("#34495e"));

        resultContainer.getChildren().addAll(resultTitle, resultDescription);

        // Add components to root
        root.getChildren().addAll(title, searchBarContainer, resultContainer);

        // Event handler for the enter button
        enterButton.setOnAction(e -> {
            String enteredName = searchBar.getText().trim();
            if (enteredName.isEmpty()) {
                resultTitle.setText("Name: Invalid Entry");
                resultDescription.setText("Please enter a valid name to fetch details.");
            } else {
                // Make API call to fetch name history
                String apiResponse = fetchNameHistory(enteredName);
                
                if (apiResponse != null) {
                    // If API response is valid, update UI
                    resultTitle.setText("Name: " + enteredName);
                    resultDescription.setText("Description: " + apiResponse);
                } else {
                    // If API fails, show default message
                    resultTitle.setText("Name: " + enteredName);
                    resultDescription.setText("Description: Failed to fetch details. Please try again later.");
                }
            }
        });

        // Set up the scene and stage
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("Name History App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to send an API request and fetch name history
    private String fetchNameHistory(String name) {
        try {
            // URL of the API endpoint (replace with actual API URL)
            URL url = new URL("https://api.example.com/namehistory?name=" + URLEncoder.encode(name, StandardCharsets.UTF_8));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Timeout after 5 seconds
            connection.setReadTimeout(5000);    // Timeout after 5 seconds

            int status = connection.getResponseCode();
            if (status == 200) { // HTTP 200 OK
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString(); // Return the API response
            } else {
                return null; // API error or invalid response
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Error in fetching data
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
