#!/bin/bash
# Script de validação de compilação

set -e

echo "🔍 Validando compilação do projeto Biblioteca..."
echo ""

# Cores
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

errors_found=0

# Lista de arquivos a validar
files=(
    "src/bibliotecasystem/Main.java"
    "src/bibliotecasystem/database/ConexaoBD.java"
    "src/bibliotecasystem/database/UsuarioDAO.java"
    "src/bibliotecasystem/database/LivroDAO.java"
    "src/bibliotecasystem/database/EmprestimoDAO.java"
    "src/bibliotecasystem/modelos/Usuario.java"
    "src/bibliotecasystem/modelos/Livro.java"
    "src/bibliotecasystem/modelos/Emprestimo.java"
    "src/bibliotecasystem/security/PasswordUtils.java"
    "src/bibliotecasystem/service/AuthService.java"
    "src/bibliotecasystem/telas/TelaLogin.java"
    "src/bibliotecasystem/telas/TelaPrincipal.java"
    "src/bibliotecasystem/telas/TelaLivros.java"
    "src/bibliotecasystem/telas/TelaUsuarios.java"
    "src/bibliotecasystem/telas/TelaEmprestimos.java"
    "src/bibliotecasystem/util/LoggerUtils.java"
)

echo "✅ Verificando arquivos:"
for file in "${files[@]}"; do
    if [ -f "$file" ]; then
        echo -e "${GREEN}✓${NC} $file"
    else
        echo -e "${RED}✗${NC} $file (NOT FOUND)"
        ((errors_found++))
    fi
done

echo ""
if [ $errors_found -eq 0 ]; then
    echo -e "${GREEN}✅ Todos os arquivos estão presentes!${NC}"
    echo ""
    echo "📦 Para compilar com Maven:"
    echo "   mvn clean compile"
    echo ""
    echo "📦 Para executar testes:"
    echo "   mvn test"
    echo ""
    echo "📦 Para gerar JAR:"
    echo "   mvn package"
else
    echo -e "${RED}❌ $errors_found arquivos não encontrados!${NC}"
    exit 1
fi
