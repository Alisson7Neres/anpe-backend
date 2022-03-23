package com.anpe.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.anpe.dto.PessoaDTO;
import com.anpe.model.Pessoa;
import com.anpe.service.PessoaService;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/pessoa")
@CrossOrigin("*")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@PostMapping(value = "/")
	public ResponseEntity<Pessoa> create(@Valid @RequestBody Pessoa pessoa) {
		viaCep(pessoa);
		pessoaService.create(pessoa);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pessoa.getId()).toUri();
		return ResponseEntity.created(uri).body(pessoa);
	}
	
	@GetMapping(value = "/")
	public ResponseEntity<List<PessoaDTO>> findAll() {
		List<Pessoa> list = pessoaService.findAll();
		List<PessoaDTO> listDTO =  list.stream().map(obj -> new PessoaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Pessoa> findById(@PathVariable Integer id) {
		Pessoa pessoa = pessoaService.findById(id);
		return ResponseEntity.ok().body(pessoa);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Pessoa> update(@PathVariable Integer id, @RequestBody Pessoa obj) {
		Pessoa pessoa = pessoaService.update(id, obj);
		return ResponseEntity.ok().body(pessoa);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		pessoaService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	// Consumindo API VIACEP
	public void viaCep(Pessoa pessoa) {
		
		try {
			
		URL url = new URL("https://viacep.com.br/ws/"+pessoa.getCep()+"/json/");
		URLConnection connection = url.openConnection();
		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		String cep = "";
		StringBuilder jsonCEP = new StringBuilder();
		
		while((cep = br.readLine()) != null) {
			jsonCEP.append(cep);
		}
		
		System.out.println(jsonCEP.toString());
	
		Pessoa userAux = new Gson().fromJson(jsonCEP.toString(), Pessoa.class);
		
		pessoa.setCep(userAux.getCep());
		pessoa.setLogradouro(userAux.getLogradouro());
		pessoa.setComplemento(userAux.getComplemento());
		pessoa.setBairro(userAux.getBairro());
		pessoa.setLocalidade(userAux.getLocalidade());
		pessoa.setUf(userAux.getUf());
		} catch(Exception e ) {
			e.printStackTrace();
		}
	}

}
