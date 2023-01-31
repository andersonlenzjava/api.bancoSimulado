ALTER TABLE transacao
    RENAME COLUMN conta_destino TO conta_destino_id;

ALTER TABLE transacao
    RENAME COLUMN conta_operadora TO conta_operadora_id;