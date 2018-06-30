#!/usr/bin/env bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo 'Authenticating ...'
RESPONSE=$(curl http://localhost:8080/login -d username=user -d password=password -i -v 2> /dev/null)
echo 'Success !'

echo 'Writing token header ...'
TOKEN=$(grep "X-Auth-Token" <(echo "$RESPONSE"))
echo $TOKEN > "$SCRIPT_DIR"/token-header.txt
echo "Wrote token in $SCRIPT_DIR/token-header.txt"
