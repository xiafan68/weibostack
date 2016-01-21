dump the seed users into kafka
bin/KafkaJobInit  10.11.1.212:9092 rData/Files/uid

nohup bin/publiccrawler  conf/RC.conf >public.log&29265
nohup bin/repostcrawler conf/RC.conf >repost.log&25234
nohup bin/TKSearchServer  -c conf/server.conf -i conf/index_twitter_intern.conf> index.log&6210
等待tssearchserver启动之后，启动其余的线程
nohup bin/timeseriesgen -c 10.11.1.212 -k 10.11.1.212:9092 -g 58.198.176.86:8649 > gen.txt&4394
nohup bin/timeseriesseg -c 10.11.1.212 -k 10.11.1.212:9092 -l 10.11.1.212:10000 -g 58.198.176.86:8649> seg.txt&12227
nohup bin/timeseriesseg -c 10.11.1.212 -k 10.11.1.212:9092 -l 10.11.1.212:10000 -g 58.198.176.86:8649>seg1.txt&31897
nohup bin/wordstats -c 10.11.1.212 -k 10.11.1.212:9092 -r> /dev/null&6394

nohup bin/retweetstats -c 10.11.1.212 -k 10.11.1.212:9092 -m rData/motion.txt -g 58.198.176.86:8649 -r> /dev/null&6455

bin/kafka-topics.sh --zookeeper localhost:9092 --alter --topic rtsupdate --partitions 4