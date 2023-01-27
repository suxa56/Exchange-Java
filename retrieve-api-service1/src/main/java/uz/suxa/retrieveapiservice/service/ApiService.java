package uz.suxa.retrieveapiservice.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class ApiService {

    private static final List<String> urls = List.of(
            "https://cbu.uz/ru/arkhiv-kursov-valyut/json/EUR/",
            "https://cbu.uz/ru/arkhiv-kursov-valyut/json/USD/",
            "https://cbu.uz/ru/arkhiv-kursov-valyut/json/CNY/",
            "https://cbu.uz/ru/arkhiv-kursov-valyut/json/RUB/"
    );

    public List<String> retrieveApi() {
        JSONArray dataObject = new JSONArray();
        List<String> currencies = new ArrayList<>();
        for (String urlString : urls) {
            try {
                URL url = new URL(urlString);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                //Check if connect is made
                int responseCode = conn.getResponseCode();

                // 200 OK
                if (responseCode != 200) {
                    throw new RuntimeException("Unable to connect to resources");
//                    throw new RuntimeException("HttpResponseCode: " + responseCode);
                } else {

                    StringBuilder informationString = new StringBuilder();
                    Scanner scanner = new Scanner(url.openStream());

                    while (scanner.hasNext()) {
                        informationString.append(scanner.nextLine());
                    }
                    //Close the scanner
                    scanner.close();

                    //JSON simple library Setup with Maven is used to convert strings to JSON
                    JSONParser parser = new JSONParser();
                    dataObject = (JSONArray) parser.parse(String.valueOf(informationString));
                }
            } catch (Exception e) {
                throw new RuntimeException("Unable to connect to resources");
            }
            JSONObject data = (JSONObject) dataObject.get(0);
            data.remove("CcyNm_EN");
            data.remove("CcyNm_UZC");
            data.remove("Diff");
            data.remove("CcyNm_RU");
            data.remove("CcyNm_UZ");
            data.remove("id");
            data.remove("Code");
            data.remove("Nominal");
            String json = data.toJSONString();
            currencies.add(json);
        }
        return currencies;
    }
}
