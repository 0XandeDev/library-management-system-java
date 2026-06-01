# 📚 Biblioteca System

## Visão geral
Biblioteca System é um sistema desktop Java Swing para gerenciar livros, usuários e empréstimos.
A nova versão adiciona segurança de autenticação, código melhor estruturado, testes automáticos e workflows GitHub Actions.

## Tecnologias
- Java 17
- Swing
- JDBC
- MySQL (por padrão)
- H2 (testes em memória)
- Ant (build original)
- Maven (integração de testes/CI)
- GitHub Actions

## Como compilar
### Usando Ant
```bash
./build.sh
```

### Usando Maven
```bash
mvn -q test
```

## Como executar
1. Configure as variáveis de ambiente ou propriedades JVM se necessário:
   - `db.url` - URL do banco de dados
   - `db.user` - usuário do banco
   - `db.password` - senha do banco
2. Execute via Ant ou Maven.

## Como rodar testes
```bash
./build.sh test
```

## Automação e CI
- `.github/workflows/ci.yml` roda o build Ant, testes Maven e análise estática em `ubuntu-latest` e `windows-latest`.
- `.github/workflows/auto-commit.yml` formata o código, executa testes e abre/atualiza um PR no branch `improvements/auto-update`.
- O workflow usa `GITHUB_TOKEN` para commits e criação de PR sem expor segredos.

## Reverter mudanças automáticas
- Reverta o commit no branch `improvements/auto-update` ou feche o PR.
- Caso precise reverter um arquivo, use `git checkout -- <arquivo>`.

## Contribuição
Veja `CONTRIBUTING.md` para convenções de branch e commit.
