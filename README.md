# JavaCrawler

## GETTING STARTED

 This application will allow users to crawl data from CNA and Twitter and analyse the data.

  Libraries required (Found in the submitted project under Required Jars folder)

  1. Jsoup (v1.12.2)
    https://jsoup.org/download

  2. Twitter4J (v4.0.7)
    http://twitter4j.org/en/

  3. JavaFX (SDK - v11.0.2)
    https://gluonhq.com/products/javafx/

  4. StanfordCoreNLP (v3.9.2)
     https://stanfordnlp.github.io/CoreNLP/

## SETTING UP
  1. To setup JavaFX on Eclipse follow the following link:
    https://www.youtube.com/watch?v=oVn6_2KuYbM
    
  2. Unzip project and run/ create a new project in your preferred IDE. Following steps will be on setting up for Eclipse.
  
  3. Right click on the project and open the Properties > Java Build Path > Libraries tab
  
  4. Click on Classpath and select the Add External JARs on the right.
  
  5. Add the following JAR file from the mentioned libraries above
     1. ejml-0.23.jar
     2. jsoup-1.12.2.jar
     3. twitter4j-async-4.0.7.jar
     4. twitter4j-core-4.0.7.jar
     5. twitter4j-examples-4.0.7.jar
     6. twitter4j-stream-4.0.7.jar
     7. javafx.base.jar
     8. javafx.controls.jar
     9. javafx.fxml.jar
     10. javafx.graphics.jar
     11. javafx.media.jar
     12. javafx.swing.jar
     13. javafx.web.jar
     14. javafx-swt.jar
     15. stanford-corenlp-3.9.2.jar
     16. stanford-corenlp-3.9.2-javadoc.jar
     17. stanford-corenlp-3.9.2-models.jar
     18. stanford-corenlp-3.9.2-sources.jar
     
  6. Run the program from the Main.java file
     
## Running the Application
     For a graphical guide follow this link: https://www.youtube.com/watch?v=TJls5ZvYiho&feature=youtu.be
     
  1. For testing purposes, no live crawling. Instead, use the Load.csv function at the bottom of the CRAWLER tab.
  
  2. First ensure that you are at the Tweets tab for CRAWLER before clicking on the Load.csv button.
  
  3. Navigate to the project folder and select the pastTweets.csv
  
  4. Switch over to the CNA Articles tab and upload the testCNA_UPDATED.csv file following the steps above.
  
  5. To sort data by date, at the Tweets and CNA Articles tab click on the Date column header to sort by latest and oldest.
  
  6. To search by keywords, on the right side, there is a Search Filter box.
  
  7. For Sentiment Analysis, click on the Sentiment Analysis tab and click ANALYSE. This process may take a few minutes.
  
  8. For DORSON Analysis, click on the DORSCON Analysis tab and click ANALYSE.
