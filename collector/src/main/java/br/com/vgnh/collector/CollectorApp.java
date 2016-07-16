package br.com.vgnh.collector;

import org.apache.log4j.Logger;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.*;

import org.json.simple.JSONObject;

@RestController
@EnableAutoConfiguration
public class CollectorApp {

    private final static Logger logger = Logger.getLogger(CollectorApp.class);
    private final static Logger tweetLogger = Logger.getLogger("TweetLogger");

    private Twitter twitter = TwitterFactory.getSingleton();

    @RequestMapping(value = "/collect", method = RequestMethod.POST)
    @ResponseBody
    String collect(@RequestBody String hashtag) throws TwitterException {

        if (hashtag.startsWith("#")) {
            hashtag = hashtag.substring(1);
        }

        Query query = new Query("#" + hashtag);
        QueryResult result = twitter.search(query);

        for (Status status : result.getTweets()) {
            JSONObject obj = new JSONObject();
            obj.put("username", status.getUser().getScreenName());
            obj.put("num_followers", status.getUser().getFollowersCount());
            obj.put("lang", status.getLang());
            obj.put("hashtag", status.getLang());
            obj.put("timesatmp", status.getCreatedAt().getTime());
            tweetLogger.info(obj.toJSONString());
        }
        return "OK";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CollectorApp.class, args);
    }
}