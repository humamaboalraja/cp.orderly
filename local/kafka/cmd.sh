#!/bin/bash

docker_compose_files=("zk" "kcluster" "create-kafka-topics")

function action() {
  for docker_compose_file in "${docker_compose_files[@]}"; do
    docker-compose --env-file ./.cp.orderly.env -f network.yml -f "$docker_compose_file".yml "$1" "$2"
  done
}

function setup_volumes() {
  mkdir volumes && cd volumes
  mkdir brokers && cd brokers && mkdir b-1 b-2 b-3
  cd ../ && mkdir zk && cd zk
  mkdir data transactions
  cd ../../
}

function init_kafka_cluster_and_topics() {
    setup_volumes
    cd src
    action "up" "-d"
}

function launch_kafka_cluster_and_topics() {
    action "up" "-d"
}

function shut_down_kafka_cluster() {
  action "down" "--remove-orphans"
}

if [ "$1" == "init-up" ]; then
    init_kafka_cluster_and_topics
    echo ruok | nc localhost 2181 && open "http://localhost:9000"
fi

if [ "$1" == "up" ]; then
    cd src
    launch_kafka_cluster_and_topics
fi

if [ "$1" == "down" ]; then
  cd src
  shut_down_kafka_cluster
fi

if [ "$1" == "kafka-reset" ]; then
  cd src
  shut_down_kafka_cluster
  cd ../
  rm -rf volumes
  docker system prune -a
fi
