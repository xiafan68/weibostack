#!/bin/sh

if [ $# -ne 3 ]
then
   echo "arg1:weibo query seed, arg2: twitter query seed, arg3:experiment out dir"
   exit
fi
wqueryseed=$1
tqueryseed=$2
odir=$3

dataDir="/home/xiafan/data/twitter/sortedsegs"
t_lsmi="conf/twitter/scale/index_lsmi_twitter.conf"
t_lsmo="conf/twitter/scale/index_lsmo_twitter.conf"
idx=4
for partDir in ${dataDir}:
do
    sleep 10
    bin/indexloader.sh -l conf/log4j-server2.properties -c ${t_lsmi} -d $dataFile
    sleep 10
    bin/perftest -e ${odir}/part${idx} -c ${t_lsmi} -q ${tqueryseed} -s single
    sleep 10
    bin/indexloader.sh -l conf/log4j-server2.properties -c ${t_lsmo} -d $dataFile
    sleep 10
    bin/perftest -e ${odir}/part${idx} -c ${t_lsmo} -q ${tqueryseed} -s single
    idx=`expr ${idx} + 4`
done
