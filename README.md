# HuggingfaceData

### 启动所有主机
zzzzzz

### 启动zookeeper集群
在每台主机上执行
```
zkServer.sh start
zkServer.sh status
```
### 启动Storm
在主节点上启动nimbus, ui, logview，在从节点上启动supervisor, logviewer
```
# Nimbus 进程启动：
storm nimbus &
# Supervisor 进程启动：
storm supervisor &
# UI 进程启动：
storm ui &
# Log Viewer 进程启动：
storm logviewer &
```

### 启动kafka
在主节点执行
```
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```

### 部署拓扑程序，监听kafka输入
在主节点部署storm，还没做😵

### 启动python脚本
send.py

### 停止
- 停止storm
jps，kill
lsof -i :8080，kill
- 停止zookeeper zkServer.sh stop