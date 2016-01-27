#!/bin/sh

SCRIPT=$(readlink -f "$0")
# Absolute path this script is in, thus /home/user/bin
SCRIPTPATH=$(dirname $(dirname "$SCRIPT"))
echo $SCRIPTPATH

dataFile="/home/xiafan/data/weibo/sortedsegs"
for conf in ${SCRIPTPATH}/conf/weibo/*
do
    echo "loading file ${conf}"
    bin/indexloader -l conf/log4j-server2.properties -c $conf -d $dataFile
done

dataFile="/home/xiafan/data/twitter/sortedsegs"
for conf in ${SCRIPTPATH}/conf/twitter/*
do
    bin/indexloader -l conf/log4j-server2.properties -c $conf -d $dataFile
done
