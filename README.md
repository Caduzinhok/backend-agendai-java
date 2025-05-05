# 🧠 Agenda AI
Agenda AI é uma API desenvolvida em Java com Spring Boot para gerenciamento inteligente de agendamentos entre empresas prestadoras de serviços e usuários finais.

## 🚀 Funcionalidades
Registro e login de usuários com diferentes perfis:

- [X] Login e Registro Usuário comum (aquele que agenda um serviço).
- [X] CRUD Funcionários
- [X] CRUD Empresa
- [X] CRUD Serviços
- [X] CRUD Agendamento de Serviços.
- [X] Consulta Disponibilidades
- [ ] CRUD de Disponibiidades.
- [ ] Login de Funcionários.
- [ ] Conectar funcionários com tabela de usuários.
## 📦 Tecnologias utilizadas
- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL

## 🔐 Autenticação
- Login via JWT

## 📁 Estrutura de endpoints (exemplos)
Método	Rota	Descrição
- POST	/api/auth/register	Registro usuário comum
- POST	/api/auth/login	Criação usuário comum
- GET	/api/employees	Listagem de funcionários
- GET	/api/employees/{id}/availability	Ver horários disponíveis
- GET /api/services - Listagem de serviços
