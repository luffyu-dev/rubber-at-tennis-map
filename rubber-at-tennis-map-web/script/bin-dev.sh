#!/bin/bash
APP_NAME=rubber-at-tennis-map-web-1.0.0-SNAPSHOT.jar
APP_PUBLISH_PATH=/home/application/rubber-at-tennis-map/
APP_PUBLISH_LOG_PATH=/home/application/rubber-at-tennis-map/log/info.log

#使用说明，用来提示输入参数
usage() {
    echo "Usage: sh robotcenter.sh [start|stop|restart|status]"
    exit 1
}


# 是否存在这个app
is_exist(){
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}'`
  #如果不存在返回1，存在返回0
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

#启动方法
start(){
  is_exist
  if [ $? -eq 0 ]; then
    echo "${APP_NAME} is already running. pid=${pid}"
  else
    nohup java -jar ${APP_PUBLISH_PATH}${APP_NAME} --spring.profiles.active=dev > ${APP_PUBLISH_LOG_PATH}  2>&1 &
    sleep 3
    echo "app run success"
  fi
}


#停止方法
stop(){
  is_exist
  if [ $? -eq "0" ]; then
    kill -9 $pid
    echo "app stop success"
  else
    echo "${APP_NAME} is not running"
  fi
}


#输出运行状态
status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    echo "${APP_NAME} is NOT running."
  fi
}

#重启
restart(){
  stop
  sleep 1
  start
  sleep 1
  echo "app restart success"
}


#根据输入参数，选择执行对应方法，不输入则执行使用说明

case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac
