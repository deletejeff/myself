#!/bin/bash
ps -ef | grep myself.jar | grep -v 'grep' |awk '{print $2}'|xargs kill -9
