var express = require('express');
var cassandra = require('cassandra-driver');
var client = new cassandra.Client({contactPoints: ['172.17.0.3'], keyspace: 'vgnh'});

var app = express();
app.set('json spaces', 2);

app.get('/user/list_top_followed', function (req, res) {
    var max_users = req.query.num || 5;
    var q = "select screen_name, num_followers from user where dummy_id = 1 \
             order by num_followers desc limit {mu}".replace("{mu}", max_users);
    client.execute(q, function (err, result) {
        if (err) {
            console.log("err", err);
            res.json({"error": "error retrieving data"})
        } else {
            res.json(result.rows);
        }
    });    
});

function groupBy(rows, attr) {
    var res = {};
    rows.forEach(function (r, i) {
        if (!res.hasOwnProperty(r[attr])) {
            res[r[attr]] = 0;
        }
        res[r[attr]] += r.num;
    });
    return res;
}

app.get('/hashtag/count_tweets', function (req, res) {
    var lang = req.query.lang || 'pt';
    var q = "select hashtag, hour, num from hashtag_by_lang where lang = '{l}';".replace("{l}", lang);
    client.execute(q, function (err, result) {
        if (err) {
            console.log("err", err);
            res.json({"error": "error retrieving data"})
        } else {
            res.json(groupBy(result.rows, "hashtag"));
        }
    });   
});

app.get('/tweets/count_by_hour', function (req, res) {
    var q = "select hour, num from hashtag_by_lang;";
    client.execute(q, function (err, result) {
        if (err) {
            console.log("err", err);
            res.json({"error": "error retrieving data"})
        } else {
            res.json(groupBy(result.rows, "hour"));
        }
    });   
});

app.listen(3000, function () {
    console.log('app listening on port 3000');
});
