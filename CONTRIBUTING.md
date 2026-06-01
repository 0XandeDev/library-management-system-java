# Contribuindo para Biblioteca System

## Convenções de branch
- Novas funcionalidades e melhorias: `improvements/*`
- Correções de bugs: `fix/*`
- Documentação: `docs/*`
- Não mesclar diretamente na `main` sem revisão.

## Convenções de commit
- `feat: descrição curta` para funcionalidades e melhorias.
- `fix: descrição curta` para correções de bugs.
- `docs: descrição curta` para mudanças em documentação.
- `chore: descrição curta` para manutenção e automação.

## Como abrir PR
1. Crie um branch a partir de `main`.
2. Faça commits pequenos e atômicos.
3. Envie o branch e abra um Pull Request para `main`.
4. Descreva as mudanças com o objetivo, o que foi alterado e como testar.

## Como rodar localmente
- `./build.sh` para compilar com Ant.
- `./build.sh test` para rodar testes via Maven.

## Como ajudar
- Verifique se novos arquivos Java passam pelo `checkstyle`.
- Evite expor segredos no repositório.
- Use `GITHUB_TOKEN` no GitHub Actions quando necessário.
