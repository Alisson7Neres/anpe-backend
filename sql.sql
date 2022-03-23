select p.id, p.cnpj, p.cpf, p.nome, p.sobrenome, e.id, e.logradouro, e.complemento, e.bairro, e.localidade, e.uf from pessoa_model p
left join endereco_model e
on e.id = p.id
