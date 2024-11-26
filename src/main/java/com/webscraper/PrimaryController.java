package com.webscraper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class PrimaryController {

    @FXML
    private TextField urlInput;
    @FXML
    private Button scrapeButton;
    @FXML
    private Label warningLabel;
    @FXML
    private ListView<String> dataList;

    // Called when the Scrape button is clicked
    @FXML
    private void onScrapeButtonClick() {
        String url = urlInput.getText().trim();
        if (!url.isEmpty() && isValidUrl(url)) {
            scrapeData(url);
        } else {
            warningLabel.setText("Please enter a valid URL.");
            warningLabel.setVisible(true); // Show the warning
        }
    }

    // Validates the URL
    private boolean isValidUrl(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }

    private void scrapeData(String url) {
        try {
            // Connect to the URL and parse the HTML
            Document doc = Jsoup.connect(url).get();

            // Get the HTML of the page
            String htmlContent = doc.html(); // You can adjust this if you want the text content or parts of the HTML

            // Clear any previous data in the ListView
            dataList.getItems().clear();

            // Show the whole HTML content as a simple string in the ListView (truncated for readability)
            String truncatedHtml = htmlContent.length() > 500 ? htmlContent.substring(0, 500) + "..." : htmlContent;
            dataList.getItems().add(truncatedHtml); // Show the truncated HTML in ListView

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
