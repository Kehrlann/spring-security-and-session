#!/usr/bin/env bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
TOKEN_FILE="$SCRIPT_DIR"/token-header.txt

if [ -f "$TOKEN_FILE" ]; then
  echo 'Removing previous token...'
  rm "$TOKEN_FILE"
fi

echo 'Authenticating ...'
RESPONSE=$(curl http://localhost:8080/login -d username=user -d password=password -i -v 2> /dev/null)
echo 'Success !'

TOKEN=$(grep "X-Auth-Token" <(echo "$RESPONSE"))
echo "$TOKEN" > "$TOKEN_FILE"
echo "Wrote token in $TOKEN_FILE"
