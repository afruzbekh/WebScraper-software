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
    
            // Use StringBuilder to accumulate links and paragraphs
            StringBuilder links = new StringBuilder();
            StringBuilder paragraphs = new StringBuilder();
    
            // Example of scraping all links from the page
            // Get all links (anchor tags <a>)
            doc.select("a[href]").forEach(element -> {
                links.append(element.attr("href")).append("\n");  // Append the href attribute (URL)
            });
    
            // Example of scraping all paragraphs
            // Get all paragraph tags <p>
            doc.select("p").forEach(element -> {
                paragraphs.append(element.text()).append("\n");  // Append the text of the paragraph
            });
    
            // Combine the scraped data into a string (you can format it as needed)
            String scrapedData = "Links:\n" + links.toString() + "\n\n" + "Paragraphs:\n" + paragraphs.toString();
    
            // Clear any previous data in the ListView
            dataList.getItems().clear();
    
            // Show the scraped data in the ListView (truncated for readability)
            String truncatedData = scrapedData.length() > 500 ? scrapedData.substring(0, 500) + "..." : scrapedData;
            dataList.getItems().add(truncatedData);
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
