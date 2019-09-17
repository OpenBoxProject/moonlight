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

APT=true
command -v apt-get >/dev/null 2>&1 || APT=false
command -v mvn >/dev/null 2>&1 || INSTALL_MVN=true

if [ "$APT" = true ] ; then
    apt-get update && apt-get upgrade
    apt-get install python-software-properties  software-properties-common -y
    apt-get update

    if [ "$INSTALL_JAVA" = true ] ; then
        echo [+] Installing Java 1.8...
        apt-get install python-software-properties software-properties-common -y
        echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
        echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections
        apt-get -y install oracle-java8-installer
        apt-get install openjdk-8-jdk -y
        echo [+] Java installation completed.
    fi

   command -v mvn >/dev/null 2>&1 || INSTALL_MVN=true
   if [ "$INSTALL_MVN" = true ] ; then
        echo [+] Installing Apache Maven...
        apt-cache search maven
        apt-get install maven -y
        echo [+] Maven installation completed.
    else
        echo [+] Apache Maven is already installed.
    fi
else

    if [ "$INSTALL_JAVA" = true ] ; then
        echo [+] Java 1.8 is required...
        exit 1
    fi

    if [ "$INSTALL_MVN" = true ] ; then
        echo [+] Maven is required.
        exit 1
    fi
fi

echo [+] Installing Moonlight...
mvn install

echo [+] Configuring...

chmod +x start_moonlight start_obi_mock

echo '[+] *** SUCCESS: Moonlight Controller installation script has completed ***'

echo "Moonlight Directory: $PWD"
echo '[+] To run, add apps to target/apps and run: ./start_moonlight'
echo '[+] To start an obi mock, run: ./start_obi_mock <listening_host> <listening_port> <obi_host> <obi_port> <dpid>'
echo ''
