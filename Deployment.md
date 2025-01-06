### 部署文档

#### 创建工作空间目录
```shell
mkdir -p **/tidb-sync
mkdir -p **/tidb-sync/job
mkdir -p **/tidb-sync/logs
mkdir -p **/tidb-sync/config
cd **/tidb-sync
```

#### 准备配置文件
* 将 `docker-compose.yaml` 文件放入 `tidb-sync` 目录
* 将 `application-prd.properties` 文件放入 `tidb-sync/config` 目录
* 将 `job/*` 文件放入 `tidb-sync/job` 目录
* 修改 `*.enable.json` 文件中的数据库连接信息修改为TiDB生产环境的连接信息(**部署docker服务的机器要能用此账号访问TiDB生产环境**)

### 启动服务&查看日志
```shell
docker compose up -d && docker compose logs -f
```
