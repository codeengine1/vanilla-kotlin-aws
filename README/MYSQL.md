### Install MySQL for your dev environment
* `brew install mysql`
    * `brew link --force mysql`
    * `brew services start mysql`
    * `mysql_secure_installation`
        * Follow prompts; **do not** enable the password plugin
        * Set `vanilla`’s password to `password` for local development
* `brew install gradle`
