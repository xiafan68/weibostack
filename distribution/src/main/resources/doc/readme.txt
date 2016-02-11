dump the seed users into kafka
bin/KafkaJobInit  10.11.1.212:9092 rData/Files/uid

nohup bin/publiccrawler  conf/RC.conf >public.log&19017
nohup bin/repostcrawler conf/RC.conf >repost.log&20136
nohup bin/TKSearchServer  -c conf/server.conf -i conf/index_twitter_intern.conf> index.log&4909
等待tssearchserver启动之后，启动其余的线程
nohup bin/timeseriesgen -c 10.11.1.212 -k 10.11.1.212:9092 -g 58.198.176.86:8649 -r> gen.txt&4913
nohup bin/timeseriesseg -c 10.11.1.212 -k 10.11.1.212:9092 -l 10.11.1.212:10000 -g 58.198.176.86:8649> seg.txt&7984

nohup bin/timeseriesseg -c 127.0.0.1 -k 127.0.0.1:9092 -l 127.0.0.1:10000 -g 127.0.0.1:8649

nohup bin/dumpstatusintokafka -d /data/xiafan/data/weibo/eventstatus/statussumdist/ -k 10.11.1.212:9092 -r> /dev/null&

nohup bin/wordstats -c 10.11.1.212 -k 10.11.1.212:9092 -r> /dev/null&5534
nohup bin/wordstats -c 127.0.0.1 -k 127.0.0.1:9092 -r> /dev/null&
nohup bin/retweetstats -c 10.11.1.212 -k 10.11.1.212:9092 -m rData/motion.txt -g 58.198.176.86:8649 -r> /dev/null&5675

nohup bin/retweetstats -c 127.0.0.1 -k 127.0.0.1:9092 -m rData/motion.txt -g 127.0.0.1:8649 -r&
bin/kafka-topics.sh --zookeeper localhost:9092 --alter --topic rtsupdate --partitions 4


bin/loadandtest.sh /home/xiafan/KuaiPan/dataset/time_series/nqueryseed.txt /home/xiafan/KuaiPan/dataset/twitter_expr/keywordsbycount/queryseed.txt /home/xiafan/expr/scale