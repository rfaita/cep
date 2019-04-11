# CEP MicroService

Este componente possui o intuito de fornecer um serviço autônomo de consulta de CEP.

OBS.: Os dados de CEP são obtidos do site [CEP Aberto](http://www.cepaberto.com/)

## Requisitos

 - jdk 8 instalado
 - maven instalado
 - docker instalado
 - docker-compose instalado

## Utilização

Será necessário configurar a chave de acesso do google maps no arquivo ***application.yml***,
caso deseje utilizar este recurso.

```yml
cep:
  maps:
    apiKey: <CHAVE_ACESSO_AQUI>
    enabled: true

```

Caso não queria utilizar, modificar a flag ***enabled*** para ***false***

```yml
cep:
  maps:
    apiKey: QQ_COISA_AQUI
    enabled: false

```


Compile o projeto:

```sh
mvn clean install
```

E inicie o mesmo com o comando:

```sh
cd cep-server
docker-compose up --build
```

O acesso e feito pela porta 8080, exemplo: http://localhost:8080

Acesse o endereço http://localhost:8080/swagger-ui.html para explorar os serviços da API.

Antes de realizar as consulta utilize a url http://localhost:8080/api/cepaberto/build para
realizar a importação dos arquivos do CEP Aberto.

Utilize durante a importação o usuário ***admin*** e a senha ***admin123***.

Caso necessite configurar o usuário e senha, altere o arquivo ***application.yml***:

```yml

cep:
    security:
        user: admin
        pass: {noop}novopass

```

OBS.: O ***{noop}*** é obrigatório para o correto funcionamento.