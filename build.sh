#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"

if [ "${1:-}" = "test" ]; then
  mvn -q test
  exit 0
fi

if [ "${1:-}" = "mvn" ]; then
  mvn -q test
  exit 0
fi

if [ "${1:-}" = "ant" ]; then
  ant -f build.xml clean
  exit 0
fi

ant -f build.xml clean
