#!/usr/bin/env bash

script_dir=$(dirname "$0")
cd $script_dir
cd ..

source dev/formatting.sh
echo -e "${PURPLE}${DOCIST_LOGO}${NC}"

clj -M:test-refresh
