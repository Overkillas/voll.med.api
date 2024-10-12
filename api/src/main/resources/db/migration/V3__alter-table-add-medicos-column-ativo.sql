#Como ja existem dados na tabela o atributo ativo não pode ser NOT NULL
ALTER TABLE medicos ADD ativo tinyint;
#Para mudar os medicos ja existentes (adicionando um valor 1 para o atributo "ativo" que antes tava null)
UPDATE medicos set ativo = 1;
#Faz com que o atributo 'ativo' fique NOT NULL para inserts seguintes
ALTER TABLE medicos MODIFY ativo tinyint NOT NULL;
