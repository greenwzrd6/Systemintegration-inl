#!/bin/bash

javac -cp .:www/WEB-INF/lib/*:winstone.jar:www/WEB-INF/classes www/WEB-INF/classes/se/yrgo/schedule/*/*.java && java -jar winstone.jar --webroot=www
