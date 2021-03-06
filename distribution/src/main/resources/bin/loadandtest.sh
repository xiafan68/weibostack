#!/bin/bash
#scalability test
if [ $# -ne 3 ]
then
   echo "arg1:weibo query seed, arg2: twitter query seed, arg3:experiment out dir"
   exit
fi
wqueryseed=$1
tqueryseed=$2
odirP=$3

function scaleTest()
{
    dataDir=$1
    lsmi=$2
    lsmo=$3
    odir=$4
    idx=4
    while [ $idx -le 20 ] 
    do
	dataFile=${dataDir}/part${idx}
	echo "datafile is "${dataFile}
	echo "bin/indexloader.sh -l conf/log4j-server2.properties -c ${lsmi} -d $dataFile"
	bin/indexloader -l conf/log4j-server2.properties -c ${lsmi} -d $dataFile -e ${odir}/part${idx}_lsmi
	
	sleep 10
	echo "bin/perftest -e ${odir}/part${idx} -c ${lsmi} -q ${tqueryseed} -s single"
	#bin/perftest -e ${odir}/part${idx} -c ${lsmi} -q ${tqueryseed} -s single
	
	sleep 10
	echo " bin/indexloader.sh -l conf/log4j-server2.properties -c ${lsmo} -d $dataFile"
	bin/indexloader -l conf/log4j-server2.properties -c ${lsmo} -d $dataFile -e ${odir}/part${idx}_lsmo
	
	sleep 10
	echo "bin/perftest -e ${odir}/part${idx} -c ${lsmo} -q ${tqueryseed} -s single"
	#bin/perftest -e ${odir}/part${idx} -c ${lsmo} -q ${tqueryseed} -s single
	idx=`expr ${idx} + 4`
	sleep 10
    done
}

function scaleTestWeibo()
{
    dataDir=$1
    lsmi=$2
    lsmo=$3
    odir=$4
    idx=4
    while [ $idx -le 20 ] 
    do
	dataFile=${dataDir}/part${idx}
	echo "datafile is "${dataFile}
	echo "bin/indexloader.sh -l conf/log4j-server2.properties -c ${lsmi} -d $dataFile"
	bin/indexloader -l conf/log4j-server2.properties -c ${lsmi} -d $dataFile -e ${odir}/part${idx}_lsmi
	
	sleep 10
	echo "bin/perftest -e ${odir}/part${idx} -c ${lsmi} -q ${tqueryseed} -s single"
	bin/perftest -e ${odir}/part${idx}_lsmi_query -c ${lsmi} -q ${wqueryseed} -s facts
	
	sleep 10
	echo " bin/indexloader.sh -l conf/log4j-server2.properties -c ${lsmo} -d $dataFile"
	bin/indexloader -l conf/log4j-server2.properties -c ${lsmo} -d $dataFile -e ${odir}/part${idx}_lsmo
	
	sleep 10
	echo "bin/perftest -e ${odir}/part${idx} -c ${lsmo} -q ${tqueryseed} -s single"
	bin/perftest -e ${odir}/part${idx}_lsmo_query -c ${lsmo} -q ${wqueryseed} -s facts
	idx=`expr ${idx} + 4`
	sleep 10
    done
}
dataDir="/home/xiafan/data/weibo/sortedsegs"
t_lsmi="conf/weibo/scale/index_lsmi_weibo.conf"
t_lsmo="conf/weibo/scale/index_lsmo_weibo.conf"
$(scaleTestWeibo $dataDir $t_lsmi $t_lsmo "${odirP}/weibo")

#dataDir="/home/xiafan/data/twitter/sortedsegs"
#t_lsmi="conf/twitter/scale/index_lsmi_twitter.conf"
#t_lsmo="conf/twitter/scale/index_lsmo_twitter.conf"
#$(scaleTest $dataDir $t_lsmi $t_lsmo "${odirP}/twitter")

