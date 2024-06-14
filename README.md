# PicPay Simplificado

## Descrição do Projeto

Implementação de um serviço backend para uma versão simplificada do PicPay, permitindo registro de usuários e transferências de dinheiro.

## Funcionalidades

- **Cadastro de Usuários**: Registro com nome completo, CPF, email e senha. CPF e email devem ser únicos.
- **Transferências**: Entre usuários e para comerciantes (comerciantes só recebem).
- **Validação de Saldo**: Verificação de saldo antes da transferência.
- **Autorização Externa**: Consulta a serviço externo para autorizar transferências.
- **Notificações**: Envio de notificações para recebimento de pagamentos.

## Tecnologias Utilizadas

- Spring Boot
- Spring Security
- AWS
- API RESTful
- Mock de serviços externos para autorização e notificações
