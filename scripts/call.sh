#!/usr/bin/env bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
TOKEN_FILE="$SCRIPT_DIR"/token-header.txt

curl -w "\n" -H "$(cat $TOKEN_FILE)" localhost:8080/api/me
