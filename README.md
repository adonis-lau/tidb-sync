## 同步 TiDB 数据的工具
使用 DataX 同步 TiDB 数据

|环境变量| 含义               |
|---|---|
|SPRING_PROFILES_ACTIVE| 要使用的配置文件         |
|SCHEDULED_DATAX_TASK_JOBPATH| datax job 配置文件路径 |
|SCHEDULED_DATAX_TASK_FIXEDDELAY| 在上一次任务执行完成后，等待指定的时间间隔再执行下一次任务，单位是毫秒 |


推送 Docker 镜像到 Harbor
docker login hub.sinoeyes.com
docker tag tidb-sync:latest hub.sinoeyes.com/data-technology/tidb-sync:latest
docker push hub.sinoeyes.com/data-technology/tidb-sync:latest
