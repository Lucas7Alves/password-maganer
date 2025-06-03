# 🔐 Password Manager

Gerenciador de Senhas Seguras desenvolvido em Java com foco em criptografia, autenticação de dois fatores (2FA) e segurança de dados. O projeto utiliza JavaFX para interface gráfica, banco de dados H2 para persistência local e criptografia AES + BCrypt para proteção de credenciais.

---

## 📷 Autor

<div align="left">
  <img src="https://avatars.githubusercontent.com/u/64313623?v=4" width="200px;" alt="Foto do Lucas Alves" style="border-radius: 50%"/>
  <br>
  <strong>Lucas Alves</strong>  
  <br>
  💼 Estudante de Análise e Desenvolvimento de Sistemas  
  📫 <a href="https://github.com/Lucas7Alves">github.com/Lucas7Alves</a>
</div>

---

## ⚙️ Tecnologias Utilizadas

- Java 21
- JavaFX
- H2 Database (modo arquivo)
- AES e BCrypt (criptografia)
- IDE recomendada: Eclipse

---

## 📁 Estrutura de Pastas

```bash
password-maganer/
├── /database/                  # Arquivo .mv.db será gerado aqui
├── /libs-jar/                  # JARs externos
├── /src/
│   └── PasswordManager/
│       ├── application/        # Classe Main
│       ├── dao/                # Acesso ao banco
│       ├── model/              # Modelos de dados
│       ├── service/            # Lógica de negócio
│       ├── controller/         # Telas da aplicação
│       ├── view/               # Design das telas
│       ├── module-info/        # Configuração das bibliotecas jar
│       └── util/               # Criptografia e conexões
├── README.md
```
---

## 🚀 Como Executar o Projeto

### ✅ Pré-requisitos

- JDK 21 instalado
- Eclipse IDE (ou outra IDE com suporte a JavaFX)

---

### 🔨 Passo a Passo

#### 1. Clone o Repositório

```git clone https://github.com/Lucas7Alves/password-maganer.git```

#### 2️⃣ Importe no Eclipse

1. **Abra o Eclipse IDE**
2. **Menu Principal:**
3. **Selecione o Projeto:**
- Clique em `Directory...`
- Navegue até a pasta do projeto clonado
- Selecione a pasta raiz do projeto
- Clique em `Finish`

#### 3️⃣ Configure as Dependências

1. **Adicione os JARs:**
- Botão direito no projeto → `Build Path` → `Configure Build Path...`
- Selecione a aba `Libraries`
- Clique em `Add JARs...`
- Navegue até a pasta `/lib` do projeto
- Selecione todos os arquivos `.jar` → `Apply and Close`

2. **Ajuste para Modulepath:**
- Na mesma aba `Libraries`
- Para cada JAR adicionado:
  - Selecione o JAR
  - Clique em `Modulepath` (se estiver em Classpath)
3. **Módulos Obrigatórios (baseado no module-info.java):**
    
| Módulo | Finalidade |
|--------|------------|
| `javafx.controls` | Componentes de interface gráfica |
| `javafx.fxml` | Carregamento de arquivos FXML |
| `javafx.graphics` | Renderização gráfica |
| `jakarta.mail` | Envio de e-mails (2FA) |
| `jakarta.activation` | Suporte ao Jakarta Mail |
| `java.net.http` | Comunicação HTTP |

#### 5️⃣ Execute a Aplicação

1. **Localize a Classe Main:**
- No Package Explorer:
  ```
  src → PasswordManager → application → Main.java
  ```

2. **Inicie o Projeto:**
- Botão direito em `Main.java`
- Selecione:
  ```
  Run As → Java Application
  ```

3. **Verifique o Console:**
- Acompanhe a inicialização no console do Eclipse
- Qualquer erro será exibido aqui

## 🛡️ Segurança

Senhas dos usuários: armazenadas com hashing seguro usando BCrypt  
Senhas de serviços: criptografadas usando AES  
Autenticação em duas etapas (2FA): implementada via e-mail  
Banco de dados: persistido localmente como database.mv.db usando H2, em um arquivo criptogradado  

## 👨‍💻 Autor
Desenvolvido por Lucas Alves  
GitHub - [Lucas7Alves](https://github.com/Lucas7Alves)

