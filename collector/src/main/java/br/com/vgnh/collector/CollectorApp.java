package br.com.vgnh.collector;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import org.json.simple.JSONObject;
import twitter4j.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@EnableAutoConfiguration
public class CollectorApp {

    @Value("${outputFolder}")
    private String outputFolder;

    private static final Logger logger = LogManager.getLogger(CollectorApp.class);
    private Twitter twitter = TwitterFactory.getSingleton();

    @RequestMapping(value = "/collect", method = RequestMethod.POST)
    @ResponseBody
    String collect(@RequestBody String hashtag) throws IOException, TwitterException {

        logger.info(String.format("Inicio da coleta para hashtag '%s'", hashtag));
        if (hashtag.startsWith("#")) {
            hashtag = hashtag.substring(1);
        }

        QueryResult queryResult = getTweetsFromHashtag(hashtag);
        BufferedWriter writer = getFileWriter();

        for (Status status : queryResult.getTweets()) {
            String s = "" + status.getId();
            s += "|" + status.getUser().getScreenName();
            s += "|" + status.getUser().getFollowersCount();
            s += "|" + status.getLang();
            s += "|" + hashtag;
            s += "|" + status.getCreatedAt().getTime();
            writer.write(s + "\n");
        }
        writer.close();
        logger.info(String.format("Fim da coleta para hashtag '%s'", hashtag));

        return "OK\n";
    }

    private QueryResult getTweetsFromHashtag(@RequestBody String hashtag) throws TwitterException {
        Query query = new Query("#" + hashtag);
        QueryResult result;
        try {
            result = twitter.search(query);
            logger.info(String.format("%d tweets encontrados", result.getCount()));
            return result;
        } catch (TwitterException e) {
            logger.error(String.format("Erro ao buscar tweets q=%s", query.getQuery()));
            throw e;
        }
    }

    private BufferedWriter getFileWriter() throws IOException {
        String path;
        String time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date());
        String host;
        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            host = "unknown";
        }
        path = String.format(outputFolder + "/tweets-%s-%s.json", host, time);
        File file = new File(path);
        BufferedWriter bw = null;
        try {
            logger.info(String.format("Criando arquivo '%s'", path));
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);
        } catch (IOException e) {
            logger.error(String.format("Erro ao criar arquivo de tweets '%s'", path));
            throw e;
        }
        return bw;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CollectorApp.class, args);
    }
}