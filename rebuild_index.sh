#!/bin/sh
# 停止正在运行的searchd
searchd -c /usr/local/sphinx/etc/caritMarket.conf.conf  --stop
#建立主索引
indexer --all -c /usr/local/sphinx/etc/caritMarket.conf.conf
#启动searchd守护程序
searchd -c /usr/local/sphinx/etc/caritMarket.conf.conf