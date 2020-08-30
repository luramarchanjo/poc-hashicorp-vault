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

Do not run dev server in **production**