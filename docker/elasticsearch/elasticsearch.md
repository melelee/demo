# elasticsearch集群安装及数据迁移说明
## elasticsearch集群安装
* 基于docker的3节点集群，每个节点均具有master资格.
* docker engine版本建议20.10.24.
* 容器网络均使用host模式，映射宿主机端口.
* elasticsearch版本为7.17.18.
* 按照官方文档配置服务器:[官方文档](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docker.html#docker-prod-prerequisites).
* [胎教级Elasticsearch集群](https://blog.csdn.net/weixin_44742630/article/details/120533544)
### 步骤
1. 生成es自身的密钥库keystore,参考[文档](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/elasticsearch-keystore.html).
2. 生成安全证书，参考[文档](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/security-basic-setup.html).
3. 将以上两步生成的elastic-certificates.p12和elasticsearch.keystore文件拷贝至config文件夹中（需保证文件的访问权限）.
4. 修改配置文件elasticsearch.yml，内容参考如下,每个节点node.name需不同：
    ```yaml
    cluster.name: docker-es-cluster
    node.name: node-151
    bootstrap.memory_lock: true
    network.host: 0.0.0.0
    
    discovery.seed_hosts:
       - 172.16.1.151:9300
       - 172.16.1.153:9300
       - 172.16.1.154:9300
    cluster.initial_master_nodes:
       - node-151
       - node-153
       - node-154
    xpack.security.enabled: true
    xpack.security.transport.ssl.enabled: true
    xpack.security.transport.ssl.verification_mode: certificate
    xpack.security.transport.ssl.client_authentication: required
    xpack.security.transport.ssl.keystore.path: elastic-certificates.p12
    xpack.security.transport.ssl.truststore.path: elastic-certificates.p12
    #关闭geoip自动更新机制，否则有可能因为网络问题导致更新失败，报错
    #ingest.geoip.downloader.enabled: false
    #指定reindex的源集群
    #reindex.remote.whitelist: "172.16.1.105:9200"
    ```
5. 使用命令直接拉起并启动es容器
    ```shell
    docker run \
      --name elasticsearch \
      -e "bootstrap.memory_lock=true" \
      -e "ES_JAVA_OPTS=-Xms3g -Xmx3g" \
      -e "KEYSTORE_PASSWORD=3iUc5amzYvmktxruHoxc" \
      --ulimit memlock=-1:-1 \
      --ulimit nofile=65535:65535 \
      --ulimit nproc=65535:65535 \
      -v /home/elasticsearch/data:/usr/share/elasticsearch/data \
      -v /home/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
      -v /home/elasticsearch/config/elasticsearch.keystore:/usr/share/elasticsearch/config/elasticsearch.keystore \
      -v /home/elasticsearch/config/elastic-certificates.p12:/usr/share/elasticsearch/config/elastic-certificates.p12 \
      -v /home/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
      --network host \
      --restart always \
      -d elasticsearch:7.17.18
    ```
## elasticsearch跨集群数据迁移
* 目前官方提供的elasticsearch跨集群迁移优雅的方案共有3个.
  1. [Reindex from a remote cluster](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/reindex-upgrade-remote.html)，[Reindex from remote](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/docs-reindex.html#reindex-from-remote)
  2. [Snapshot and restore](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/snapshot-restore.html)
  3. [logstash](https://www.elastic.co/guide/en/logstash/7.17/introduction.html)
* 本次迁移涉及数据量较少，使用方案1。
    ```json
    POST _reindex
    {
      "source": {
        "remote": {
          "host": "http://ip:port",
          "username": "username",
          "password": "password"
        },
        "index": "source_index_name"
      },
      "dest": {
        "index": "target_index_name"
      },
      "max_docs_per_second": 500, // 每秒处理的最大文档数
      "requests_per_second": 20   // 控制向ES发送请求的速度
    }
    ```
