# kotlin-idp

## 使い方

1. dockerの起動

```shell
docker compose up -d
```

2. アプリケーションの起動

```shell
./gradlew bootRun
```

3. ログイン

* 初期ユーザーとして、
  ログインID: admin
  パスワード: admin
  のユーザーが作成されているのユーザーとクライアントの管理を行うことが可能

## 機能

* ユーザーの登録と認証
* アクセストークンの発行と管理