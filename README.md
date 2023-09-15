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

* 初期ユーザーとして、以下のアカウントが作成されているのユーザーとクライアントの管理を行うことが可能

  | ログインID | パスワード |
    |-------------------|-------|
  | admin@example.com | admin |

## 機能

* ユーザーの登録と認証
* アクセストークンの発行と管理