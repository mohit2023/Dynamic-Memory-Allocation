#!/bin/bash
if [ $# -eq 0 ]; then
    echo "Provide input on console"
    java Driver_List
fi
if [ $# -eq 1 ]; then
    java Driver_List < "${1}"
fi
if [ $# -eq 2 ]; then
    java Driver_List < "${1}" > "${2}"
fi