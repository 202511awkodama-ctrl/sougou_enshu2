# Worklog Lite

1時間デモ用のミニ勤怠・作業ログアプリです。Java 21、Spring Boot、Maven、Thymeleaf、Spring Data JPA、H2 Database を使います。

## 起動方法

```bash
./mvnw spring-boot:run
```

Mavenがインストール済みの場合は、代わりに次のコマンドでも起動できます。

```bash
mvn spring-boot:run
```

アプリ起動後、ブラウザで `http://localhost:8080/logs` を開きます。

## Render へのデプロイ

`render.yaml` を使って Web Service としてデプロイできます。

1. Render でこのリポジトリを接続します。
2. New から `Blueprint` を選び、`render.yaml` を読み込みます。
3. 作成される Web Service の build command は `./mvnw -DskipTests package`、start command は `java -jar target/worklog-lite-0.0.1-SNAPSHOT.jar` です。
4. デプロイ後は公開URLの `/logs` を開きます。

Render 上では `PORT` 環境変数を使って起動します。アプリ側は `server.port=${PORT:8080}` に対応済みです。

## テスト方法

```bash
./mvnw test
```

Mavenがインストール済みの場合:

```bash
mvn test
```

## 主要URL

- `GET /` - `/logs` にリダイレクト
- `GET /logs` - 作業ログ一覧
- `GET /logs?date=yyyy-MM-dd` - 日付で絞り込み、日別合計時間を表示
- `GET /logs/new` - 作業ログ作成
- `GET /logs/{id}/edit` - 作業ログ編集
- `POST /logs/{id}/delete` - 作業ログ削除
- `GET /h2-console` - H2コンソール

## デモ手順

1. `./mvnw spring-boot:run` で起動します。
2. `http://localhost:8080/logs` を開き、サンプルデータ、今日、登録件数を確認します。
3. 日付フィルタで今日の日付を選び、合計時間が表示されることを確認します。
4. 「新規作成」から作業ログを登録します。
5. 登録したログを「編集」で変更し、一覧に戻って反映を確認します。
6. 不要なログを「削除」で消し、件数が変わることを確認します。
7. 時間を逆に入力して保存し、エラーメッセージを確認します。

## H2接続情報

- JDBC URL: `jdbc:h2:mem:worklogdb`
- User Name: `sa`
- Password: 空欄

## 実装範囲

- 作業ログの一覧、作成、編集、削除
- 必須項目バリデーション
- `startTime < endTime` のバリデーション
- 日付絞り込み
- 日付絞り込み時の日別合計作業時間
- 起動時のサンプルデータ5件投入

## 制限事項

- 認証、ユーザー管理、外部DB、Docker、REST API専用構成はありません。
- H2のインメモリDBを使うため、アプリを停止すると登録データは消えます。
