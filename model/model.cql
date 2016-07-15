create keyspace vgnh
  with replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}
  and durable_writes = true;

use vgnh;

create table user (
  screen_name text,
  num_followers int,
  primary key (screen_name, num_followers)
);

create table hashtag_count (
  hour timestamp,
  lang text,
  hashtag text,
  num int,
  primary key (hour, lang, hashtag)
);
