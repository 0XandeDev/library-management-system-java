# 🔧 Instruções de Compilação - Biblioteca System

## Compilação com Maven

```bash
# Limpar build anterior
mvn clean

# Compilar apenas (sem testes)
mvn -DskipTests compile

# Compilar com testes
mvn clean install

# Executar apenas testes
mvn -DskipTests=false test

# Gerar JAR executável
mvn package
```

## Compilação com Javac (Direto)

```bash
# Compilar todas as classes
javac -d build/classes \
  -encoding UTF-8 \
  -source 17 -target 17 \
  src/bibliotecasystem/*.java \
  src/bibliotecasystem/database/*.java \
  src/bibliotecasystem/modelos/*.java \
  src/bibliotecasystem/security/*.java \
  src/bibliotecasystem/service/*.java \
  src/bibliotecasystem/telas/*.java \
  src/bibliotecasystem/util/*.java

# Executar a aplicação
java -cp build/classes bibliotecasystem.Main
```

## Estrutura do Projeto

```
src/
├── bibliotecasystem/
│   ├── Main.java                          # Classe principal
│   ├── database/
│   │   ├── ConexaoBD.java                # Conexão com BD
│   │   ├── UsuarioDAO.java
│   │   ├── LivroDAO.java
│   │   └── EmprestimoDAO.java
│   ├── modelos/
│   │   ├── Usuario.java
│   │   ├── Livro.java
│   │   └── Emprestimo.java
│   ├── security/
│   │   └── PasswordUtils.java             # Hash de senhas
│   ├── service/
│   │   └── AuthService.java               # Autenticação
│   ├── telas/
│   │   ├── TelaLogin.java
│   │   ├── TelaPrincipal.java
│   │   ├── TelaLivros.java
│   │   ├── TelaUsuarios.java
│   │   └── TelaEmprestimos.java
│   └── util/
│       └── LoggerUtils.java               # Logging
└── test/java/
    └── bibliotecasystem/
        └── database/
            ├── DatabaseTestBase.java
            ├── ConexaoBDTest.java
            ├── UsuarioDAOTest.java
            └── LivroDAOTest.java
```

## Dependências

- **Java 17+**
- **MySQL 8.0+** (runtime)
- **H2 Database** (para testes)
- **JUnit 5** (para testes)

## Configuração do Banco de Dados

Certifique-se de que MySQL está rodando:

```sql
CREATE DATABASE IF NOT EXISTS biblioteca_db;
USE biblioteca_db;
```

A aplicação criará as tabelas automaticamente na primeira execução.
