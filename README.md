# Overview

This is a proof of concept (POC) using [Hashicorp Vault](https://www.vaultproject.io)

# What is Hashicorp Vault?

Vault is a tool for securely accessing secrets. A secret is anything that you want to tightly control access to, such as API keys, passwords, certificates, and more. Vault provides a unified interface to any secret, while providing tight access control and recording a detailed audit log.

A modern system requires access to a multitude of secrets: database credentials, API keys for external services, credentials for service-oriented architecture communication, etc. Understanding who is accessing what secrets is already very difficult and platform-specific. Adding on key rolling, secure storage, and detailed audit logs is almost impossible without a custom solution. This is where Vault steps in.

The key features of Vault are:

## [Secrets Management](https://www.vaultproject.io/use-cases/secrets-management)

**The Challenge**

Secrets for applications and systems need to be centralized and static IP-based solutions don't scale in dynamic environments with frequently changing applications and machines

**The Solution**

Vault centrally manages and enforces access to secrets and systems based on trusted sources of application and user identity

## [Data Encryption](https://www.vaultproject.io/use-cases/data-encryption)

**The Challenge**

All application data should be encrypted, but deploying a cryptography and key management infrastructure is expensive, hard to develop against, and not cloud or multi-datacenter friendly

**The Solution**

Vault provides encryption as a service with centralized key management to simplify encrypting data in transit and at rest across clouds and data centers

## [Identity-based Access](https://www.vaultproject.io/use-cases/identity-based-access)

**The Challenge**

With the proliferation of different clouds, services, and systems all with their own identity providers, organizations need a way to manage identity sprawl

**The Solution**

Vault merges identities across providers and uses a unified ACL system to broker access to systems and secrets

# [Install Vault](https://learn.hashicorp.com/tutorials/vault/getting-started-install?in=vault/getting-started)

1º Add the hashicorp key

```shell script
sudo curl -fsSL https://apt.releases.hashicorp.com/gpg | sudo apt-key add -
```

2º Add the hashicorp repository

```shell script
sudo apt-add-repository "deb [arch=amd64] https://apt.releases.hashicorp.com $(lsb_release -cs) main"
```

3º Update and install vault

```shell script
sudo apt-get update && sudo apt-get install vault
```

## Testing the installation

To test the installation run the command `vault` and will appear some options!

```shell script
$ vault 

Usage: vault <command> [args]

Common commands:
    read        Read data and retrieves secrets
    write       Write data, configuration, and secrets
    delete      Delete secrets and configuration
    list        List data or secrets
    login       Authenticate locally
    agent       Start a Vault agent
    server      Start a Vault server
    status      Print seal and HA status
    unwrap      Unwrap a wrapped secret

Other commands:
    audit          Interact with audit devices
    auth           Interact with auth methods
    debug          Runs the debug command
    kv             Interact with Vault's Key-Value storage
    lease          Interact with leases
    monitor        Stream log messages from a Vault server
    namespace      Interact with namespaces
    operator       Perform operator-specific tasks
    path-help      Retrieve API help for paths
    plugin         Interact with Vault plugins and catalog
    policy         Interact with policies
    print          Prints runtime configurations
    secrets        Interact with secrets engines
    ssh            Initiate an SSH session
    token          Interact with tokens
```

## Command Completion

Use the vault command to install command-line completions like this.

```shell script
vault -autocomplete-install
```

Once you have installed the completions, you should restart your terminal session or reload the shell script to begin using them.

```shell script
source ~/.bashrc
```

# Starting the Server

First, start a Vault dev server. The dev server is a built-in, pre-configured server that is not very secure but useful for playing with Vault locally.

```shell script
vault server -dev
```

Now that we start the dev server, launch a new terminal session.

```shell script
export VAULT_ADDR='http://127.0.0.1:8200'
```

Save the unseal key somewhere. Don't worry about how to save this securely. For now, just save it anywhere.

```shell script
export VAULT_TOKEN="<YOUR ROOT TOKEN HERE>"
```

Now we need to verify the Server is Running

```shell script
$ vault status

Key             Value
---             -----
Seal Type       shamir
Initialized     true
Sealed          false
Total Shares    1
Threshold       1
Version         1.5.0
Cluster Name    vault-cluster-4d862b44
Cluster ID      92143a5a-0566-be89-f229-5a9f9c47fb1a
HA Enabled      false
```

**Warning**

Do not run dev server in **production!**

# [Writing a Secret](https://learn.hashicorp.com/tutorials/vault/getting-started-first-secret?in=vault/getting-started#writing-a-secret)

Let's start by writing a secret. This is done very simply with the `vault kv` command, as shown below:

```shell script
$ vault kv put secret/hello foo=world

Key              Value
---              -----
created_time     2019-02-04T19:53:22.730733Z
deletion_time    n/a
destroyed        false
version          1
```

This writes the pair `foo=world` to the path `secret/hello`!

You can even write multiple pieces of data, as shown below:

```shell script
$ vault kv put secret/hello foo=world excited=yes

Key              Value
---              -----
created_time     2019-02-04T19:54:03.250328Z
deletion_time    n/a
destroyed        false
version          2
```

**Warning**

The examples in this guide use the `<key>=<value>` input to send secrets to Vault. However, sending data as a part of the CLI command often end up in the **shell script history unencrypted**. To avoid this, refer to the documentation or [Static Secrets: Key/Value Secrets Engine guide](https://learn.hashicorp.com/tutorials/vault/static-secrets#q-how-do-i-enter-my-secrets-without-exposing-the-secret-in-my-shell script-s-history) to learn different approaches.

# [Getting a Secret](https://learn.hashicorp.com/tutorials/vault/getting-started-first-secret?in=vault/getting-started#getting-a-secret)

As you might expect, secrets can be retrieved with `vault kv get`:

```shell script
$ vault kv get secret/hello

====== Metadata ======
Key              Value
---              -----
created_time     2019-02-04T19:54:03.250328Z
deletion_time    n/a
destroyed        false
version          2

===== Data =====
Key        Value
---        -----
excited    yes
foo        world
```

Vault gets the data from storage and decrypts it. The output format is purposefully whitespace separated to make it easy to pipe into a tool like `awk`.

# [Deleting a Secret](https://learn.hashicorp.com/tutorials/vault/getting-started-first-secret?in=vault/getting-started#deleting-a-secret)

Now that you've learned how to read and write a secret, let's go ahead and delete it. You can do so using the `vault kv delete` command.

```shell script
$ vault kv delete secret/hello

Success! Data deleted (if it existed) at: secret/hello
```

# [Token authentication](https://learn.hashicorp.com/tutorials/vault/getting-started-authentication?in=vault/getting-started#token-authentication)

Token authentication is automatically enabled. When you started the dev server, the output displayed a root token. The Vault CLI read the root token from the `$VAULT_TOKEN` environment variable. This root token can perform any operation within Vault because it is assigned the `root` policy. One capability is to create new tokens.

```shell script
$ vault token create

Key                  Value
---                  -----
token                s.iyNUhq8Ov4hIAx6snw5mB2nL
token_accessor       maMfHsZfwLB6fi18Zenj3qh6
token_duration       ∞
token_renewable      false
token_policies       ["root"]
identity_policies    []
policies             ["root"]
```

The token is created and displayed here as `s.iyNUhq8Ov4hIAx6snw5mB2nL`. Each token that Vault creates is unique.

When a token is no longer needed it can be revoked.

Revoke the first token you created.

```shell script
$ vault token revoke s.iyNUhq8Ov4hIAx6snw5mB2nL

Success! Revoked token (if it existed)
```

Attempt to login with the last token you created.

```shell script
$ vault login s.iyNUhq8Ov4hIAx6snw5mB2nL

Error authenticating: error looking up token: Error making API request.

URL: GET http://127.0.0.1:8200/v1/auth/token/lookup-self
Code: 403. Errors:

* permission denied
```

Login with the root token.

```shell script
$ vault login $VAULT_TOKEN

Success! You are now authenticated. The token information displayed below
is already stored in the token helper. You do NOT need to run "vault login"
again. Future Vault requests will automatically use this token.

Key                  Value
---                  -----
token                s.yFgycysoWKJTi9Dw4WcscWiO
token_accessor       qOWOm47ZHomnpr5klg4vGEEP
token_duration       ∞
token_renewable      false
token_policies       ["root"]
identity_policies    []
policies             ["root"]
```

# [Policies](https://learn.hashicorp.com/tutorials/vault/getting-started-policies?in=vault/getting-started)

Policies in Vault control what a user can access. For authentication Vault has multiple options or methods that can be enabled and used. Vault always uses the same format for both authorization and policies. All auth methods map identities back to the core policies that are configured with Vault.

There are some built-in policies that cannot be removed. For example, the root and `default` policies are required policies and cannot be deleted. The `default` policy provides a common set of permissions and is included on all tokens by default. The `root` policy gives a token super admin permissions, similar to a root user on a linux machine.

```shell script
$ vault policy list

default
root
```

## [Policy Format](https://learn.hashicorp.com/tutorials/vault/getting-started-policies?in=vault/getting-started#policy-format)

Policies are authored in [HCL](https://github.com/hashicorp/hcl), but are JSON compatible. Here is an example policy:

```
# Dev servers have version 2 of KV secrets engine mounted by default, so will
# need these paths to grant permissions:
path "secret/data/*" {
  capabilities = ["create", "update"]
}

path "secret/data/foo" {
  capabilities = ["read"]
}
```

## [Writing the Policy](https://learn.hashicorp.com/tutorials/vault/getting-started-policies?in=vault/getting-started#writing-the-policy)

Let's create the `my-policy.hcl` file, as shown below:

```
# Dev servers have version 2 of KV secrets engine mounted by default, so will
# need these paths to grant permissions:
path "secret/data/*" {
  capabilities = ["create", "update"]
}

path "secret/data/foo" {
  capabilities = ["read"]
}
```

Now that the file is create, write the policy:

```shell script
$ vault policy write my-policy my-policy.hcl

Success! Uploaded policy: my-policy
```

To see the list of policies, execute the following command.

```shell script
$ vault policy list

default
root
my-policy
```

To view the contents of a policy, execute the `vault policy` read command.

```shell script
$ vault policy read my-policy

# Dev servers have version 2 of KV secrets engine mounted by default, so will
# need these paths to grant permissions:
path "secret/data/*" {
  capabilities = ["create", "update"]
}

path "secret/data/foo" {
  capabilities = ["read"]
}
```

## [Testing the Policy](https://learn.hashicorp.com/tutorials/vault/getting-started-policies?in=vault/getting-started#testing-the-policy)

First, check to verify that KV v2 secrets engine has not been enabled at `secret/`.

```shell script
$ vault secrets list

Path          Type         Accessor              Description
----          ----         --------              -----------
cubbyhole/    cubbyhole    cubbyhole_b81986c7    per-token private secret storage
identity/     identity     identity_33dc5c7d     identity store
sys/          system       system_ad432442       system endpoints used for control, policy and debugging
```

If `secret/` is not listed, enable it before proceeding.

```shell script
vault secrets enable -path=secret/ kv-v2
```

To use the policy, create a token and assign it to that policy.

```shell script
$ vault token create -policy=my-policy

Key                  Value
---                  -----
token                s.C0FxR0eDCf1mkb0NzzRuWDO7
token_accessor       0B68xBxuxwSMTp1UbxxJEWjf
token_duration       768h
token_renewable      true
token_policies       ["default" "my-policy"]
identity_policies    []
policies             ["default" "my-policy"]
```

Copy the generated token value and authenticate with Vault.

```shell script
$ vault login s.C0FxR0eDCf1mkb0NzzRuWDO7

Success! You are now authenticated. The token information displayed below
is already stored in the token helper. You do NOT need to run "vault login"
again. Future Vault requests will automatically use this token.

Key                  Value
---                  -----
token                s.C0FxR0eDCf1mkb0NzzRuWDO7
token_accessor       0B68xBxuxwSMTp1UbxxJEWjf
token_duration       767h59m30s
token_renewable      true
token_policies       ["default" "my-policy"]
identity_policies    []
policies             ["default" "my-policy"]
```

Verify that you can write any data to `secret/data/`.

```shell script
$ vault kv put secret/creds password="my-long-password"

Key              Value
---              -----
created_time     2020-08-31T11:46:45.186427422Z
deletion_time    n/a
destroyed        false
version          1
```

Since my-policy only permits read from the `secret/data/foo` path, any attempt to write fails with "permission denied" error.

```shell script
$ vault kv put secret/foo robot=beepboop

Error writing data to secret/data/foo: Error making API request.

URL: PUT http://127.0.0.1:8200/v1/secret/data/foo
Code: 403. Errors:

* permission denied
```

# [Web UI](http://127.0.0.1:8200/ui/vault/auth?with=token)

# Be Happy