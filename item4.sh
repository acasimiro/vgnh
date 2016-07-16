#! /bin/bash

for HASHTAG in brasil brazil brasil2016 brazil2016 jogosolimpicos olimpiadas olimpiadas2016 olympics rio2016 riodejaneiro
do
	curl -X POST -H "Cache-Control: no-cache" -d \'$HASHTAG\' http://localhost:8080/collect
done
