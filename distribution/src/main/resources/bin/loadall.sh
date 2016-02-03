#!/bin/sh

SCRIPT=$(readlink -f "$0")
# Absolute path this script is in, thus /home/user/bin
SCRIPTPATH=$(dirname $(dirname "$SCRIPT"))
echo $SCRIPTPATH

dataFile="/home/xiafan/data/weibo/part20"
for conf in ${SCRIPTPATH}/conf/weibo/ttest/*
do
<<<<<<< HEAD
    echo "loading file ${conf}"
    bin/indexloader -l conf/log4j-server2.properties -c $conf -d $dataFile
=======
    bin/indexloader.sh -l conf/log4j-server2.properties -c $conf -d $dataFile -e ~/expr/weibo_throughput -s $1
>>>>>>> c0539989dcd6de93f79681f2eac0d8038a7a3153
done

dataFile="/home/xiafan/data/twitter/part20"
for conf in ${SCRIPTPATH}/conf/twitter/ttest/*
do
<<<<<<< HEAD
    bin/indexloader -l conf/log4j-server2.properties -c $conf -d $dataFile
=======
    bin/indexloader.sh -l conf/log4j-server2.properties -c $conf -d $dataFile -e ~/expr/twitter_throughput -s $2
>>>>>>> c0539989dcd6de93f79681f2eac0d8038a7a3153
done
