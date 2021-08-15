### Install Java JDK 11 via Coretto

```bash
brew install --cask corretto11
sudo chmod o+rx /Library/Java/JavaVirtualMachines
```

#### modify zshrc (or bashrc) to set JAVA_HOME to the new location
```bash
export JAVA_HOME="/Library/Java/JavaVirtualMachines/amazon-corretto-11.jdk/Contents/Home"
```

#### source the file after to ensure it all works
```bash
source ~/.zshrc
```

### run java -version to ensure it worked
```bash
java -version
```
you should see something similar to:
```bash
openjdk version "11.0.12" 2021-07-20 LTS
OpenJDK Runtime Environment Corretto-11.0.12.7.2 (build 11.0.12+7-LTS)
OpenJDK 64-Bit Server VM Corretto-11.0.12.7.2 (build 11.0.12+7-LTS, mixed mode)
```
