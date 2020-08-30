# Overview

# Problem

# Solution

# [Install Vault](https://learn.hashicorp.com/tutorials/vault/getting-started-install?in=vault/getting-started)

1º Add the hashicorp key

```shell
sudo curl -fsSL https://apt.releases.hashicorp.com/gpg | sudo apt-key add -
```

2º Add the hashicorp repository

```shell
sudo apt-add-repository "deb [arch=amd64] https://apt.releases.hashicorp.com $(lsb_release -cs) main"
```

3º Update and install vault

```shell
sudo apt-get update && sudo apt-get install vault
```

## Testing the installation

To test the installation run the command `vault` and will appear some options!

```shell
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

```shell
vault -autocomplete-install
```

Once you have installed the completions, you should restart your terminal session or reload the shell to begin using them.

```shell
source ~/.bashrc
```

# Starting the Server

First, start a Vault dev server. The dev server is a built-in, pre-configured server that is not very secure but useful for playing with Vault locally.

```shell
vault server -dev
```

Now that we start the dev server, launch a new terminal session.

```shell
export VAULT_ADDR='http://127.0.0.1:8200'
```

Save the unseal key somewhere. Don't worry about how to save this securely. For now, just save it anywhere.

```shell
export VAULT_TOKEN="<YOUR ROOT TOKEN HERE>"
```

Now we need to verify the Server is Running

```shell
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

```shell
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

```shell
$ vault kv put secret/hello foo=world excited=yes

Key              Value
---              -----
created_time     2019-02-04T19:54:03.250328Z
deletion_time    n/a
destroyed        false
version          2
```

**Warning**

The examples in this guide use the `<key>=<value>` input to send secrets to Vault. However, sending data as a part of the CLI command often end up in the **shell history unencrypted**. To avoid this, refer to the documentation or [Static Secrets: Key/Value Secrets Engine guide](https://learn.hashicorp.com/tutorials/vault/static-secrets#q-how-do-i-enter-my-secrets-without-exposing-the-secret-in-my-shell-s-history) to learn different approaches.