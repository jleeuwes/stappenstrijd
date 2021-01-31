#!/usr/bin/env bash

set -Eeu -o pipefail

export JAVA_HOME=/nix/store/mzrjfj7nng19z1s10dhv2ka6n9sigcaz-openjdk-11.0.8+10/
mvn "$@" package

trap 'kill `jobs -p`; exit' INT

java -jar stappenservice/target/stappenservice-*.jar &
java -jar scoreservice/target/scoreservice-*.jar &

wait
