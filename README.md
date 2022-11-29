##  Microservicos e Mensageria com Spring Cloud e Docker

##### Este é um curso para iniciar no mundos dos microservices com Java, utilizando os frameworks Spring Cloud e Boot.
Link do curso:  <a href="https://www.udemy.com/course/domine-microservicos-e-mensageria-com-spring-cloud-e-docker/" target="_blank">Domine Microservicos e Mensageria com Spring Cloud e Docker</a>

Os assuntos abordados no curso são:
* Módulos Spring Cloud/Boot
* Entendimento e implementação de uma arquitetura completa de microservices
* Service Discovery
* Api Gateway
* Balanceamento de Carga
* Desenvolvimento de Microservices
* Comunicação Sícrona e Assícrona de Microservices
* Serviço/Fila de Mensageria com RabbitMQ
* Authorization Server com Keycloak
* Desenvolvimento de Imagens Docker
* Criar Containers Docker a partir das imagens customizadas
* Criar Réplicas dos Microservices

#### Comandos básicos:

#### Build projetos:

###### Cria o arquivo .jar e imagem docker.

```markdown
mvn clean package -Pdocker
```

###### Windows
```markdown
.\mvnw clean package -Pdocker 
```

###### Criar container

>Os comandos abaixo deverão ser executados somente após a criação das imagem docker de cada microserviços. Executando o comando acima em cada projeto.

>Verificar disponibilidade das portas no seu computador local. Caso necessite alterar, verificar o impacto da mudança.

Criando a imagem do Keycloak
```markdown
docker run -p 9091:8080 --name keycloak18 --network msnetwork -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:18.0.0 start-dev
```

Criando a imagem do RabbtMQ
```markdown
docker run -p 9091:8080 --name keycloak18 --network msnetwork -e docker run --name rabbitmq -p 5672:5672 -p 15672:15672 --network msnetwork -d rabbitmq:3.11-management
```

Criando a imagem do Eureka
```markdown
docker run --name eurekaserver --network msnetwork -p 8761:8761 -d eurekaserver:1.0.0
```

Criando a imagem do Cloud Gateway
```markdown
docker run --name mscloudgateway -p 8080:8080 --network msnetwork -e EUREKA_SERVER=eurekaserver -e KEYCLOAK_SERVER=keycloak18 -e KEYCLOAK_PORT=8080 -d mscloudgateway:1.0.0
```

Criando a imagem do microserviço de Cliente
```markdown
docker run --name msclientes --network msnetwork -e EUREKA_SERVER=eurekaserver -d msclientes:1.0.0
```

Criando a imagem do microserviço de Cartões
```markdown
docker run --name mscartoes --network msnetwork -e RABBITMQ_SERVER=rabbitmq -e EUREKA_SERVER=eurekaserver -d mscartoes:1.0.0
```

Criando a imagem do microserviço de Avalidor de crédito
```markdown
docker run --name msavaliadorcredito --network msnetwork -e RABBITMQ_SERVER=rabbitmq -e EUREKA_SERVER=eurekaserver -d msavaliadorcredito:1.0.0
```




###### Criar rede no docker para comunicação dos microserviços:
```markdown
docker network create msnetwork
```
