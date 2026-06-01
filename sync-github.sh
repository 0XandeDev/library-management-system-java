#!/bin/bash

echo "🚀 Sincronizando projeto com GitHub usando GitHub CLI..."
cd /workspaces/library-management-system-java

# Verificar se está tudo commitado
echo "📋 Verificando status..."
git status

echo ""
echo "📤 Enviando mudanças para GitHub..."

# Usar GitHub CLI
if command -v gh &> /dev/null; then
    echo "✅ GitHub CLI encontrado!"
    
    # Fazer push
    git push origin main --verbose
    
    # Criar issue se necessário
    echo ""
    echo "✨ Pronto! Mudanças enviadas para: https://github.com/0XandeDev/library-management-system-java"
else
    echo "⚠️  GitHub CLI não encontrado"
    echo "Tente: sudo apt install gh"
    echo "Depois: gh auth login"
    echo "E finalmente: git push origin main"
fi
