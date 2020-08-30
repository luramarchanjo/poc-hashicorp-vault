# Overview

# Problem

# Solution

# [Install Vault](https://learn.hashicorp.com/tutorials/vault/getting-started-install?in=vault/getting-started)

```shell
# 1º Add the hashicorp key
sudo curl -fsSL https://apt.releases.hashicorp.com/gpg | sudo apt-key add -

# 2º Add the hashicorp repository
sudo apt-add-repository "deb [arch=amd64] https://apt.releases.hashicorp.com $(lsb_release -cs) main"

# 3º Update and install vault
sudo apt-get update && sudo apt-get install vault
```

## Testing the installation

To test the installation run the command `vault` and will appear some options!

## Command Completion

Use the vault command to install command-line completions like this.

`vault -autocomplete-install`

Once you have installed the completions, you should restart your terminal session or reload the shell to begin using them.

`source ~/.bashrc`

# Starting the Server

