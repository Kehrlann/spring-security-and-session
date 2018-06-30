#!/usr/bin/env bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
TOKEN_FILE="$SCRIPT_DIR"/token-header.txt

echo 'Logging out ...'
curl -X POST -H "$(cat $TOKEN_FILE)" "http://localhost:8080/login?logout"
echo 'Success !'

if [ -f "$TOKEN_FILE" ]; then
  rm "$TOKEN_FILE"
  echo 'Removed previous token.'
fi
