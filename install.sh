#!/bin/bash

function makedir {
	if [ ! -d $1 ] ; then
		mkdir -p $1
	fi
}

function copyfile {
	# Copies a file, if already exists asks for user confirmation 
	# $1 - src
	# $2 - dst
	if [ -e $2 ] ; then
		read -p "[+] File $2 already exists. Overwrite? [yN]" -n 1 -r
		if [[ $REPLY =~ ^[Yy]$ ]] ; then
	    	cp -f $1 $2
	    fi
	else
		cp -f $1 $2
	fi
}

echo '[+] *** This script installs the Moonlight Controller ***'


if type -p java; then
    echo [+] Found java executable in system path.
    _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    echo [+] Found java executable in JAVA_HOME.   
    _java="$JAVA_HOME/bin/java"
else
    INSTALL_JAVA=true
    echo '[+] No Java executable found.'
fi

if [[ "$_java" ]]; then
    version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo [+] Current Java version is "$version"
    if [[ "$version" > "1.8" ]]; then
        INSTALL_JAVA=false
        echo '[+] No need to upgrade Java version.'
    else         
        INSTALL_JAVA=true
        echo '[+] Installation will try to upgrade Java to version 1.8. In case of a failure, please do that manually.' 
    fi
fi

command -v mvn >/dev/null 2>&1 || INSTALL_MVN=true

if [ "$INSTALL_JAVA" = true ] ; then
	echo [+] Installing Java 1.8...
	apt-get install python-software-properties software-properties-common
	add-apt-repository ppa:webupd8team/java
	apt-get update
	echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
	echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections
	apt-get -y install oracle-java8-installer
	echo [+] Java installation completed.
fi

if [ "$INSTALL_MVN" = true ] ; then
	echo [+] Installing Apache Maven...
	apt-cache search maven
	apt-get install maven
	echo [+] Maven installation completed.
else
	echo [+] Apache Maven is already installed.
fi

echo [+] Installing Moonlight...
mvn install

echo [+] Configuring...
makedir ./apps
makedir ./topology
makedir ./config

copyfile ./src/main/resources/topology.json ./topology/topology.json
copyfile ./src/main/resources/config.properties ./config/config.properties

echo 'java -cp target/MoonlightController-1.0-jar-with-dependencies.jar org.moonlightcontroller.main.Main' > start_moonlight
chmod +x start_moonlight

echo '[+] *** SUCCESS: Moonlight Controller installation script has completed ***'
echo '[+] To run, use: ./start_moonlight'
echo ''
