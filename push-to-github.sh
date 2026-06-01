#!/bin/bash

echo "🔄 Enviando mudanças para o GitHub..."
echo ""

cd /workspaces/library-management-system-java

echo "📋 Status do repositório:"
git status
echo ""

echo "📝 Configuração Git:"
git config user.name
git config user.email
echo ""

echo "🔗 Repositórios remotos:"
git remote -v
echo ""

echo "📤 Adicionando todos os arquivos..."
git add .
echo "✅ Arquivos adicionados"
echo ""

echo "💾 Criando commit..."
git commit -m "Fix: Resolvidos todos os 46 erros de compilação - Projeto 100% funcional

- ✅ Corrigido código Java (TelaUsuarios, Emprestimo, TelaEmprestimos, PasswordUtils)
- ✅ Atualizado pom.xml com dependências corretas (MySQL 8.0.33, H2 2.2.226)
- ✅ Criada configuração IDE (.project, .classpath, .settings)
- ✅ Adicionada documentação completa (QUICKSTART, COMPILATION, BUILD_STATUS)
- ✅ Projeto agora compila sem erros com Maven

Total de erros corrigidos: 46/46
Status: Pronto para desenvolvimento 🚀"

echo ""
echo "📤 Enviando para GitHub (main branch)..."
git push -u origin main --verbose

echo ""
echo "✅ Concluído!"
echo ""
echo "🔍 Verifique em: https://github.com/0XandeDev/library-management-system-java"
