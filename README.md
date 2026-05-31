# CadFlow - Sitema Cadastro de Pessoas em Vulnerabilidade Social

> **Projeto de Extensão: Desenvolvimento para a Disciplina de Informática e Sociedade III** 

> **Curso: Análise e Desenvolvimento de Sistemas (ADS) — IFSul Campus Venâncio Aires**

---

## Sobre o Projeto

O **CadFlow** é um sistema de software desenvolvido com o objetivo de atender a realidade social e operacional da **Casa de Acolhimento Venâncio Aires/RS**, uma entidade real destinada a receber e acolher crianças e adolescentes em situações de vulnerabilidade ou risco. 

O projeto surgiu no âmbito da Curricularização da Extensão. Atualmente, a entidade realiza o registro de novos ingressos de forma manual e burocrática por meio de arquivos isolados no editor de texto Microsoft Word. O CadFlow centraliza e digitaliza esse fluxo, permitindo a emissão rápida, eficiente e padronizada das fichas cadastrais e dos Planos Individuais de Atendimento (PIA) para envio aos fóruns e demais órgãos reguladores.

A plataforma foi projetada com foco em usabilidade e agilidade operacional, garantindo um atendimento mais rápido e humanizado, reduzindo o tempo gasto com burocracia para que os profissionais possam se concentrar no acompanhamento técnico dos acolhidos.

---

## Tecnologias Utilizadas

- **Linguagem Backend/Frontend:** Java (JavaFX para interface gráfica intuitiva e consistente)
- **Gerenciador de Dependências:** Maven
- **Banco de Dados:** MySQL / MariaDB
- **Arquitetura:** Orientação a Objetos (Diagrama de Classes incluso)

---

## Funcionalidades Principais

### 1. Painel de Controle e Gestão de Pessoas
- **Tela Principal Dinâmica:** Listagem em tempo real de todas as pessoas cadastradas na base de dados, com indicadores visuais rápidos que sinalizam se o indivíduo está em status ativo de "Acolhido" ou não.
- **Mecanismo de Busca e Filtro:** Funcionalidade de pesquisa por Nome ou CPF, com filtros dedicados para ocultar ou exibir registros inativos.
- **Ações Rápidas Integradas:** Botões diretos na interface para cadastrar, visualizar a ficha completa, alterar dados, excluir registros ou emitir documentos.

### 2. Cadastro Completo e Modularizado de Acolhidos
A interface organiza de forma lógica e centralizada dados sensíveis de alta complexidade divididos em abas:
- **Dados Pessoais:** Nome completo, nome social, CPF, filiação, data de nascimento, idade calculada automaticamente, sexo, cor, nacionalidade, naturalidade e endereço da última residência.
- **Dados do Acolhido (Prontuário Técnico):** Registro de documentação civil (RG/Certidão/NIS), questões e histórico de saúde (doenças, deficiências, alergias e uso de medicações contínuas), histórico de situação de rua e contexto social, situação jurídica (medidas protetivas ativas) e serviços da rede já acessados (CRAS, CAPS, CREAS, Escolas/APAE).
- **Familiares deste Acolhido:** Cadastro detalhado e vinculação de membros da família (parentesco, idade, ocupação, renda, endereço, dependência química e recebimento de benefícios sociais como o BPC).
- **Dados do Acolhimento:** Registro cronológico da data de entrada, responsável pelo abrigamento (ex: Conselho Tutelar), contatos, histórico com quem residia anteriormente e seleção múltipla dos motivos do acolhimento (negligência, violência física/psicológica, conflito familiar).

### 3. Módulo Estatístico e Visão Geral (Dashboards)
A plataforma processa a base de dados em tempo real e entrega aos administradores um painel analítico contendo:
- **Cards de Indicadores Rápidos:** Quantitativo de Acolhidos ativos, total de Familiares vinculados mapeados e número exato de Vagas Disponíveis na casa com opção de alteração direta do limite total.
- **Gráficos Analíticos:** Gráfico de pizza interativo demonstrando a distribuição demográfica por sexo (Masculino, Feminino, Outros) da população atualmente acolhida na instituição.

### 4. Criação e Monitoramento de Planos de Ação (PIA)
- Estruturação do Plano Individual de Atendimento (PIA) direto no sistema, com campos para definição de objetivos interdisciplinares, listagem de ações em parceria com a rede de proteção, atribuição de responsáveis técnicos, prazos de início/fim e monitoramento dinâmico do status do plano.

### 5. Emissão de Documentos e Relatórios
- Geração automatizada de fichas cadastrais padronizadas e do Plano de Ação em formato PDF ou versão pronta para impressão, organizando as informações em seções formais para envio imediato às autoridades do judiciário.

### 6. Banco de Dados
O script para a criação das tabelas e relacionamentos necessários encontra-se no diretório: src/database/

---

## Pré-Requisitos para Execução do Projeto

- **Java Development Kit (JDK)** (versão 17 ou superior recomendada)
- **Maven** instalado e configurado nas variáveis de ambiente
- **Servidor MySQL** ativo localmente (via XAMPP, Workbench ou container)

---

### Clonar o Repositório:

git clone https://github.com/Alysson-afb/sistema-cadastro-cadflow.git
