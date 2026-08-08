
## **Cobertura de testes**

  

Como os Controllers possuem responsabilidade predominantemente de exposição dos endpoints e delegação das operações, enquanto os métodos do Repository são fornecidos pelo Spring Data JPA, os testes unitários foram concentrados nas classes Service ( **UsuarioService** e **CustomUserDetailsService** ) por serem responsáveis pela maior parte das regras de negócio da aplicação.

  

### **Cenários testados:**

  

**Cadastro de usuário com sucesso**

 - Garante que o e-mail seja validado antes da persistência.
 - Verifica que a senha é criptografada antes de ser armazenada.
 - Garante que o usuário seja persistido corretamente.
 - Verifica o envio do e-mail após o cadastro.
 - Confirma que o UsuarioResponse é retornado corretamente.

  

**Cadastro com e-mail já cadastrado**

 - Verifica a regra de negócio que impede usuários duplicados pelo
   e-mail.
 - Garante que a EmailJaCadastradoException seja lançada.
 - Garante que o usuário não seja salvo quando o e-mail já existe.
 - Garante que o e-mail de confirmação não seja enviado quando o
   cadastro falha.

  

**Busca de usuário por ID com sucesso**

 - Verifica se um usuário existente é retornado corretamente.
 - Garante a conversão da entidade Usuario para UsuarioResponse.
 - Confirma que o id, nome e e-mail retornados correspondem ao usuário encontrado.



**Busca de usuário por ID inexistente**

 - Verifica o tratamento do cenário em que o ID informado não corresponde a nenhum usuário.
 - Garante que a exceção UsuarioNaoEncontradoException seja lançada.



**Listagem de usuários sem filtro**

 - Verifica o comportamento padrão da consulta quando nenhum filtro de nome é informado.
 - Garante que findAll(Pageable) seja utilizado.
 - Verifica o retorno paginado dos usuários.
 - Confirma que as entidades são corretamente convertidas para UsuarioResponse.



**Listagem de usuários com filtro**

 - Verifica a aplicação do filtro opcional pelo nome.
 - Garante que a busca considere qualquer parte do nome, conforme o requisito.
 - Confirma que o método de consulta filtrada do repository é utilizado.
 - Verifica o correto retorno dos usuários encontrados e sua conversão para UsuarioResponse.



**Autenticação com usuário existente**

 - Verifica a integração entre o serviço de autenticação e o UsuarioRepository.
 - Garante que o usuário encontrado seja convertido corretamente para UserDetails.
 - Confirma que o e-mail e a senha armazenada sejam utilizados pelo Spring Security na autenticação.



**Autenticação com usuário inexistente**

 - Verifica o comportamento da autenticação quando o e-mail não está cadastrado.
 - Garante que UsernameNotFoundException seja lançada.



## Possibilidades para o frontend:


| Tecnologia | Prós | Contras |
|---|---|---|
| React | Grande quantidade de bibliotecas, componentes reutilizáveis e boa integração com APIs REST. | Exige escolher bibliotecas adicionais para roteamento e gerenciamento de estado e pode ter uma curva inicial maior. |
| Angular | TypeScript nativo, possui soluções integradas para roteamento, formulários e requisições HTTP. | Mais complexo e verboso, possui uma curva de aprendizado maior. Pode ser excessivo para uma aplicação pequena. |
| Vue.js | Sintaxe relativamente simples, boa documentação, permite desenvolver interfaces menores com pouca configuração. | Ecossistema menor que React, para projetos maiores pode exigir decisões adicionais sobre arquitetura. |
| Next.js | Baseado em React, oferece roteamento, renderização no servidor e recursos para aplicações mais completas, além de bom desempenho e SEO. | Pode adicionar complexidade desnecessária para um frontend simples que apenas consome uma API REST. |
| HTML + CSS + JavaScript | Simples, não exige framework; baixo número de dependências, adequado para projetos pequenos. | Menor organização para aplicações que crescem, gerenciamento de estado e componentes fica exponencialmente mais trabalhoso. |
| Thymeleaf + Spring Boot | Integração direta com o backend Spring, permite desenvolver frontend e backend no mesmo projeto | Menos adequado para interfaces altamente interativas, cria maior acoplamento entre frontend e backend. |
| Flutter | Permite desenvolver aplicações para múltiplas plataformas a partir de uma única base de código, bom desempenho para aplicativos mobile | É necessário aprender uma nova linguagem (Dart) e a constante identação de widgets torna os arquivos excessivamente grandes
