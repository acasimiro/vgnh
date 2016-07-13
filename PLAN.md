
# Plano de Trabalho

**Objetivo:** este plano de trabalho visa entregar primeiramente a resolução do problema e, se possível, demonstrar outras habilidades que possam servir de critério de desempate.

## Estrutura do problema

O problema proposto pode ser estruturado em 3 problemas menores: 
  - a coleta dos dados (item 2);
  - a consolidação (item 5);
  - e a exibição de relatórios (itens 6 e 7).

Cada um deses problemas pode ser solucionado por subsistemas independentes, sendo que a base de dados Cassandra funcionaria como um contrato entre eles. Vale ressaltar, inclusive, que poderiam ser desenvolvidos por equipes diferentes.

## Sequência de atividades

A sequência de tarefas propostas abaixo visa prever possíveis problemas e evitar retrabalho.

1. Modelagem dos dados (item 3)
    - Começamos pela modelagem dos dados para que funcione de contrato entre os sistemas
    - A modelagem errada dos dados pode comprometer não só o desempenho das consultas, mas até mesmo a escolha das ferramentas utilizadas
2. Construção do pipeline básico de desenvolvimento
    - Construir uma aplicação hello world
    - Garantir o funcionamento do build
    - Construir imagem Docker com a aplicação
    - Subir instância atualizada do docker
    - Garantir que e aplicação pode ser testada manualmente
3. Construção do sistema de coleta `collector` (item 2)
    - Servlet Java com endpoint `POST /collect/<hashtag>` (usei POST pois o GET tem semântica nulipotente)
    - Funcionamento passivo, disparado ao chamar o endpoint acima
    - Criar app do twitter
4. Execução da coleta das hashtags indicadas (item 4)
5. Construção do sistema de consolidação `consolidator` (item 5)
    - Spark application escrita em scala ou python
    - Justificativa: são linguagens simples para manipulação de dados e nativas no spark
    - Funcionamento reativo, executa consolidação conforme novos dados são gravados no banco
6. Construção da api de relatórios `report-api` (item 6)
    - Node JS
    - `GET /user/list_top_followed&num=5`
    - `GET /hashtag/count_tweets&lang=pt`
    - `GET /tweets/count&granularity=hour`
7. Construção do sistema de exibição dos resultados `reports` (item 7)
    - Script shell
