# Escopo do MVP: app para treinadores

## Por quê

Na minha consultoria online eu perdia horas passando o treino pronto para planilha e depois para PDF, tempo que eu poderia usar para estudar, gravar vídeos para o Instagram e vender. Quero um app que o treinador use para montar e entregar o treino, para alavancar a consultoria dele e aumentar o valor que o aluno percebe.

## O que é

Aplicativo para treinadores que vendem consultoria online. O treinador monta os treinos dentro do app e os disponibiliza ao aluno, que acessa pelo celular. O treinador paga uma assinatura ao dono do sistema.

## Atores

- **Dono do sistema:** fornece o app e cobra a assinatura.
- **Treinador:** cliente pagante. Gerencia alunos e treinos.
- **Aluno:** vê o treino, inicia, registra peso e repetições em cada série e conclui.

## Fluxo principal

1. O treinador conhece o sistema e cria uma conta.
2. Período de teste (ver decisões em aberto).
3. Ele assina. O provedor de pagamento confirma por webhook e a conta fica ativa se o pagamento estiver em dia.
4. O treinador gera um link de convite. O aluno abre o link, cria o próprio login e fica vinculado a esse treinador.
5. O treinador cria treinos: um específico na ficha do aluno, ou um modelo reutilizável que pode ser atribuído a vários alunos.
6. O aluno vê o treino e o inicia. Em cada série de cada exercício, anota o peso e as repetições feitas. Ao terminar, conclui o treino.
7. O treinador vê a frequência (os dias em que o aluno concluiu treino e a porcentagem de cumprimento) e o que o aluno registrou.

## Dentro do MVP

- Conta e login com JWT. Papéis: treinador e aluno.
- Assinatura do treinador: teste, cobrança mensal (base mais valor por aluno ativo), webhook de pagamento e estados da conta.
- Convite por link único e com validade. Vínculo entre aluno e treinador.
- Aluno ativo por padrão. Só o treinador desativa.
- Treino: título, exercícios com séries, repetições, descanso, link de vídeo e observações do treinador. Modelos e treinos por aluno.
- Sessão de treino: o aluno inicia e conclui um treino. Registro de série: peso e repetições feitos em cada série de cada exercício.
- Relatório de frequência para o treinador, a partir das sessões concluídas.
- Isolamento: o treinador só vê os próprios alunos e o aluno só vê os próprios dados.
- Ficha do aluno com um campo de texto para anotações do treinador, visível só para ele.

## Fora do MVP (por ora)

- Gráficos de evolução, comparação com o treino anterior e sugestão de progressão de carga. (O registro simples de peso e repetições está dentro do MVP.)
- Observações do aluno e chat.
- Upload ou hospedagem de vídeo (usar só o link).
- Personalização visual do app.
- Aplicativo nativo. A interface entra depois da etapa 3.
- Aluno pagar o treinador dentro do sistema (fase 2).
- Fotos e dados sensíveis de treino.
- Nutricionistas e dieta.
- Painel administrativo do dono (usar o painel do provedor de pagamento e o banco).

## Regras já decididas

- O aluno nasce ativo. Só o treinador o desativa.
- Período de teste de 10 dias, sem limite de alunos. Se acabar sem assinatura, a conta fica suspensa, sem apagar nada, e volta quando ele assinar.
- Um aluno tem um treinador por vez.
- O histórico pertence ao treinador. Cada vínculo aluno-treinador tem seus próprios treinos, sessões, registros de série e anotações. Ao trocar de treinador, o novo começa do zero. Se o aluno voltar, o treinador antigo recupera o que já tinha.
- O e-mail é único por conta, o que impede repetir o teste com o mesmo e-mail.
- Valores de exemplo: R$ 100 por mês mais R$ 2 por aluno ativo. Ficam em configuração, não no código.
- Arquitetura: monólito modular.
- Testes junto com cada etapa: unitários nas regras e um de integração no fluxo principal (Testcontainers e REST Assured), escritos por mim.

## Propostas a confirmar

- Contar os alunos ativos no dia da cobrança.
- Atraso no pagamento: aviso imediato ao treinador; 7 dias de tolerância; depois, o treinador não cadastra alunos nem edita planos, mas os alunos continuam vendo o treino; após 30 dias, suspensão. Os dados nunca são apagados.
- Ao atribuir um modelo a um aluno, o aluno recebe uma cópia. Editar o modelo depois não altera treinos já atribuídos.

## Decisões em aberto

1. Provedor de pagamento: Stripe ou Mercado Pago com Pix? Confirmar o ambiente de testes.
2. Como o aluno usa o app: site que funcione bem no celular ou aplicativo? Decidir depois da etapa 3.
3. Existe uma integração externa natural para o Feign? (opcional)

## Limites conhecidos

- Um treinador pode criar outra conta, com outro e-mail, para ganhar outro teste de 10 dias. Fora do controle do MVP.
- O campo de anotações é texto livre e pode receber dados pessoais ou de saúde, mesmo sem o sistema pedir. Atenção à privacidade (LGPD), a pesquisar mais para frente.

## Etapas

1. Base: contas, alunos, treinos, PostgreSQL, Docker e testes.
2. JWT, convite por link e isolamento entre treinadores.
3. Modelos e cópias de treino, sessão de treino, registro de série (peso e repetições), conclusão e relatório de frequência.
4. Assinatura, webhook e estados da conta.
5. RabbitMQ: avisos de pagamento e de fim do teste.
6. Feign (opcional).

**Definição de pronto de cada etapa:** regras com testes unitários, um teste de integração do fluxo principal, README atualizado e uma entrada no diário.
