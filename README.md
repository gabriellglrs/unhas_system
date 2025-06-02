<img width=100% src="https://capsule-render.vercel.app/api?type=waving&color=4C89F8&height=120&section=header"/>

![LinkedIn cover - 26](https://github.com/user-attachments/assets/1ee085f0-fb59-4d07-8d83-a294bc66f00d)


# Sistema de Agendamento de Unhas 💅

Sistema web para agendamento de serviços de manicure e pedicure, desenvolvido com Spring Boot. O sistema oferece funcionalidades completas para gestão de clientes, serviços, agendamentos e portfólio.
<br><br>
![image](https://github.com/user-attachments/assets/f49cd31a-9222-485e-bcb5-2029f78c9610)
<br><br>
![image](https://github.com/user-attachments/assets/bbdf9a3d-1ec2-4122-ba22-5370fcf90d1a)
<br><br>
![image](https://github.com/user-attachments/assets/ee0c6490-d15d-4c14-84d8-ed39ff9214ff)
<br><br>
![image](https://github.com/user-attachments/assets/2c664f61-4022-4d82-a9b5-c7596976bc80)
<br><br>
## 🚀 Funcionalidades

### Para Clientes
- ✅ Cadastro e autenticação de usuários
- ✅ Visualização de serviços disponíveis
- ✅ Agendamento online com verificação de disponibilidade
- ✅ Histórico de agendamentos
- ✅ Cancelamento de agendamentos
- ✅ Visualização do portfólio de trabalhos

### Para Administradores
- ✅ Dashboard com agendamentos do dia
- ✅ Gestão completa de agendamentos
- ✅ Cadastro e gestão de serviços
- ✅ Gestão de clientes
- ✅ Gestão do portfólio (upload de imagens)
- ✅ Atualização de status dos agendamentos
- ✅ Notificações em tempo real via WebSocket

## 🛠️ Tecnologias Utilizadas

- **Backend:** Spring Boot 3.x
- **Banco de Dados:** JPA/Hibernate
- **Segurança:** Spring Security
- **Frontend:** Thymeleaf
- **Comunicação em Tempo Real:** WebSocket/STOMP
- **Autenticação:** BCrypt para criptografia de senhas
- **Upload de Arquivos:** MultipartFile
- **Build:** Maven

## 📋 Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- Banco de dados (H2 para desenvolvimento ou MySQL/PostgreSQL para produção)

## 🔧 Instalação e Configuração

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd unhas
```

### 2. Configure o banco de dados
Edite o arquivo `application.properties` ou `application.yml` com as configurações do seu banco:

```properties
# Exemplo para MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/unhas_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

### 3. Execute a aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 👤 Usuário Administrador Padrão

Após a primeira execução, um usuário administrador é criado automaticamente:

- **Email:** admin@admin.com
- **Senha:** admin123

> ⚠️ **Importante:** Altere essas credenciais após o primeiro login!

## 📁 Estrutura do Projeto

```
src/main/java/com/unhasystem/unhas/
├── config/                 # Configurações (Security, WebSocket, MVC)
├── controllers/            # Controladores REST/MVC
├── entities/              # Entidades JPA
├── repositories/          # Repositórios de dados
├── services/              # Lógica de negócio
└── UnhasApplication.java  # Classe principal
```

### Principais Entidades

- **Usuario:** Gerencia autenticação e autorização
- **Cliente:** Informações dos clientes
- **Servico:** Catálogo de serviços oferecidos
- **Agendamento:** Controle dos agendamentos
- **ItemPortfolio:** Galeria de trabalhos realizados

## 🔐 Sistema de Autenticação

O sistema utiliza Spring Security com:
- Autenticação baseada em formulário
- Criptografia BCrypt para senhas
- Controle de acesso baseado em roles:
  - `ROLE_ADMIN`: Acesso total ao sistema
  - `ROLE_CLIENT`: Acesso às funcionalidades de cliente

## 📅 Sistema de Agendamentos

### Características
- Verificação automática de conflitos de horário
- Horários configuráveis (padrão: 9h às 18h)
- Intervalos de 30 minutos
- Status de agendamento: AGENDADO, CONFIRMADO, CONCLUÍDO, CANCELADO
- Cálculo automático do horário de término baseado na duração do serviço

### Fuso Horário
- Configurado para América/São_Paulo
- Conversão automática para UTC no banco de dados

## 🖼️ Sistema de Portfólio

- Upload de imagens para showcase de trabalhos
- Associação com serviços específicos
- Armazenamento local em `./uploads/portfolio/`
- Geração automática de nomes únicos para arquivos

## 🔄 WebSocket/Notificações

Configurado para notificações em tempo real:
- Endpoint: `/ws-agendamento`
- Tópico: `/topic`
- Prefixo da aplicação: `/app`

## 🚀 Deploy

### Configurações para Produção

1. **Banco de Dados:** Configure um banco robusto (MySQL/PostgreSQL)
2. **Uploads:** Configure um diretório persistente para uploads
3. **Segurança:** 
   - Altere as credenciais padrão
   - Configure HTTPS
   - Revise as configurações de CORS se necessário

### Variáveis de Ambiente Sugeridas

```bash
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=jdbc:mysql://localhost:3306/unhas_prod
DATABASE_USERNAME=usuario_prod
DATABASE_PASSWORD=senha_segura
UPLOAD_PATH=/var/uploads/unhas
```

## 📝 Endpoints Principais

### Públicos
- `GET /` - Página inicial
- `GET /login` - Página de login
- `GET /registro` - Cadastro de usuários
- `GET /portfolio` - Visualização do portfólio

### Clientes (ROLE_CLIENT)
- `GET /agendamento/novo` - Novo agendamento
- `GET /agendamento/horarios-disponiveis` - Consulta horários
- `POST /agendamento/agendar` - Criar agendamento
- `POST /agendamento/{id}/cancelar` - Cancelar agendamento

### Administradores (ROLE_ADMIN)
- `GET /admin/dashboard` - Dashboard administrativo
- `GET /admin/agendamentos` - Gestão de agendamentos
- `GET /admin/clientes` - Gestão de clientes
- `GET /admin/servicos` - Gestão de serviços
- `GET /admin/portfolio` - Gestão do portfólio

## 🐛 Solução de Problemas

### Problemas Comuns

1. **Erro de permissão de arquivo:**
   - Verifique as permissões do diretório `./uploads`

2. **Conflitos de agendamento:**
   - Verifique a configuração do fuso horário
   - Confirme se os horários estão sendo salvos corretamente

3. **Login não funciona:**
   - Verifique se o usuário admin foi criado
   - Confirme as configurações do Spring Security

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## 📞 Suporte

Para dúvidas ou suporte:
- Abra uma issue no GitHub
- Consulte a documentação do Spring Boot
- Verifique os logs da aplicação em caso de erros

---

**Desenvolvido com ❤️ para facilitar o agendamento de serviços de beleza**
<br>
<br>
<div align="center">
  <img src="https://github.com/user-attachments/assets/ed7208b8-6bdc-4c82-98aa-8c8cb9c1428f" height="150"/>
</div>


<img width=100% src="https://capsule-render.vercel.app/api?type=waving&color=4C89F8&height=120&section=footer"/>
