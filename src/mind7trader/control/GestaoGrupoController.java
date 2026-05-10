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
        if (carregados == null || carregados.isEmpty()) {
            this.grupos = new ArrayList<>();
            // Criar grupos de exemplo para teste
            criarGruposExemplo();
        } else {
            this.grupos = carregados;
        }
    }
    
    // Método para criar grupos de exemplo (apenas para teste)
    private void criarGruposExemplo() {
        // Verificar se já existem grupos
        if (!grupos.isEmpty()) {
            return;
        }
        
        // Criar alguns grupos de exemplo
        Grupo grupo1 = new Grupo(UUID.randomUUID().toString(), "Grupo Poupança Rápida", TipoCiclo.SEIS_MESES, TipoPeriodo.SEMANAL);
        Grupo grupo2 = new Grupo(UUID.randomUUID().toString(), "Grupo Investimento Seguro", TipoCiclo.NOVE_MESES, TipoPeriodo.MENSAL);
        Grupo grupo3 = new Grupo(UUID.randomUUID().toString(), "Grupo Premium", TipoCiclo.DOZE_MESES, TipoPeriodo.MENSAL);
        
        grupos.add(grupo1);
        grupos.add(grupo2);
        grupos.add(grupo3);
        
        salvarGrupos();
        System.out.println("Grupos de exemplo criados: " + grupos.size());
    }
    
    private void salvarGrupos() {
        Ficheiro.salvarListaGrupos(grupos);
    }
    
    // CREATE
    public Grupo criarGrupo(String nome, TipoCiclo ciclo, TipoPeriodo periodo) {
        // Verificar se já existe grupo com mesmo nome
        for (Grupo g : grupos) {
            if (g.getNome().equalsIgnoreCase(nome) && g.getStatus() == Grupo.StatusGrupo.ATIVO) {
                return null; // Grupo já existe
            }
        }
        
        String id = UUID.randomUUID().toString();
        Grupo grupo = new Grupo(id, nome, ciclo, periodo);
        grupos.add(grupo);
        salvarGrupos();
        System.out.println("Grupo criado: " + nome + " - Total grupos: " + grupos.size());
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
        System.out.println("Grupos ativos encontrados: " + ativos.size());
        return ativos;
    }
    
    public List<Grupo> listarTodosGrupos() {
        return new ArrayList<>(grupos);
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
}