#!/bin/bash
nohup java -jar /home/myself/myself.jar -Xms256m -Xmx512m > /home/myself/logs/myself.out 2>&1 &
tail -f  /home/myself/logs/myself.out
