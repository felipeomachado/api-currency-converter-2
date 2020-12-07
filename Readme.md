# API de Conversão de Moedas

Api que realiza a conversão entre duas moedas utilizando taxas de conversões atualizadas 

### Moedas suportadas
Você pode fazer a conversão entre mais de 30 moedas. Seguem alguns exemplos:
- BRL - Real Brasileiro
- USD - Dólar dos Estados Unidos
- CAD - Dólar Canadense
- EUR - Euro
 
 A lista completa de moedas suportadas pode ser [consultada aqui](https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html)  

### Como funciona
A aplicação foi desenvolvida com:
 - Kotlin - linguagem de programação mais limpa e menos verbosa que o java
 - Javalin - framework web leve para kotlin e java. Suporta WebSockets, Http2 e requisições assíncronas.
 - Koin - framework de injeção de dependência para kotlin
 - Exposed - framework ORM para kotlin 
 - H2 - banco de dados de fácil configuração
 - Retrofit - cliente http para kotlin e java que auxilia o consumo de outras apis  

Testes:
 - Junit - framework open-source para auxiliar nos testes
 - Mockk - biblioteca  para "mock" que funciona com kotlin
 
 **Estrutura**
```
 + api/
    + response/
        Classes que representam a resposta da chamada da api externa para obtenção das taxas
    + services/
        Interfaces que representam o serivço de consumo de api externas
 + controllers/
    Classes que representam a camada mais externa da aplicação. Possuem o processamento das requisições e respectivas respostas
 + db/
    Classes com os objetos que representam os dominios que serão persistidos e recuperados no banco de dados
 + entities/
    Clases que representam as entidades no sistema
 + repositories/
    Classes de acesso ao banco de dados para persistência e recuperação de informações
 + services/
    Classes que possuem a lógica de negócio da aplicação
 + utils/
    Classes utilitárias em geral
 + validators/
    Classes de validação das requisições
 - Main.kt <- A classe Principal
```
 
### Executando a aplicação
Voce precisará:
 - Java na versão 1.8
 - Maven
 
 O servidor está configurado para iniciar na porta 7000
```
-- faça um clone deste repositório:
$ git clone https://github.com/felipeomachado/api-currency-converter.git

-- rode o comando maven abaixo:
$ mvn package

-- execute a aplicação:
$ java -jar ./target/api-currency-converter-1.0-SNAPSHOT-jar-with-dependencies.jar

-- a aplicação rodará em http://localhost:7000
```


## Endpoints 
**POST: /api/v1/convert**<br>
Realiza a conversão do valor de uma moeda de origem para uma moeda de destino.<br>
Request:
```
{
    "userId":1,
    "sourceCurrency": "USD",
    "sourceValue": 3.0,
    "targetCurrency": "BRL"
}
```
Response:
```
{
    "transactionId": 1,
    "userId": 1,
    "sourceCurrency": "USD",
    "sourceValue": 3.0,
    "targetCurrency": "BRL",
    "targetValue": 15.4845793239,
    "conversionRate": 5.1615264413,
    "dateTime": "2020-12-06T11:22:01.418"
}
```

Caso seja passado algum dado inválido na requisição, será retornando erro 400 com uma das mensagens abaixo:
```
 - Id de usuário inválido: "Invalid User Id"
 - Moeda de origem inválida: "Invalid Source Currency"
 - Moeda de destino inválida: "Invalid Target Currency"
 - Moeda de origem não suportada: "Source currency 'ASD' was not supported"
 - Moeda de destino não suportada: "Target currency 'ASD' was not supported"
```
**GET - /api/v1/transactions/user/{userId}**<br>
Lista todas as transações realizadas por um usuário<br>
Request:
```
/api/v1/transactions/user/1
```
Response:
```
[
    {
        "transactionId": 1,
        "userId": 1,
        "sourceCurrency": "USD",
        "sourceValue": 3.0,
        "targetCurrency": "BRL",
        "targetValue": 15.4845793239,
        "conversionRate": 5.1615264413,
        "dateTime": "2020-12-06T11:25:55.147"
    },
    {
        "transactionId": 2,
        "userId": 1,
        "sourceCurrency": "EUR",
        "sourceValue": 4.0,
        "targetCurrency": "BRL",
        "targetValue": 25.1036,
        "conversionRate": 6.2759,
        "dateTime": "2020-12-06T11:26:09.147"
    }
]
```
Caso seja passado um id de usuário inválido, é retornanda a seguinte mensagem com status 400:

```
 Invalid User Id
```

### Live Demo
http://34.230.19.53:7000/