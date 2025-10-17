package kg.mega.lalafo.service;

import kg.mega.lalafo.model.Ad;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LalafoService {

    public List<Ad> getAds() {
        List<Ad> ads = new ArrayList<>();
        WebDriver driver = null;

        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            driver = new ChromeDriver(options);

            String url = "https://lalafo.kg/bishkek";
            driver.get(url);

            Thread.sleep(3000); // ждём первую загрузку

            // Прокрутка страницы вниз с ожиданием подгрузки контента
            long lastHeight = (long) ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("return document.body.scrollHeight");

            int scrollCount = 0;
            while (scrollCount < 20) { // до 20 прокруток максимум
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("window.scrollTo(0, document.body.scrollHeight);");

                Thread.sleep(2000); // ждём пока догрузятся объявления

                long newHeight = (long) ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("return document.body.scrollHeight");

                if (newHeight == lastHeight) break; // достигли конца
                lastHeight = newHeight;
                scrollCount++;
            }

            //  парсим весь контент
            String html = driver.getPageSource();
            Document doc = Jsoup.parse(html);
            Elements adElements = doc.select("article, div[role=article]");

            int count = 0;
            for (Element el : adElements) {
                if (count >= 100) break;

                String title = el.select("p").first() != null ? el.select("p").first().text() : "";
                if (title.isEmpty() || isCategory(title)) continue;

                String price = el.select("p:matches((?i)сом)").text();
                if (price.isEmpty()) price = "—";

                String imageUrl = el.select("img").attr("data-src");
                if (imageUrl.isEmpty()) imageUrl = el.select("img").attr("src");
                if (imageUrl.isEmpty()) imageUrl = "https://via.placeholder.com/300x200?text=No+Image";

                ads.add(new Ad(title, price, "Бишкек", "—", imageUrl));
                count++;
            }

            if (ads.isEmpty()) {
                ads.add(new Ad("Не удалось найти объявления 😢", "—", "Lalafo", "—",
                        "https://via.placeholder.com/300x200?text=No+Ads"));
            }

        } catch (Exception e) {
            ads.add(new Ad("Ошибка: " + e.getMessage(), "—", "Сервер", "—",
                    "https://via.placeholder.com/300x200?text=Lalafo+Error"));
        } finally {
            if (driver != null) driver.quit();
        }

        return ads;
    }




    // Метод, чтобы отфильтровать категории (например, "Транспорт", "Работа" и т.д.)
    private boolean isCategory(String text) {
        String lower = text.toLowerCase();
        return lower.contains("транспорт") ||
                lower.contains("недвижимость") ||
                lower.contains("услуги") ||
                lower.contains("дом и сад") ||
                lower.contains("ремонт") ||
                lower.contains("техника") ||
                lower.contains("работа") ||
                lower.contains("вещи") ||
                lower.contains("спорт") ||
                lower.contains("животные") ||
                lower.contains("оборудование") ||
                lower.contains("детский") ||
                lower.contains("медтовары") ||
                lower.contains("находки") ||
                lower.contains("иссык") ||
                lower.length() < 4;
    }
}