-- Model
drop keyspace vgnh;
create keyspace vgnh
  with replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}
  and durable_writes = true;

use vgnh;

create table user (
  dummy_id int,
  num_followers int,
  screen_name text,
  primary key (dummy_id, num_followers, screen_name)
) with clustering order by (num_followers desc);

create table hashtag_count (
  lang text,
  hashtag text,
  hour timestamp,
  num int,
  primary key (lang, hashtag, hour)
);
