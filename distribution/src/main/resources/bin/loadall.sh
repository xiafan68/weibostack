#!/bin/sh

SCRIPT=$(readlink -f "$0")
# Absolute path this script is in, thus /home/user/bin
SCRIPTPATH=$(dirname $(dirname "$SCRIPT"))
echo $SCRIPTPATH

dataFile="/home/xiafan/data/weibo/part20"
for conf in ${SCRIPTPATH}/conf/weibo/ttest/*
do
    echo "loading file ${conf}"
    #bin/indexloader -l conf/log4j-server2.properties -c $conf -d $dataFile
    fname=$(basename $conf)
    bin/throughputtest -c $conf -d $dataFile -e ~/expr/weibo_throughput/$fname -s $1
done

dataFile="/home/xiafan/data/twitter/part20"
for conf in ${SCRIPTPATH}/conf/twitter/ttest/*
do
    fname=$(basename $conf)
    #bin/indexloader -l conf/log4j-server2.properties -c $conf -d $dataFile
    bin/throughputtest -c $conf -d $dataFile -e ~/expr/twitter_throughput/$fname -s $2
done
