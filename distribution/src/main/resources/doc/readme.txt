dump the seed users into kafka
bin/KafkaJobInit  10.11.1.212:9092 rData/Files/uid

nohup bin/publiccrawler  conf/RC.conf >public.log&985
nohup bin/repostcrawler conf/RC.conf >repost.log&1374
nohup bin/TKSearchServer  -c conf/server.conf -i conf/index_twitter_intern.conf> /dev/null&
等待tssearchserver启动之后，启动其余的线程
nohup bin/timeseriesgen -c 10.11.1.212 -k 10.11.1.212:9092 > /dev/null&
nohup bin/timeseriesseg -c 10.11.1.212 -k 10.11.1.212:9092 -l 10.11.1.212:10000> /dev/null&
nohup bin/wordstats -c 10.11.1.212 -k 10.11.1.212:9092 -r> /dev/null&11842
