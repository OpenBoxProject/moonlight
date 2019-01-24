# The Moonlight Controller

# Installation

#### Create an openbox directory
```bash
mkdir openbox
cd openbox
```

#### Clone git repositories
```bash
git clone moonlight
git clone firewall
git clone snort
```
## Install Locally

### Install Moonlight Controller locally

*if apt-get is not installed, make sure that java 1.8 and maven are installed on your machine
```bash
cd moonlight
./install.sh
cd ..
```

### Install the apps

#### Firewall App
```bash
cd MoonlightFirewall
./make_jar
cd ..
cp MoonlightFirewall/target/MoonlightFirewall-1.0.jar moonlight/target/apps/.
```

#### Snort App
```bash
cd MoonlightSnort
./make_jar
cd ..
cp MoonlightSnort/target/original-MoonlightSnort-1.0.jar moonlight/target/apps/.
```
### Run the controller
```bash
cd moonlight
./start_moonlight
```

### Mock OBI

To Examine the deployed blocks, you would need an obi running.

To start a mock OBI, run

```bash
pushd moonlight && ./start_obi_mock 127.0.0.1 3637 127.0.0.1 3632 2
```

where ./start_obi_mock parameters are `./start_obi_mock <controller_host> <controller_port> <obi_host> <obi_port> <dpid>`

To run multiple OBIs on different ports and dpids (Data Plane IDs / Endpoints), simply run the ./start_mock_obi command again, with different command line arguments

Once an Mock OBI is loaded, it listens for HTTP requests on the configured <obi_port> port.

The mock OBI will send an Hello message to the moonlight controller upon initialization.

## Install on Docker

### Build the image

```bash
cp moonlight/docker/Dockerfile .
docker build . -t moonlight-controller
```

### Install the apps

First, create the apps directory on your host
```bash
mkdir apps
```

Then, copy the openbox applications jars to that directory
```bash
docker run --rm -v $PWD/apps/:/openbox/moonlight/target/apps -ti moonlight-controller /bin/bash -c 'cp MoonlightFirewall/target/MoonlightFirewall-1.0.jar moonlight/target/apps/.;cp MoonlightSnort/target/original-MoonlightSnort-1.0.jar moonlight/target/apps/.'
```

You can place additional openbox applications jars in the apps directory, and then run

```bash
docker run --rm --name controller -p 8080:8080 -p3635:3635 -p3637:3637 -v $PWD/apps/:/openbox/moonlight/target/apps -ti moonlight-controller
```

### Mock OBI

To Examine the deployed blocks, you would need an obi running.

To start a mock OBI, expose the obi port when you run the controller (by adding -p$MOCK_OBI_PORT:$MOCK_OBI_PORT to the docker run command above) so you could interact with it from your localhost

Then, execute the `./start_obi_mock command on the controller's running container:

```bash
docker exec -ti controller /bin/bash -c 'pushd moonlight && ./start_obi_mock 127.0.0.1 3637 127.0.0.1 3632 2'
```

where ./start_obi_mock parameters are `./start_obi_mock <controller_host> <controller_port> <obi_host> <obi_port> <dpid>`

To run multiple OBIs on different ports and dpids (Data Plane IDs / Endpoints), simply run the docker exec command again, with different command line arguments

Once an Mock OBI is loaded, it will send a Hello message to the Moonlight controller and will start a server to listens for HTTP requests on the configured <obi_port> port.



