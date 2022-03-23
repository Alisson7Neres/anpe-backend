package com.anpe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anpe.model.Pessoa;
import com.anpe.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa create(Pessoa obj) {
		obj.setId(null);
		if(obj.getCpf() != null || obj.getCnpj() != null) {
			return pessoaRepository.save(obj);
		}
		return null;
	}

	public Pessoa findById(Integer id) {
		Optional<Pessoa> obj = pessoaRepository.findById(id);
		return obj.get();
	}
	
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}
	
	public Pessoa update(Integer id, Pessoa pessoas) {
		Pessoa pessoa = findById(id);
		pessoa.setNome(pessoas.getNome());
		pessoa.setSobrenome(pessoas.getSobrenome());
		pessoa.setCpf(pessoas.getCpf());
		pessoa.setCnpj(pessoas.getCnpj());
		
		pessoa.setCep(pessoas.getCep());
		pessoa.setBairro(pessoas.getBairro());
		pessoa.setComplemento(pessoas.getComplemento());
		pessoa.setLocalidade(pessoas.getLocalidade());
		pessoa.setLogradouro(pessoas.getLogradouro());
		pessoa.setUf(pessoas.getUf());
		
		return pessoaRepository.save(pessoa);
	}
	
	public void delete(Integer id) {
		findById(id);
		pessoaRepository.deleteById(id);
	}

}
