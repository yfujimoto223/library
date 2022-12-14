DROP TABLE IF EXISTS 出版社;
DROP TABLE IF EXISTS 書籍;
DROP TABLE IF EXISTS 予約者;
DROP TABLE IF EXISTS 管理者;
DROP TABLE IF EXISTS 図書館;
DROP TABLE IF EXISTS リクエスト書籍;
DROP TABLE IF EXISTS 貸出状況;
DROP TABLE IF EXISTS 管理者から利用者へのメッセージ;
DROP TABLE IF EXISTS 注文;
DROP TABLE IF EXISTS 注文明細;
DROP TABLE IF EXISTS 販売者;

CREATE TABLE 出版社
(
   出版社ID IDENTITY NOT NULL,
   出版社名 VARCHAR (100),
   PRIMARY KEY (出版社ID)
);
CREATE TABLE 書籍
(
   書籍ID IDENTITY NOT NULL,
   書名 VARCHAR (100),
   著者 VARCHAR (100),
   価格 BIGINT,
   出版社ID BIGINT NOT NULL,
   出版年 DATE,
   登録日 DATE,
   PRIMARY KEY (書籍ID),
   FOREIGN KEY (出版社ID) REFERENCES 出版社 (出版社ID) ON UPDATE RESTRICT
);
CREATE TABLE 予約者
(
   予約者ID IDENTITY NOT NULL,
   貸出券番号 BIGINT,
   名前 VARCHAR (100),
   ふりがな VARCHAR (100),
   住所 VARCHAR (100),
   郵便番号 VARCHAR (14),
   EMAIL VARCHAR (128),
   パスワード VARCHAR (30),
   PRIMARY KEY (予約者ID)
);
CREATE TABLE 管理者
(
   管理者ID IDENTITY NOT NULL,
   管理者番号 BIGINT,
   名前 VARCHAR (100),
   ふりがな VARCHAR (100),
   住所 VARCHAR (100),
   郵便番号 VARCHAR (14),
   EMAIL VARCHAR (128),
   パスワード VARCHAR (30),
   PRIMARY KEY (管理者ID)
);
CREATE TABLE 販売者
(
   販売者ID IDENTITY NOT NULL,
   販売者番号 BIGINT,
   名前 VARCHAR (100),
   ふりがな VARCHAR (100),
   住所 VARCHAR (100),
   郵便番号 VARCHAR (14),
   EMAIL VARCHAR (128),
   パスワード VARCHAR (30),
   PRIMARY KEY (販売者ID)
);
CREATE TABLE リクエスト書籍
(
   リクエストID IDENTITY NOT NULL,
   予約者ID BIGINT NOT NULL,
   ISBN BIGINT NOT NULL UNIQUE,
   書名 VARCHAR (100),
   著者 VARCHAR (100),
   価格 BIGINT,
   出版社名 VARCHAR (100),
   出版年 DATE,
   リクエスト日 DATE,
   PRIMARY KEY (リクエストID),
   FOREIGN KEY (予約者ID) REFERENCES 予約者 (予約者ID) ON UPDATE RESTRICT
);
CREATE TABLE 貸出状況
(
   予約ID IDENTITY NOT NULL,
   予約者ID BIGINT NOT NULL,
   書籍ID BIGINT NOT NULL,
   申込日 DATE,
   返却予定日 DATE,
   貸出ステータス BIGINT,
   PRIMARY KEY (予約ID),
   FOREIGN KEY (書籍ID) REFERENCES 書籍 (書籍ID) ON UPDATE RESTRICT,
   FOREIGN KEY (予約者ID) REFERENCES 予約者 (予約者ID) ON UPDATE RESTRICT
);
CREATE TABLE 管理者から利用者へのメッセージ
(
   メッセージID IDENTITY NOT NULL,
   管理者ID BIGINT NOT NULL,
   予約者ID BIGINT NOT NULL,
   内容 VARCHAR (200),
   登録日 DATE,
   PRIMARY KEY (メッセージID),
   FOREIGN KEY (管理者ID) REFERENCES 管理者 (管理者ID) ON UPDATE RESTRICT,
   FOREIGN KEY (予約者ID) REFERENCES 予約者 (予約者ID) ON UPDATE RESTRICT
);
CREATE TABLE 注文
(
   注文ID IDENTITY NOT NULL,
   管理者ID BIGINT NOT NULL,
   注文日 DATE NOT NULL,
   注文金額 BIGINT NOT NULL,
   PRIMARY KEY (注文ID),
   FOREIGN KEY (管理者ID) REFERENCES 管理者 (管理者ID) ON UPDATE RESTRICT
);
CREATE TABLE 注文明細
(
   注文明細ID IDENTITY NOT NULL,
   注文ID BIGINT NOT NULL,
   ISBN BIGINT NOT NULL UNIQUE,
   書名 VARCHAR (100),
   著者 VARCHAR (100),
   価格 BIGINT,
   出版社名 VARCHAR (100),
   PRIMARY KEY (注文明細ID),
   FOREIGN KEY (注文ID) REFERENCES 注文 (注文ID) ON UPDATE RESTRICT
);