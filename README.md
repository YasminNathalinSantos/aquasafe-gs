# 🌊 AquaSafe API

> Sistema de monitoramento de enchentes e alertas de risco em tempo real.  
> Desenvolvido para a **Global Solution 2026 - FIAP**

---

## 📋 Sobre o Projeto

O **AquaSafe** é uma API REST desenvolvida com Spring Boot que gerencia regiões monitoradas, sensores de nível de água e chuva, leituras e alertas de risco de enchente.

O sistema permite que dispositivos IoT ou dados simulados enviem leituras para a API, que processa as informações, calcula o nível de risco e gera alertas automáticos quando necessário — alimentando aplicações mobile e web com dados em tempo real.

### Fluxo Principal
```
Sensor/IoT → API Java (AquaSafe) → Banco Oracle → Mobile/Web exibe alertas
```

### Regra de Negócio — Níveis de Risco
| Nível de Água | Risco Calculado |
|---------------|-----------------|
| Abaixo de 40% | 🟢 BAIXO |
| Entre 40% e 70% | 🟡 MEDIO |
| Entre 70% e 85% | 🟠 ALTO |
| Acima de 85% | 🔴 CRITICO (gera alerta automático) |

---

## 👥 Equipe

| Nome | RM |
|------|----|
| Yasmin Nathali | RM561365 |
| Lucas da Silva Lima | RM562118 |
| Riquelme Nascimento de Oliveira | RM565468 |
| Enzo Franchin de Souza | RM565677 |

---

## 🔗 Links

| Recurso | URL |
|---------|-----|
| 🚀 **Deploy** | X |
| 🎥 **Vídeo de Apresentação** | X |
| 🎯 **Vídeo Pitch** | X |
| 📘 **Swagger UI** | `{base_url}/swagger-ui/index.html` |
| 📄 **API Docs JSON** | `{base_url}/v3/api-docs` |

---

## 🏗️ Arquitetura do Projeto

```
src/main/java/br/com/fiap/aquasafe
├── AquaSafeApplication.java
├── config/
│   └── SwaggerConfiguration.java
├── control/
│   ├── AutenticacaoController.java
│   ├── AlertaController.java
│   ├── LeituraController.java
│   ├── RegiaoController.java
│   ├── SensorController.java
│   └── UsuarioController.java
├── dto/
│   ├── AlertaDTO.java
│   ├── AlertaRequestDTO.java
│   ├── LeituraDTO.java
│   ├── LeituraRequestDTO.java
│   ├── RegiaoDTO.java
│   ├── RegiaoRequestDTO.java
│   ├── SensorDTO.java
│   ├── SensorRequestDTO.java
│   └── UsuarioRequestDTO.java
├── model/
│   ├── Alerta.java
│   ├── Endereco.java          ← @Embeddable
│   ├── LeituraSensor.java
│   ├── NivelRiscoEnum.java
│   ├── Pessoa.java            ← @Embedded Endereco
│   ├── RegiaoMonitorada.java
│   ├── Sensor.java
│   ├── StatusSensorEnum.java
│   ├── StatusUsuarioEnum.java
│   ├── TipoAlertaEnum.java
│   ├── TipoSensorEnum.java
│   └── Usuario.java
├── projection/
│   ├── AlertaProjection.java
│   └── RegiaoProjection.java
├── repository/
│   ├── AlertaRepository.java
│   ├── LeituraRepository.java
│   ├── PessoaRepository.java
│   ├── RegiaoRepository.java
│   ├── SensorRepository.java
│   └── UsuarioRepository.java
├── security/
│   ├── AuthManager.java
│   ├── JWTAuthFilter.java
│   ├── JWTUtil.java
│   ├── SecurityConfig.java
│   └── UsuarioConfig.java
├── service/
│   ├── AlertaCachingService.java
│   ├── LeituraCachingService.java
│   ├── RegiaoCachingService.java
│   ├── RegiaoPaginacaoService.java
│   └── SensorCachingService.java
└── validation/
    └── GerenciadorValidacoes.java
```

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|-----------|--------|-----------|
| Java | 21 | Linguagem principal |
| Spring Boot | 4.0.3 | Framework principal |
| Spring Data JPA | - | Persistência de dados com ORM |
| Spring Security | - | Autenticação e autorização |
| Spring HATEOAS | - | Links hipermídia nas respostas |
| Spring Cache | - | Cache de dados |
| Spring Validation | - | Validação de dados de entrada |
| JWT (jjwt) | 0.12.6 | Geração e validação de tokens |
| Oracle Database | 19c | Banco de dados |
| Hibernate | 7.2.4 | ORM |
| Lombok | 1.18.42 | Produtividade |
| SpringDoc OpenAPI | 3.0.2 | Documentação Swagger |
| Spring Boot DevTools | - | Hot reload em desenvolvimento |

---

## ✅ Requisitos Atendidos

| Requisito | Implementação |
|-----------|---------------|
| API REST com Spring Boot | ✅ Controllers organizados por domínio |
| Organização em camadas | ✅ model, dto, repository, service, control, security, config, validation |
| Verbos HTTP corretos | ✅ GET, POST, PUT, DELETE com status codes adequados |
| HATEOAS | ✅ EntityModel com links nos endpoints de busca por ID |
| Injeção de dependência | ✅ @Autowired em todos os componentes |
| Lombok | ✅ Configurado no pom.xml |
| Spring Boot DevTools | ✅ Hot reload ativo |
| JPA + JpaRepository | ✅ Todos os repositories estendem JpaRepository |
| CRUD completo | ✅ Regiões, Sensores, Leituras, Alertas, Usuários |
| DTOs com Java Records | ✅ RegiaoDTO, SensorDTO, LeituraDTO, AlertaDTO |
| Spring Validation | ✅ @NotEmpty, @Size, @DecimalMin, @DecimalMax |
| Tratamento de exceções | ✅ GerenciadorValidacoes + ResponseStatusException |
| Herança | ✅ Pessoa ← Usuario |
| Embedded | ✅ Endereco embutido em Pessoa |
| Múltiplas tabelas | ✅ 6 tabelas com relacionamentos FK |
| Spring Security + JWT | ✅ JWTUtil, JWTAuthFilter, SecurityConfig |
| Swagger/OpenAPI | ✅ Disponível em /swagger-ui/index.html |
| CORS configurado | ✅ CorsConfigurationSource liberado para todas as origens |
| Cache | ✅ CachingService por entidade com @Cacheable e @CacheEvict |
| Paginação | ✅ RegiaoPaginacaoService com PageRequest |
| Projections | ✅ RegiaoProjection e AlertaProjection com SQL nativo |
| JPQL | ✅ Queries customizadas em todos os repositories |
| Regra de negócio | ✅ Alerta automático quando nível de água > 85% |

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

- [JDK 21](https://adoptium.net) instalado
- [Eclipse IDE for Enterprise Java](https://www.eclipse.org/downloads) instalado
- Acesso à rede da FIAP (VPN ou presencial) para conexão com Oracle

### Passo a Passo

**1. Clone o repositório**
```bash
git clone https://github.com/seu-usuario/aquasafe.git
cd aquasafe
```

**2. Importe no Eclipse**
```
File → Import → Maven → Existing Maven Projects
Selecione a pasta raiz do projeto (onde está o pom.xml)
Clique em Finish e aguarde o download das dependências
```

**3. Verifique o JDK**
```
Botão direito no projeto → Properties → Java Build Path
Confirme que está usando JDK 21
```

**4. Execute a aplicação**
```
Abra: src/main/java/br/com/fiap/aquasafe/AquaSafeApplication.java
Botão direito → Run As → Spring Boot App
Aguarde a mensagem: Started AquaSafeApplication in X seconds
```

**5. Acesse o Swagger**
```
http://localhost:8080/swagger-ui/index.html
```

**6. Faça login e obtenha o token**
```
Endpoint: POST /autenticacao/login
usuario: RM561365
senha: senha123
duracao: 60
```

**7. Autorize no Swagger**
```
Copie o token retornado
Clique em Authorize 🔒 no topo do Swagger
Cole o token e clique em Authorize
Agora todos os endpoints estão liberados
```

---

## 🔐 Credenciais de Teste

| Campo | Valor |
|-------|-------|
| Usuário (RM) | `RM561365` |
| Senha | `senha123` |
| Perfil | `ADMIN` |

| Campo | Valor |
|-------|-------|
| Usuário (RM) | `RM000001` |
| Senha | `senha123` |
| Perfil | `USER` |

---

## 🗄️ Banco de Dados

- **SGBD:** Oracle 19c
- **Host:** oracle.fiap.com.br
- **Porta:** 1521
- **SID:** ORCL

> ⚠️ O banco Oracle da FIAP só é acessível dentro da rede do campus ou via VPN.

### Tabelas

| Tabela | Descrição |
|--------|-----------|
| `tb_pessoa` | Dados pessoais (nome, CPF, endereço embutido) |
| `tb_usuario` | Credenciais de acesso (RM, senha BCrypt, perfil) |
| `tb_regiao` | Regiões geográficas monitoradas |
| `tb_sensor` | Sensores físicos instalados nas regiões |
| `tb_leitura` | Leituras registradas pelos sensores |
| `tb_alerta` | Alertas de risco gerados manualmente ou automaticamente |

---

## 📐 Modelagem do Banco de Dados

```
tb_pessoa (1) ──────── (1) tb_usuario
    └── Endereco (Embedded)

tb_regiao (1) ──────── (N) tb_sensor
tb_regiao (1) ──────── (N) tb_alerta
tb_sensor (1) ──────── (N) tb_leitura
```

---

## 📡 Endpoints Principais

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/autenticacao/login` | Login e geração de token JWT |
| GET | `/regioes/todas` | Listar todas as regiões |
| GET | `/regioes/paginadas` | Listar regiões paginadas |
| GET | `/regioes/{id}` | Buscar região por ID (HATEOAS) |
| POST | `/regioes/cadastrar` | Cadastrar nova região |
| PUT | `/regioes/{id}` | Atualizar região |
| DELETE | `/regioes/{id}` | Remover região e filhos |
| GET | `/sensores/todos` | Listar todos os sensores |
| GET | `/sensores/regiao/{id}` | Sensores por região |
| POST | `/sensores/cadastrar` | Cadastrar sensor |
| PUT | `/sensores/{id}` | Atualizar sensor |
| DELETE | `/sensores/{id}` | Remover sensor e leituras |
| GET | `/leituras/sensor/{id}` | Leituras por sensor |
| GET | `/leituras/regiao/{id}` | Leituras por região |
| POST | `/leituras/registrar` | Registrar leitura (dispara alerta se > 85%) |
| GET | `/alertas/ativos` | Alertas ativos |
| POST | `/alertas/gerar` | Gerar alerta manualmente |
| PUT | `/alertas/{id}/encerrar` | Encerrar alerta |
| POST | `/usuarios/novo` | Criar novo usuário |

---

## 🧪 Fluxo de Teste Recomendado

```
1. POST /autenticacao/login          → obter token JWT
2. GET  /regioes/todas               → ver regiões disponíveis
3. GET  /sensores/regiao/{id}        → ver sensores da região
4. POST /leituras/registrar          → registrar leitura (nivel > 85 gera alerta)
5. GET  /alertas/ativos              → confirmar alerta gerado automaticamente
6. PUT  /alertas/{id}/encerrar       → encerrar alerta
```
