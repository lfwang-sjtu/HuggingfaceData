# HuggingfaceData

### 启动所有主机
zzzzzz

### 登陆主机
```
ssh root@ip
```

### 传输文件
```
scp -r apache-storm-1.2.4 root@ip:/root
```

### 启动zookeeper集群
在每台主机上执行
```
zkServer.sh start
zkServer.sh status
```
### 启动Storm
在主节点上启动nimbus, ui, logview，在从节点上启动supervisor, logviewer
```
# run nimbus(master)
nohup sh storm nimbus &
# run supervisor(slave)
nohup sh storm supervisor &
# run logviewer 
nohup sh storm logviewer &
# run ui  
nohup sh storm ui &
```

### 启动hive服务器
nohup ./hive --service hiveserver2 &
beeline -u jdbc:hive2://master:10000 --verbose=true

### 启动kafka
在主节点执行
```
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```

### 部署拓扑程序，监听kafka输入
本地编译
```
mvn clean compile assembly:single
```
scp发送jar包
```
scp target/preprocess-1.0-SNAPSHOT-jar-with-dependencies.jar root@ip:/root/myjar
```

部署运行topology
```
storm jar xxx.jar org.example.KafkaJsonTopology
```

### 启动python脚本
send.py

### 停止
- 停止storm
jps，kill
lsof -i :8080，kill
- 停止zookeeper zkServer.sh stop
```
ps -aux | grep nimbus
ps -aux | grep supervisor
ps -aux | grep ui
ps -aux | grep logviewer
ps -aux | grep drpc
```

### git?

```
git branch testing # new branch
git log --oneline --decorate --graph --all # view
git checkout testing # switch to branch
git commit -a -m 'msg' # add + commit

git commit, git commit
git merge
手动编辑
git add
git commit
```

### 端口关联
```
ssh -L 8080:127.0.0.1:8080 root@10.119.12.217
```

### 开放安全组的端口
```
nimbus 6627
supervisor 6700-6703
zookeeper 2181, logviewer 8000
```

###
```json
    {
        "modelId": "albert-base-v1",
        "pipeline_tag": "fill-mask",
        "author": null,
        "likes": 1,
        "downloads": 73054,
        "library_name": "transformers"
    }
```
### 调试用hql命令
```sql
INSERT INTO TABLE model1 VALUES('shitauweh', 'awiheu', 0, 0, ARRAY('datasetsStr','aowei','awuefi'), 'authqiwueifor','librwehfary_name');

INSERT INTO TABLE model1 VALUES('albert-base-v1', 'fill-mask', 'null_author', 'transformers', '1', '73054', ARRAY('bookcorpus', wikipedia'))
```