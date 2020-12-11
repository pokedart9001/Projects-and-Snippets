#!/bin/bash

MAX=$1
for i in "$@"; do
	if [ "$i" -gt "$MAX" ]; then
		MAX=$i
	fi
done
echo "$MAX"
