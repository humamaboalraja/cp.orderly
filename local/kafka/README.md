# Kafka cluster setup for MacOS's arm64 architecture
A tool for setting up a local Kafka cluster & Topics


### Cluster setup
1. run `./cmd.sh init-up` to create the volumes, network, images and run the containers
2. If everything went the script will automatically open [CMAK's Dashboard](http://localhost:9000/)
3. From the dashboard, navigate to clusters and add your cluster details
4. All you need to do to add it already provisioned cluster is to fill 
   1. `Cluster Name` - E.g. Orderly
   2. `Cluster Zookeeper Hosts` - E.g. zookeeper:2181 - refer to [zk.yml](./src/zk.yml)
5. For creating more topics refer to [create-kafka-topics.yml](./src/create-kafka-topics.yml) 

### **Commands**

Initialize Docker volumes & run zookeeper, kafka's cluster and create Kafka's topics
```bash
./cmd.sh init-up
```
Run zookeeper, kafka's cluster and create Kafka's topics

```bash
./cmd.sh up
```


Shut down zookeeper & Kafka's cluster
```bash
./cmd.sh down
```


Shut down zookeeper, Kafka's cluster, delete local volumes and delete Docker's resources
```bash
./cmd.sh kafka-reset
```

---

For checking the health of the ZooKeeper running image. [source](https://github.com/confluentinc/cp-docker-images/issues/827)
```bash
# expected result: imok%
echo ruok | nc localhost 2181
```


