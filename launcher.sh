#!/bin/bash

currentDir=$(pwd)
jarFiles=()
jarFolders=(
    "$currentDir/fqw/target/"
    "$currentDir/support/target/"
    "$currentDir/protocol/target/"
)

if [ -z "$jarFolders" ]; then
    echo "jarFolders is null."
else
    echo "jarFolders is initialized."
fi

for folder in "${jarFolders[@]}"; do
    if [ -d "$folder" ]; then
        jarFiles+=($(find "$folder" -name "*.jar"))
    else
        echo "Directory not found: $folder"
    fi
done

function start_jars {
    for jar in "${jarFiles[@]}"; do
        java -jar "$jar" &
        echo "Started: $(basename "$jar")"
    done
}

function stop_jars {
    javaProcesses=$(pgrep -f java)
    if [ -n "$javaProcesses" ]; then
        for pid in $javaProcesses; do
            kill -9 "$pid"
            echo "Stopped: $pid"
        done
    else
        echo "No Java processes found."
    fi
}

echo "Found JAR files:"
for jar in "${jarFiles[@]}"; do
    echo "$jar"
done

echo "Arg is $1"

case "${1,,}" in
    start)
        start_jars
        ;;
    reboot)
        stop_jars
        start_jars
        ;;
    stop)
        stop_jars
        ;;
    *)
        echo "Invalid action. Please use 'start', 'reboot', or 'stop'."
        ;;
esac
