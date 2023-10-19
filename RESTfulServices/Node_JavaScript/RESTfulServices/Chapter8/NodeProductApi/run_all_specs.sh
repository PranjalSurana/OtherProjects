#! /usr/bin/bash
# Run all specs under a given directory using Jasmine

[[ $# -gt 1 ]] && (echo "Usage: $0 [dir]" >&2; exit 1)

find "${1:-spec}" -name '*.[Ss]pec.js' | 
    while read filepath
    do
        echo; echo "Executing specs in $filepath"
        jasmine "$filepath" | egrep -v '^(Started|Random|Finished|$)'
    done
