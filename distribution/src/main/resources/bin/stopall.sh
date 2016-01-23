#!/bin/sh
ps axu|grep weibo|awk '{print $2}'|xargs kill
