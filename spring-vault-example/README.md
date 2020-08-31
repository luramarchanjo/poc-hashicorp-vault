# Overview

This is a proof of concept (POC) using [Spring Vault](https://spring.io/projects/spring-vault#overview) and 
[Hashicorp Vault](https://www.vaultproject.io/).

# Pre-requirements

* Maven 3.6 or later
* Java 11 or later
* Hashicorp Vault

# Testing

1º We need to start the Hashicorp Vault, as shown below:

```shell script
$ vault server -dev
```

2º We need to login into the Hashicorp Vault, as shown below:

```shell script
$ export VAULT_ADDR='http://127.0.0.1:8200'
$ export VAULT_TOKEN='YOUR ROOT VAULT TOKEN'
$ vault login $VAULT_TOKEN
```

3º We need to create the secret, as shown below:

```shell script
$ vault kv put secret/poc-spring-vault database.username=luramarchanjo database.password=1234567890
```

4º We need to start the application, as shown below:

First configure the environment variables:

```shell script
$ export VAULT_TOKEN='YOUR ROOT VAULT TOKEN'
$ export VAULT_SCHEME='http'
```

Then start the spring boot application:

```shell script
$ mvn clean spring-boot:run
```

Verify the logs:

```text
2020-08-31 10:56:23.050  INFO 2903 --- [           main] com.example.Application                  : Started Application in 1.908 seconds (JVM running for 2.542)
2020-08-31 10:56:23.053  INFO 2903 --- [           main] com.example.Application                  : 
2020-08-31 10:56:23.053  INFO 2903 --- [           main] com.example.Application                  : --------------------Properties--------------------
2020-08-31 10:56:23.053  INFO 2903 --- [           main] com.example.Application                  : database.username=luramarchanjo
2020-08-31 10:56:23.054  INFO 2903 --- [           main] com.example.Application                  : database.password=1234567890
2020-08-31 10:56:23.054  INFO 2903 --- [           main] com.example.Application                  : --------------------------------------------------
```

# Be Happy