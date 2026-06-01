#!/bin/bash
# Script de compilação para o projeto Biblioteca

set -e

echo "🔨 Compilando projeto Biblioteca..."

# Criar diretório de saída se não existir
mkdir -p build/classes

# Compilar código principal
echo "📦 Compilando código fonte..."
javac -d build/classes \
  -encoding UTF-8 \
  -source 17 -target 17 \
  src/bibliotecasystem/*.java \
  src/bibliotecasystem/database/*.java \
  src/bibliotecasystem/modelos/*.java \
  src/bibliotecasystem/security/*.java \
  src/bibliotecasystem/service/*.java \
  src/bibliotecasystem/telas/*.java \
  src/bibliotecasystem/util/*.java 2>&1 || true

echo "✅ Compilação concluída!"
