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

-- Inserts
insert into user (dummy_id, screen_name, num_followers) values (1, 'za', 5);
insert into user (dummy_id, screen_name, num_followers) values (1, 'ze', 5);
insert into user (dummy_id, screen_name, num_followers) values (1, 'zi', 4);
insert into user (dummy_id, screen_name, num_followers) values (1, 'zo', 3);
insert into user (dummy_id, screen_name, num_followers) values (1, 'zu', 4);
insert into user (dummy_id, screen_name, num_followers) values (1, 'xx', 1);

insert into hashtag_by_lang (lang, hashtag, hour, num) values ('pt', 'lala', '2016-07-17 20:00', 10);
insert into hashtag_by_lang (lang, hashtag, hour, num) values ('en', 'lala', '2016-07-17 21:00', 11);
insert into hashtag_by_lang (lang, hashtag, hour, num) values ('pt', 'lala', '2016-07-17 22:00', 50);
insert into hashtag_by_lang (lang, hashtag, hour, num) values ('pt', 'lele', '2016-07-17 20:00', 10);
insert into hashtag_by_lang (lang, hashtag, hour, num) values ('en', 'lele', '2016-07-17 21:00', 11);
insert into hashtag_by_lang (lang, hashtag, hour, num) values ('pt', 'lele', '2016-07-17 22:00', 50);

-- Queries
select screen_name, num_followers from user where dummy_id = 1 order by num_followers desc limit 5;
select hashtag, hour, num from hashtag_by_lang where lang = 'pt';
select hour, num from hashtag_by_lang;
