#!/bin/sh

SCRIPT=$(readlink -f "$0")
# Absolute path this script is in, thus /home/user/bin
SCRIPTPATH=$(dirname "$SCRIPT")
echo $SCRIPTPATH

dataFile="/home/xiafan/data/weibo/part20"
for conf in ${SCRIPTPATH}/conf/weibo/ttest/*
do
    bin/indexloader.sh -l conf/log4j-server2.properties -c $conf -d $dataFile -e ~/expr/weibo_throughput -s $1
done

dataFile="/home/xiafan/data/twitter/part20"
for conf in ${SCRIPTPATH}/conf/twitter/ttest/*
do
    bin/indexloader.sh -l conf/log4j-server2.properties -c $conf -d $dataFile -e ~/expr/twitter_throughput -s $2
done
