#!/bin/bash
redis-server /usr/local/redis6/conf/redis.conf &
tail -f /usr/local/redis6/logs/redis_6379.log
