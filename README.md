# scalatags-hokko

```scalatags-hokko``` and its dependencies are not yet available on maven central, for now you should manually publish them locally.
From here on we assume an installation of SBT, guidelines can be found here: TODO


## Compiling scalatags-hokko

```scalatags-hokko``` depends on the ```hokko``` FRP library. 

Installing ```hokko```:

```
git clone https://github.com/Tzbob/hokko.git 
cd hokko
git checkout fb67786b902fd79314e52c4351ea95a1ff4079d7

sbt publish-local
cd ..
```

Compiling ```scalatags-hokko```:

```
git clone https://github.com/Tzbob/scalatags-hokko.git 
git checkout fb67786b902fd79314e52c4351ea95a1ff4079d7

cd hokko
sbt compile
```

## Running the Example

An example application is available in ```modules/examples```.
You can use SBT to compile the example:

```
sbt examples/fastOptJS
```
This compiles the example to a Javascript program. 
An HTML document to test the program is located in ```modules/examples/src/main/resources/index.html```:

```
$BROWSER modules/examples/src/main/resources/index.html
```

