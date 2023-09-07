import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.awt.desktop.ScreenSleepEvent;
import java.time.LocalDate;
import java.time.LocalTime;

import java.io.FileWriter;
import java.io.IOException;


public class NewsScraper {
    public static void main(String[] args) throws IOException, InterruptedException {

        Write(LocalDate.now() + ScrapeTagesschau() + " \n(Tagesschau: " + LocalTime.now() + ") \n\n");

        while (true){
            if (newNews()==true){
                Write(LocalDate.now() + ScrapeTagesschau() + " \n(Tagesschau: " + LocalTime.now() + ") \n\n");
            }
            else {System.out.println("keine neuen News");}
            Thread.sleep(500);
        }



        //String b = ScrapeTagesschau();
        //String a = LocalDate.now() + b + " \n(Tagesschau: " + LocalTime.now() + ") \n\n";

        //System.out.println(a);
        //Write(a); //writes the news article in the txt
    }


    public static boolean newNews() throws InterruptedException {
        String h1 = ScrapeTagesschau();
        Thread.sleep(500);
        String h2 = ScrapeTagesschau();
        return !h1.equals(h2);
    }


    public static String ScrapeTagesschau() {
        String topic;
        String head;
        String content;
        try {
            String url = "https://www.tagesschau.de/";

            // Connect to the URL
            Document document = Jsoup.connect(url).get();

            // Locate news (different for every website so adjust!!!)
            Element Etopic = document.select("div.teasergroup:nth-child(1) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1) > div:nth-child(1) > div:nth-child(1) > h3:nth-child(1) > span:nth-child(1)").first();
            Element Ehead = document.select("div.teasergroup:nth-child(1) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1) > div:nth-child(1) > div:nth-child(1) > h3:nth-child(1) > span:nth-child(2)").first();
            Element Econtent = document.select("div.teasergroup:nth-child(1) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1) > div:nth-child(1) > div:nth-child(1) > p:nth-child(2)").first();

            // Extract and return the News
            if (Etopic != null) {
                topic = Etopic.text();
                head = Ehead.text();
                content = Econtent.text();
                return "\nThema: " + topic + " - " + head + "\nInhalt: " + content;
            } else {
                return "ERROR: news not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "ERROR: Exception_Scrape";
    }

    public static String Write(String a) throws IOException {
        try {
            FileWriter writer = new FileWriter("news.txt", true);
            writer.write(a);
            writer.close();
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR: Exception_Write";
    }
}
