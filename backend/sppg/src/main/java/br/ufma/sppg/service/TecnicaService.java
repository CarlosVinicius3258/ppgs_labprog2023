package br.ufma.sppg.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.repo.TecnicaRepository;
import br.ufma.sppg.service.exceptions.RegrasRunTime;
import jakarta.transaction.Transactional;

@Service
public class TecnicaService {

  @Autowired
  TecnicaRepository tecnicaRepo;

  @Autowired
  DocenteRepository docenteRepo;

  @Autowired
  ProgramaRepository programaRepo;

  public Tecnica salvarTecnica(Tecnica tecnica) {
    return tecnicaRepo.save(tecnica);
  }

  @Transactional
  public Tecnica atualizarEstatisticas(Integer idTecnica, Integer qtdGrad, Integer qtdMestrado, Integer qtdDoutorado) {

    Optional<Tecnica> tecnica = tecnicaRepo.findById(idTecnica);

    if (tecnica.isPresent()) {
      Tecnica tecnicaObj = tecnica.get();

      tecnicaObj.setQtdGrad(qtdGrad);
      tecnicaObj.setQtdMestrado(qtdMestrado);
      tecnicaObj.setQtdDoutorado(qtdDoutorado);

      return tecnicaRepo.save(tecnicaObj);
    }

    throw new RegrasRunTime("A técnica informada não existe!");
  }

  // Obter todas as Orientações de uma téncnica
  public List<Orientacao> obterOrientacoesTecnica(Integer idTecnica) {
    Optional<Tecnica> tecnica = tecnicaRepo.findById(idTecnica);

    if (tecnica.isPresent()) {
      return tecnica.get().getOrientacoes();
    }

    throw new RegrasRunTime("A técnica informada não existe");
  }

  // Obter todas as técnicas de um docente
  public List<Tecnica> obterTecnicasDocente(Integer idDocente) {

    Optional<Docente> docente = docenteRepo.findById(idDocente);

    if (docente.isPresent()) {
      return docente.get().getTecnicas();
    }

    throw new RegrasRunTime("O docente informado não existe");
  }

  // Obter todos os Docentes de uma técnica
  public List<Docente> obterDocentesTecnica(Integer idTecnica) {

    Optional<Tecnica> tecnica = tecnicaRepo.findById(idTecnica);

    if (tecnica.isPresent()) {
      return tecnica.get().getDocentes();
    }

    throw new RegrasRunTime("A técnica informada não existe");
  }

  public Optional<List<Tecnica>> obterTecnicasDocentePorPeriodo(Integer idDocente, Integer dataInicio,
      Integer dataFim) {
    Optional<Docente> docente = docenteRepo.findById(idDocente);

    // verificando se o docente existe
    if (docente.isPresent()) {

      if (dataInicio > dataFim) {
        Integer dataAuxiliar = dataInicio;

        dataInicio = dataFim;
        dataInicio = dataAuxiliar;
      }

      return tecnicaRepo.obterTecnicasDocentePorPeriodo(idDocente, dataInicio, dataFim);
    }

    throw new RegrasRunTime("O docente informado não existe!");
  }

  // public ArrayList<Tecnica> obterTecnicasPPGPorPeriodo(Integer idPrograma,
  // Integer anoInicio, Integer anoFim) {
  // Optional<Programa> programa = programaRepo.findById(idPrograma);

  // // verificando se o docente existe
  // if (programa.isPresent()) {

  // if (anoInicio > anoFim) {
  // Integer dataAuxiliar = anoInicio;

  // anoInicio = anoFim;
  // anoFim = dataAuxiliar;
  // }

  // List<Tecnica> tecnicasPrograma = programa.get().get();

  // // verificando se o docente tem técnicas
  // if (tecnicasDocente.size() > 0) {

  // ArrayList<Tecnica> tecnicasDocenteNoPeriodo = new ArrayList<>();

  // for (Tecnica tecnica : tecnicasDocente) {
  // if (tecnica.getAno() >= anoInicio && tecnica.getAno() <= anoFim) {
  // tecnicasDocenteNoPeriodo.add(tecnica);
  // }
  // }

  // return tecnicasDocenteNoPeriodo;
  // }
  // }

  // throw new RegrasRunTime("O docente informado não existe!");
  // }
}
