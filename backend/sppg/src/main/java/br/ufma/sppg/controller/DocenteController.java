package br.ufma.sppg.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.dto.OrientacaoResponse;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.service.OrientacaoService;
import br.ufma.sppg.service.ProducaoService;
import br.ufma.sppg.service.TecnicaService;
import br.ufma.sppg.service.exceptions.RegrasRunTime;


@RequestMapping("/api/Docente")
@RestController
public class DocenteController{
    @Autowired
    TecnicaService tecnicaServivce;

    @Autowired
    ProducaoService producaoServivce;

    @Autowired
    OrientacaoService orientacaoServivce;

    @GetMapping("/obter_producoes/{id}/{data1}/{data2}")
    public ResponseEntity<?> obterProducoesDeDocente(@PathVariable(value = "id", required = true) Integer idDocente,
    @PathVariable(value = "data1", required = true)  Integer data1,
    @PathVariable(value = "data2", required = true)  Integer data2){

        try{
            List<Producao> producaoDocente = producaoServivce.obterProducoesDocente(idDocente, data1, data2);
            return ResponseEntity.ok(producaoDocente);
        }catch (RegrasRunTime e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obter_orientacoes/{id}")
    public ResponseEntity<?> obterOrientacoesDeDocente(@PathVariable(value = "id", required = true) Integer idDocente){

        try{
            ArrayList<OrientacaoResponse> orientacaoDocente = orientacaoServivce.obterOrientacaoDocentes(idDocente);
            return ResponseEntity.ok(orientacaoDocente);
        }catch (RegrasRunTime e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obter_tecnicas/{id}")
    public ResponseEntity<?> obterTecnicasDeDocente(@PathVariable(value = "id", required = true) Integer idDocente){

        try{
            List<Tecnica> tecnicaDocente = tecnicaServivce.obterTecnicasDocente(idDocente); 
            return ResponseEntity.ok(tecnicaDocente);
        }catch (RegrasRunTime e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}