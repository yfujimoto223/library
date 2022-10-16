# プロジェクト名：図書館(仮)
このプロジェクト「図書館(仮)」はサンプルアプリとして開発を行いました。
開発期間は約二週間です。

### アプリ実行前に行うこと
アプリ開始後、H2Databaseが自動的に実行されます。
アプリを開始する前に、H2Databaseのコンソール画面で以下のフォルダに存在する「schema.sql」「data.sql」を実行してください。
>\liblary\src\main\resources


## 開発環境
* IDE:Eclipse IDE for Java Developers (includes Incubating components) Version: 2022-09 (4.25.0)
* フレームワーク：Spring Boot
* フロントエンドで使用した言語：HTML、Javascript、CSS
* バックエンドで使用した言語：Java17
* DB:H2 Database
* HTMLテンプレートエンジン：thymeleaf
* サーバー:Tomcat

## build.gradleの設定
> plugins {
> 	id 'org.springframework.boot' version '2.7.4'
> 	id 'io.spring.dependency-management' version '1.0.14.RELEASE'
> 	id 'java'
> }
> 
> group = 'com.example'
> version = '0.0.1-SNAPSHOT'
> sourceCompatibility = '17'
> 
> configurations {
> 	compileOnly {
> 		extendsFrom annotationProcessor
> 	}
> }
> 
> repositories {
> 	mavenCentral()
> }
> 
> dependencies {
> 	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
> 	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
> 	implementation 'org.springframework.boot:spring-boot-starter-web'
> 	implementation 'org.springframework.boot:spring-boot-starter-validation'
> 	implementation 'org.springframework.boot:spring-boot-starter-aop'
> 	compileOnly 'org.projectlombok:lombok'
> 	developmentOnly 'org.springframework.boot:spring-boot-devtools'
> 	runtimeOnly 'com.h2database:h2'
> 	annotationProcessor 'org.projectlombok:lombok'
> 	testImplementation 'org.springframework.boot:spring-boot-starter-test'
> }
> 
> tasks.named('test') {
> 	useJUnitPlatform()
> }

## アーキテクチャ
アーキテクチャには「MVCモデル」+「DCIアーキテクチャ」+「DDD(ドメイン駆動設計)」の設計モデルを取り入れています。
※各モデルの詳細な説明はWikipediaから引用しました。

### MVCモデル
MVCでは、プログラムを3つの要素、Model（モデル）、View（ビュー）、Controller（コントローラ）に分割する。

* ・モデル
そのアプリケーションが扱う領域のデータと手続き（ビジネスロジック - ショッピングの合計額や送料を計算するなど）を表現する要素である。また、データの変更をビューに通知するのもモデルの責任である（モデルの変更を通知するのにObserver パターンが用いられることもある）。
* ・ビュー
モデルのデータを取り出してユーザが見るのに適した形で表示する要素である。すなわち、UIへの出力を担当する。例えば、ウェブアプリケーションではHTML文書を生成して動的にデータを表示するためのコードなどにあたる。GUIにおいては通常、階層構造を成す。
* ・コントローラ
ユーザからの入力（通常イベントとして通知される）をモデルへのメッセージへと変換してモデルに伝える要素である。すなわち、UIからの入力を担当する。モデルに変更を引き起こす場合もあるが、直接に描画を行ったり、モデルの内部データを直接操作したりはしない。

### DCIアーキテクチャ
データ、コンテキスト、および相互作用( DCI ) は、通信オブジェクトのシステムをプログラムするためにコンピューター ソフトウェアで使用されるパラダイムです。
パラダイムは、ドメイン モデル(データ) をユース ケース(コンテキスト) およびオブジェクトが果たす役割 (相互作用) から分離します。
DCI は、モデル - ビュー - コントローラー(MVC) を補完します。

* ・データ
データは「システムが何であるか」のままです。
DCI アーキテクチャのデータ部分は、関係を持つ (比較的) 静的なデータ モデルです。
通常、データ設計は、システムの基本的なドメイン構造を表す従来のクラスとしてコード化されます。
* ・コンテキスト
コンテキストは、特定のアルゴリズム、シナリオ、またはユース ケースのロールと、実行時にこれらのロールをオブジェクトにマップし、ユース ケースを実行するコードをコードに含むクラス (またはそのインスタンス) です。
各ロールは、特定のユース ケースの制定中に、1 つのオブジェクトにバインドされます。ただし、1 つのオブジェクトが同時に複数の役割を果たしている場合があります。
* ・相互作用
相互作用は「システムが行うこと」です。
相互作用は、実行時にオブジェクトが果たすロールとして実装されます。
これらのオブジェクトは、データ (ドメイン) オブジェクトの状態とメソッドを、1 つ以上の役割のメソッド (ただし、役割はステートレスであるため、状態はありません) と結合します。
適切な DCI スタイルでは、ロールはその (方法のない) ロールの観点からのみ別のオブジェクトに対処します。

### DDD(ドメイン駆動設計)
ドメイン駆動設計（英語: domain-driven design、DDD）とは、ドメインの専門家からの入力に従ってドメインに一致するようにソフトウェアをモデル化することに焦点を当てるソフトウェア設計手法である。
オブジェクト指向プログラミングに関しては、ソースコード（クラス名・クラスメソッド・クラス変数）の構造と名称がビジネスドメインと一致させる必要があることを意味する。
例えばローンの申し込みを処理するソフトウェアには、LoanApplicationやCustomerなどのクラスと、AcceptOfferやWithdrawどのメソッドが含まれることになる。
ドメイン駆動設計は次の目標に基づいている。
プロジェクトの主な焦点をコアドメインとドメインロジックに置く。
ドメインのモデルに基づく複雑な設計。
特定のドメインの問題に対処する概念モデルを繰り返し改良するために、技術とドメインの専門家の間で創造的なコラボレーションを開始する。
以下のクラスに分割して実装されます。
* ・Aggregate・・・デザインパターンのAggregateを取り入れたドメインの集約を行うクラスです。
* ・Entity・・・DBから取得したデータの同一性を担保するクラスです。
* ・Specification・・・ビジネスルールなど複雑な仕様を実装するクラスです。
* ・ValueObject・・・DBから取得したデータを格納するデータ読み取り専用のクラスです。

### レイヤー階層
以下のレイヤーの順に処理が実行されます。
* 1.Controller層・・・MVCモデルを実装するレイヤーです。画面からのイベント、画面の入力値チェック、Usecase層のメソッド呼び出しを行います。
* 2.Usecase層・・・DCIアーキテクチャを実装するレイヤーです。MVCモデルのModelクラスのデータ受け渡し、Domain層のメソッド呼び出しなどを行います。
* 3.Domain層・・・DDD(ドメイン駆動設計)を実装するレイヤーです。メソッドの呼び出しはAggregateクラス経由のみで行います。
* 4.Data層・・・DBに関係する処理を格納するレイヤーです。DTO、DAOなどを実装します。

## DB構成について
DBの構成、概要について説明を行います。

### 出版社
書籍を管理している組織。

### 書籍
図書館で貸出可能な本。

### 予約者
本の貸出、返却、リクエストを行います。

### 管理者
「利用者」への本の貸出状況、リクエストされた書籍の注文などを行います。

### 管理者
「管理者」から発注された「リクエスト書籍」を納品します。

### リクエスト書籍
「予約者」からリクエストがあった書籍。

### 貸出状況
「予約者」が借りている書籍のステータス。

### 管理者から利用者へのメッセージ
「管理者」から「予約者」へのメッセージ。

### 注文
「管理者」が「販売者」へ発注した「リクエスト書籍」。

### 注文明細
「リクエスト書籍」の詳細情報。