package br.com.fiap.aquasafe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfiguration {

	final String TIPO_AUTENTICACAO = "bearerAuth";

	@Bean
	OpenAPI configurarSwagger() {
		return new OpenAPI()

				.addSecurityItem(new SecurityRequirement().addList(TIPO_AUTENTICACAO))

				.components(new Components()
						.addSecuritySchemes(TIPO_AUTENTICACAO,
								new SecurityScheme()
										.name(TIPO_AUTENTICACAO)
										.type(SecurityScheme.Type.HTTP)
										.scheme("bearer")
										.bearerFormat("JWT")))

				.info(new Info()
						.title("AquaSafe API - FIAP Global Solution 2026")
						.description("""
								## API REST para monitoramento de enchentes e alertas de risco.
								
								---
								
								### Como usar esta API:
								
								**1. Faça login** no endpoint `POST /autenticacao/login` com:
								- `usuario`: seu RM (user: RM561365) -> Para o professor são essas aqui
								- `senha`: sua senha (senha: senha123) -> Para o professor são essas aqui
								- `duracao`: tempo do token em minutos (ex: 60)
								
								OBS: Se é sua primeira vez aqui no sistema faça o login no endpoint de usuarios.
								
								**2. Copie o token** retornado e clique no botão **Authorize 🔒** no topo da página.
								
								**3. Cole o token** no campo e clique em Authorize.
								
								---
								
								### Perfis de usuario (campo `permissao`):
								
								| Valor | Descricao |
								|-------|-----------|
								| `ADMIN` | Acesso total ao sistema |
								| `USER`  | Usuario comum |
								
								---
								
								### Regra de negocio - Niveis de risco:
								
								| Nivel de agua | Risco |
								|---------------|-------|
								| Abaixo de 40% | BAIXO |
								| Entre 40% e 70% | MEDIO |
								| Acima de 70% | ALTO |
								| Acima de 85% | CRITICO (gera alerta automatico) |
								""")
						.summary("AquaSafe - Sistema de Alertas de Enchente")
						.termsOfService("https://aquasafe.fiap.com.br/termos")
						.version("1.0.0")
						.license(new License()
								.url("/licenses")
								.name("FIAP - Global Solution 2026")));
	}
}