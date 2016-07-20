#! /bin/bash

HOST="localhost:3000"

for ENDPOINT in "/user/list_top_followed?num=5" "/hashtag/count_tweets?lang=pt" "/tweets/count_by_hour"
do
	echo -e "=== Testing API ==="
	echo -e "endpoint: $ENDPOINT"
	curl -X GET -H "Cache-Control: no-cache" "$HOST$ENDPOINT"
	echo -e
	echo -e
done
