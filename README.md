# color-retriever

## DBを起動する
### Redis
`redis`配下のシェルスクリプトを実行する。  
- Dockerコンテナを開始する .... `./run_container.sh`
- Dockerコンテナを終了する .... `./destroy_container.sh`
### DynamoDB
#### DBの起動
AWS公式のローカルDBjarを利用する。  
`java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb`
#### テーブルの作成
`javac -sourcepath src -cp sdk/*.jar -d dist src/DdbStarter.java`
