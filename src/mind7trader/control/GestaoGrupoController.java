package control;

import model.Grupo;
import model.Grupo.TipoCiclo;
import model.Grupo.TipoPeriodo;
import util.Ficheiro;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GestaoGrupoController {
    
    private List<Grupo> grupos;
    
    public GestaoGrupoController() {
        carregarGrupos();
    }
    
    private void carregarGrupos() {
        List<Grupo> carregados = Ficheiro.carregarGrupos();
        if (carregados == null) {
            this.grupos = new ArrayList<>();
        } else {
            this.grupos = carregados;
        }
    }
    
    private void salvarGrupos() {
        Ficheiro.salvarListaGrupos(grupos);
    }
    
    // CREATE
    public Grupo criarGrupo(String nome, TipoCiclo ciclo, TipoPeriodo periodo) {
        String id = UUID.randomUUID().toString();
        Grupo grupo = new Grupo(id, nome, ciclo, periodo);
        grupos.add(grupo);
        salvarGrupos();
        return grupo;
    }
    
    // READ
    public Grupo buscarGrupoPorId(String id) {
        for (Grupo grupo : grupos) {
            if (grupo.getId().equals(id) && grupo.getStatus() != Grupo.StatusGrupo.CANCELADO) {
                return grupo;
            }
        }
        return null;
    }
    
    public List<Grupo> listarGruposPorPeriodo(TipoPeriodo periodo) {
        List<Grupo> resultado = new ArrayList<>();
        for (Grupo grupo : grupos) {
            if (grupo.getPeriodo() == periodo && grupo.getStatus() == Grupo.StatusGrupo.ATIVO) {
                resultado.add(grupo);
            }
        }
        return resultado;
    }
    
    public List<Grupo> listarGruposPorCiclo(TipoCiclo ciclo) {
        List<Grupo> resultado = new ArrayList<>();
        for (Grupo grupo : grupos) {
            if (grupo.getCiclo() == ciclo && grupo.getStatus() == Grupo.StatusGrupo.ATIVO) {
                resultado.add(grupo);
            }
        }
        return resultado;
    }
    
    public List<Grupo> listarTodosGruposAtivos() {
        List<Grupo> ativos = new ArrayList<>();
        for (Grupo grupo : grupos) {
            if (grupo.getStatus() == Grupo.StatusGrupo.ATIVO) {
                ativos.add(grupo);
            }
        }
        return ativos;
    }
    
    // UPDATE
    public boolean adicionarMembroAoGrupo(String idGrupo, String idCliente) {
        Grupo grupo = buscarGrupoPorId(idGrupo);
        if (grupo != null) {
            grupo.adicionarMembro(idCliente);
            salvarGrupos();
            return true;
        }
        return false;
    }
    
    public boolean removerMembroDoGrupo(String idGrupo, String idCliente) {
        Grupo grupo = buscarGrupoPorId(idGrupo);
        if (grupo != null) {
            grupo.removerMembro(idCliente);
            salvarGrupos();
            return true;
        }
        return false;
    }
    
    public boolean finalizarGrupo(String idGrupo) {
        Grupo grupo = buscarGrupoPorId(idGrupo);
        if (grupo != null) {
            grupo.setStatus(Grupo.StatusGrupo.FINALIZADO);
            salvarGrupos();
            return true;
        }
        return false;
    }
    
    // RELATÓRIOS
    public double getSaldoTotalGrupo(String idGrupo) {
        Grupo grupo = buscarGrupoPorId(idGrupo);
        return grupo != null ? grupo.getSaldoTotalGrupo() : 0;
    }
    
    public int getQuantidadeMembrosGrupo(String idGrupo) {
        Grupo grupo = buscarGrupoPorId(idGrupo);
        return grupo != null ? grupo.getQuantidadeMembros() : 0;
    }

    public boolean atualizarSaldoTotalGrupo(String idGrupo, double valor) {
    Grupo grupo = buscarGrupoPorId(idGrupo); // Supondo que exista um método para buscar o grupo
    if (grupo != null) {
        grupo.adicionarSaldoTotal(valor); // Supondo que Grupo tenha um método para adicionar ao saldo
        salvarGrupos(); // Persistir as mudanças, similar ao salvarEmprestimos
        return true;
    }
    return false;
}
}
