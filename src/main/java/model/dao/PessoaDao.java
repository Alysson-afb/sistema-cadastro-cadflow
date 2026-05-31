package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.DB.DB;
import model.classes.Acolhido;
import model.classes.Pessoa;

public class PessoaDao {

    private Connection con;

    public PessoaDao(Connection con) {
        this.con = con;
    }

    public boolean inserirAcolhido(Acolhido acolhido) {
        PreparedStatement stmtPessoa = null;
        PreparedStatement stmtAcolhido = null;

        String sqlPessoa = "INSERT INTO pessoa (nome_completo, nome_social, cpf, data_nascimento, sexo, cor, nacionalidade, naturalidade, estado_civil, profissao, escolaridade, estado_uf, telefone, endereco_atual) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String sqlAcolhido = "INSERT INTO acolhido (fk_id_pessoa, registro_cartorio, info_saude, servicos_acessados, medida_protetiva, historico_rua, avaliacao_interdisciplinar, data_entrada, "
                + "responsavel_acolhimento, contato_responsavel, residia_com, detalhes_acolhimento, motivo_acolhimento, "
                + "plano_objetivo, plano_acoes, plano_responsaveis, plano_prazo_inicio, plano_prazo_fim, observacoes) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            con.setAutoCommit(false);

            stmtPessoa = con.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
            stmtPessoa.setString(1, acolhido.getNome());
            stmtPessoa.setString(2, acolhido.getNomeSocial());
            stmtPessoa.setString(3, acolhido.getCpf());
            stmtPessoa.setDate(4, acolhido.getDataNascimento() != null ? Date.valueOf(acolhido.getDataNascimento()) : null);
            stmtPessoa.setString(5, acolhido.getSexo());
            stmtPessoa.setString(6, acolhido.getCor());
            stmtPessoa.setString(7, acolhido.getNacionalidade());
            stmtPessoa.setString(8, acolhido.getNaturalidade());
            stmtPessoa.setString(9, acolhido.getEstadoCivil());
            stmtPessoa.setString(10, acolhido.getProfissao());
            stmtPessoa.setString(11, acolhido.getEscolaridade());
            stmtPessoa.setString(12, acolhido.getEstadoUF());
            stmtPessoa.setString(13, acolhido.getTelefone());
            stmtPessoa.setString(14, acolhido.getEnderecoAtual());
            stmtPessoa.executeUpdate();

            ResultSet rs = stmtPessoa.getGeneratedKeys();
            int idGerado = 0;
            if (rs.next()) {
                idGerado = rs.getInt(1);
                acolhido.setIdPessoa(idGerado);
            } else {
                throw new SQLException("Erro ID Pessoa");
            }

            stmtAcolhido = con.prepareStatement(sqlAcolhido);
            stmtAcolhido.setInt(1, idGerado);
            stmtAcolhido.setString(2, acolhido.getRegistroCartorio());
            stmtAcolhido.setString(3, acolhido.getInfoSaude());
            stmtAcolhido.setString(4, acolhido.getServicosAcessados());
            stmtAcolhido.setString(5, acolhido.getMedidaProtetiva());
            stmtAcolhido.setString(6, acolhido.getHistoricoRua());
            stmtAcolhido.setString(7, acolhido.getAvaliacaoInterdisciplinar());
            stmtAcolhido.setDate(8, acolhido.getDataEntrada() != null ? Date.valueOf(acolhido.getDataEntrada()) : new Date(System.currentTimeMillis()));

            stmtAcolhido.setString(9, acolhido.getResponsavelAcolhimento());
            stmtAcolhido.setString(10, acolhido.getContatoResponsavel());
            stmtAcolhido.setString(11, acolhido.getResidiaCom());
            stmtAcolhido.setString(12, acolhido.getDetalhesAcolhimento());
            stmtAcolhido.setString(13, acolhido.getMotivoAcolhimento());
            stmtAcolhido.setString(14, acolhido.getPlanoObjetivo());
            stmtAcolhido.setString(15, acolhido.getPlanoAcao());
            stmtAcolhido.setString(16, acolhido.getPlanoResponsaveis());
            stmtAcolhido.setDate(17, acolhido.getPlanoPrazoInicio() != null ? Date.valueOf(acolhido.getPlanoPrazoInicio()) : null);
            stmtAcolhido.setDate(18, acolhido.getPlanoPrazoFim() != null ? Date.valueOf(acolhido.getPlanoPrazoFim()) : null);
            stmtAcolhido.setString(19, acolhido.getObservacoes());

            stmtAcolhido.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
            }
            DB.closeStatement(stmtPessoa);
            DB.closeStatement(stmtAcolhido);
        }
    }

    public boolean inserirPessoa(Pessoa pessoa) {
        PreparedStatement stmt = null;
        String sql = "INSERT INTO pessoa (nome_completo, nome_social, cpf, data_nascimento, "
                + "sexo, cor, nacionalidade, naturalidade, estado_civil, profissao, "
                + "escolaridade, estado_uf, telefone, endereco_atual) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getNomeSocial());
            stmt.setString(3, pessoa.getCpf());
            stmt.setDate(4, pessoa.getDataNascimento() != null ? Date.valueOf(pessoa.getDataNascimento()) : null);
            stmt.setString(5, pessoa.getSexo());
            stmt.setString(6, pessoa.getCor());
            stmt.setString(7, pessoa.getNacionalidade());
            stmt.setString(8, pessoa.getNaturalidade());
            stmt.setString(9, pessoa.getEstadoCivil());
            stmt.setString(10, pessoa.getProfissao());
            stmt.setString(11, pessoa.getEscolaridade());
            stmt.setString(12, pessoa.getEstadoUF());
            stmt.setString(13, pessoa.getTelefone());
            stmt.setString(14, pessoa.getEnderecoAtual());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir pessoa simples: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            DB.closeStatement(stmt);
        }
    }

    public List<Pessoa> getAll() {
        List<Pessoa> list = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet res = null;

        String sql = "SELECT p.pk_cod_pessoa, p.nome_completo, "
                + "CASE WHEN a.fk_id_pessoa IS NOT NULL THEN 1 ELSE 0 END AS eh_acolhido "
                + "FROM pessoa p LEFT JOIN acolhido a ON p.pk_cod_pessoa = a.fk_id_pessoa";

        try {
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                Pessoa p = new Pessoa(
                        res.getInt("pk_cod_pessoa"),
                        res.getString("nome_completo"),
                        res.getInt("eh_acolhido")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
        }
        return list;
    }

    public Acolhido buscarPorId(int id) {
        Acolhido a = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT p.*, ac.* FROM pessoa p "
                + "LEFT JOIN acolhido ac ON p.pk_cod_pessoa = ac.fk_id_pessoa "
                + "WHERE p.pk_cod_pessoa = ?";

        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                a = new Acolhido();

                a.setIdPessoa(rs.getInt("pk_cod_pessoa"));
                a.setNome(rs.getString("nome_completo"));
                a.setNomeSocial(rs.getString("nome_social"));
                a.setCpf(rs.getString("cpf"));
                if (rs.getDate("data_nascimento") != null) {
                    a.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                }
                a.setSexo(rs.getString("sexo"));
                a.setTelefone(rs.getString("telefone"));
                a.setEnderecoAtual(rs.getString("endereco_atual"));
                a.setCor(rs.getString("cor"));
                a.setNacionalidade(rs.getString("nacionalidade"));
                a.setNaturalidade(rs.getString("naturalidade"));
                a.setEstadoCivil(rs.getString("estado_civil"));
                a.setProfissao(rs.getString("profissao"));
                a.setEscolaridade(rs.getString("escolaridade"));
                a.setEstadoUF(rs.getString("estado_uf"));

                if (rs.getObject("fk_id_pessoa") != null) {
                    a.setObservacoes(rs.getString("observacoes"));
                    a.setStatusAcolhido(1);
                    a.setRegistroCartorio(rs.getString("registro_cartorio"));
                    a.setInfoSaude(rs.getString("info_saude"));
                    a.setServicosAcessados(rs.getString("servicos_acessados"));
                    a.setMedidaProtetiva(rs.getString("medida_protetiva"));
                    a.setHistoricoRua(rs.getString("historico_rua"));
                    a.setAvaliacaoInterdisciplinar(rs.getString("avaliacao_interdisciplinar"));
                    if (rs.getDate("data_entrada") != null) {
                        a.setDataEntrada(rs.getDate("data_entrada").toLocalDate());
                    }
                    a.setResponsavelAcolhimento(rs.getString("responsavel_acolhimento"));
                    a.setContatoResponsavel(rs.getString("contato_responsavel"));
                    a.setResidiaCom(rs.getString("residia_com"));
                    a.setDetalhesAcolhimento(rs.getString("detalhes_acolhimento"));
                    a.setMotivoAcolhimento(rs.getString("motivo_acolhimento"));

                    a.setPlanoObjetivo(rs.getString("plano_objetivo"));
                    a.setPlanoAcao(rs.getString("plano_acoes"));
                    a.setPlanoResponsaveis(rs.getString("plano_responsaveis"));

                    if (rs.getDate("plano_prazo_inicio") != null) {
                        a.setPlanoPrazoInicio(rs.getDate("plano_prazo_inicio").toLocalDate());
                    }
                    if (rs.getDate("plano_prazo_fim") != null) {
                        a.setPlanoPrazoFim(rs.getDate("plano_prazo_fim").toLocalDate());
                    }
                } else {
                    a.setStatusAcolhido(0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(stmt);
        }
        return a;
    }

    public boolean atualizar(Acolhido a, boolean ehAcolhido) {
        PreparedStatement stmtPessoa = null;
        PreparedStatement stmtAcolhido = null;

        String sqlPessoa = "UPDATE pessoa SET nome_completo=?, nome_social=?, cpf=?, data_nascimento=?, "
                + "sexo=?, cor=?, nacionalidade=?, naturalidade=?, estado_civil=?, profissao=?, "
                + "escolaridade=?, estado_uf=?, telefone=?, endereco_atual=? WHERE pk_cod_pessoa=?";

        String sqlUpdateAcolhido = "UPDATE acolhido SET registro_cartorio=?, info_saude=?, servicos_acessados=?, "
                + "medida_protetiva=?, historico_rua=?, avaliacao_interdisciplinar=?, data_entrada=?, "
                + "responsavel_acolhimento=?, contato_responsavel=?, residia_com=?, detalhes_acolhimento=?, motivo_acolhimento=?, "
                + "plano_objetivo=?, plano_acoes=?, plano_responsaveis=?, plano_prazo_inicio=?, plano_prazo_fim=?, observacoes=? "
                + "WHERE fk_id_pessoa=?";

        String sqlInsertAcolhido = "INSERT INTO acolhido (fk_id_pessoa, registro_cartorio, info_saude, servicos_acessados, "
                + "medida_protetiva, historico_rua, avaliacao_interdisciplinar, data_entrada, "
                + "responsavel_acolhimento, contato_responsavel, residia_com, detalhes_acolhimento, motivo_acolhimento, "
                + "plano_objetivo, plano_acoes, plano_responsaveis, plano_prazo_inicio, plano_prazo_fim, observacoes) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String sqlDeleteAcolhido = "DELETE FROM acolhido WHERE fk_id_pessoa=?";

        try {
            con.setAutoCommit(false);

            stmtPessoa = con.prepareStatement(sqlPessoa);
            stmtPessoa.setString(1, a.getNome());
            stmtPessoa.setString(2, a.getNomeSocial());
            stmtPessoa.setString(3, a.getCpf());
            stmtPessoa.setDate(4, a.getDataNascimento() != null ? Date.valueOf(a.getDataNascimento()) : null);
            stmtPessoa.setString(5, a.getSexo());
            stmtPessoa.setString(6, a.getCor());
            stmtPessoa.setString(7, a.getNacionalidade());
            stmtPessoa.setString(8, a.getNaturalidade());
            stmtPessoa.setString(9, a.getEstadoCivil());
            stmtPessoa.setString(10, a.getProfissao());
            stmtPessoa.setString(11, a.getEscolaridade());
            stmtPessoa.setString(12, a.getEstadoUF());
            stmtPessoa.setString(11, a.getEscolaridade());
            stmtPessoa.setString(12, a.getEstadoUF());
            stmtPessoa.setString(13, a.getTelefone());
            stmtPessoa.setString(14, a.getEnderecoAtual());

            stmtPessoa.setInt(15, a.getIdPessoa());
            stmtPessoa.executeUpdate();

            if (ehAcolhido) {

                stmtAcolhido = con.prepareStatement(sqlUpdateAcolhido);
                stmtAcolhido.setString(1, a.getRegistroCartorio());
                stmtAcolhido.setString(2, a.getInfoSaude());
                stmtAcolhido.setString(3, a.getServicosAcessados());
                stmtAcolhido.setString(4, a.getMedidaProtetiva());
                stmtAcolhido.setString(5, a.getHistoricoRua());
                stmtAcolhido.setString(6, a.getAvaliacaoInterdisciplinar());
                stmtAcolhido.setDate(7, a.getDataEntrada() != null ? Date.valueOf(a.getDataEntrada()) : null);

                stmtAcolhido.setString(8, a.getResponsavelAcolhimento());
                stmtAcolhido.setString(9, a.getContatoResponsavel());
                stmtAcolhido.setString(10, a.getResidiaCom());
                stmtAcolhido.setString(11, a.getDetalhesAcolhimento());
                stmtAcolhido.setString(12, a.getMotivoAcolhimento());

                stmtAcolhido.setString(13, a.getPlanoObjetivo());
                stmtAcolhido.setString(14, a.getPlanoAcao());
                stmtAcolhido.setString(15, a.getPlanoResponsaveis());
                stmtAcolhido.setDate(16, a.getPlanoPrazoInicio() != null ? Date.valueOf(a.getPlanoPrazoInicio()) : null);
                stmtAcolhido.setDate(17, a.getPlanoPrazoFim() != null ? Date.valueOf(a.getPlanoPrazoFim()) : null);

                stmtAcolhido.setString(18, a.getObservacoes());

                stmtAcolhido.setInt(19, a.getIdPessoa());

                int rows = stmtAcolhido.executeUpdate();

                if (rows == 0) {
                    DB.closeStatement(stmtAcolhido);
                    stmtAcolhido = con.prepareStatement(sqlInsertAcolhido);

                    stmtAcolhido.setInt(1, a.getIdPessoa());
                    stmtAcolhido.setString(2, a.getRegistroCartorio());
                    stmtAcolhido.setString(3, a.getInfoSaude());
                    stmtAcolhido.setString(4, a.getServicosAcessados());
                    stmtAcolhido.setString(5, a.getMedidaProtetiva());
                    stmtAcolhido.setString(6, a.getHistoricoRua());
                    stmtAcolhido.setString(7, a.getAvaliacaoInterdisciplinar());
                    stmtAcolhido.setDate(8, a.getDataEntrada() != null ? Date.valueOf(a.getDataEntrada()) : new Date(System.currentTimeMillis()));
                    stmtAcolhido.setString(9, a.getResponsavelAcolhimento());
                    stmtAcolhido.setString(10, a.getContatoResponsavel());
                    stmtAcolhido.setString(11, a.getResidiaCom());
                    stmtAcolhido.setString(12, a.getDetalhesAcolhimento());
                    stmtAcolhido.setString(13, a.getMotivoAcolhimento());
                    stmtAcolhido.setString(14, a.getPlanoObjetivo());
                    stmtAcolhido.setString(15, a.getPlanoAcao());
                    stmtAcolhido.setString(16, a.getPlanoResponsaveis());
                    stmtAcolhido.setDate(17, a.getPlanoPrazoInicio() != null ? Date.valueOf(a.getPlanoPrazoInicio()) : null);
                    stmtAcolhido.setDate(18, a.getPlanoPrazoFim() != null ? Date.valueOf(a.getPlanoPrazoFim()) : null);

                    stmtAcolhido.setString(19, a.getObservacoes());

                    stmtAcolhido.executeUpdate();
                }
            } else {

                stmtAcolhido = con.prepareStatement(sqlDeleteAcolhido);
                stmtAcolhido.setInt(1, a.getIdPessoa());
                stmtAcolhido.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.closeStatement(stmtPessoa);
            DB.closeStatement(stmtAcolhido);
        }
    }

    public boolean desativar(int id) {
        PreparedStatement stmt = null;

        String sql = "UPDATE pessoa SET ativo = 0 WHERE pk_cod_pessoa = ?";

        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DB.closeStatement(stmt);
        }
    }

    public List<Pessoa> filtrar(int tipoFiltro, boolean mostrarInativos) {
        List<Pessoa> list = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet res = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.pk_cod_pessoa, p.nome_completo, p.cpf, p.ativo, ");
        sql.append("CASE WHEN a.fk_id_pessoa IS NOT NULL THEN 1 ELSE 0 END AS eh_acolhido ");
        sql.append("FROM pessoa p LEFT JOIN acolhido a ON p.pk_cod_pessoa = a.fk_id_pessoa ");
        sql.append("WHERE 1=1 ");

        if (!mostrarInativos) {
            sql.append("AND p.ativo = 1 ");
        }

        if (tipoFiltro == 1) {
            sql.append("AND a.fk_id_pessoa IS NOT NULL ");
        } else if (tipoFiltro == 2) {
            sql.append("AND a.fk_id_pessoa IS NULL ");
        }

        sql.append("ORDER BY p.nome_completo");

        try {
            stmt = con.prepareStatement(sql.toString());
            res = stmt.executeQuery();
            while (res.next()) {
                Pessoa p = new Pessoa(
                        res.getInt("pk_cod_pessoa"),
                        res.getString("nome_completo"),
                        res.getInt("eh_acolhido")
                );
                p.setCpf(res.getString("cpf"));

                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
        }
        return list;
    }

    public java.util.Map<String, Integer> getDadosDashboard() {
        java.util.Map<String, Integer> stats = new java.util.HashMap<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            String sqlAcolhidos = "SELECT COUNT(*) FROM acolhido a JOIN pessoa p ON a.fk_id_pessoa = p.pk_cod_pessoa WHERE p.ativo = 1";
            stmt = con.prepareStatement(sqlAcolhidos);
            rs = stmt.executeQuery();
            if (rs.next()) {
                stats.put("totalAcolhidos", rs.getInt(1));
            }
            DB.closeResultSet(rs);
            DB.closeStatement(stmt);

            String sqlFamiliares = "SELECT COUNT(*) FROM pessoa p LEFT JOIN acolhido a ON p.pk_cod_pessoa = a.fk_id_pessoa WHERE a.fk_id_pessoa IS NULL AND p.ativo = 1";
            stmt = con.prepareStatement(sqlFamiliares);
            rs = stmt.executeQuery();
            if (rs.next()) {
                stats.put("totalFamiliares", rs.getInt(1));
            }
            DB.closeResultSet(rs);
            DB.closeStatement(stmt);

            String sqlSexo = "SELECT p.sexo, COUNT(*) as qtd FROM acolhido a JOIN pessoa p ON a.fk_id_pessoa = p.pk_cod_pessoa WHERE p.ativo = 1 GROUP BY p.sexo";
            stmt = con.prepareStatement(sqlSexo);
            rs = stmt.executeQuery();
            int masc = 0, fem = 0, outros = 0;
            while (rs.next()) {
                String s = rs.getString("sexo");
                if ("M".equals(s)) {
                    masc = rs.getInt("qtd");
                } else if ("F".equals(s)) {
                    fem = rs.getInt("qtd");
                } else {
                    outros += rs.getInt("qtd");
                }
            }
            stats.put("M", masc);
            stats.put("F", fem);
            stats.put("O", outros);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(stmt);
        }
        return stats;
    }

    public int getCapacidadeMaxima() {
        String sql = "SELECT valor FROM configuracao WHERE chave = 'capacidade_maxima'";
        int capacidade = 20;

        try (PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                capacidade = Integer.parseInt(rs.getString("valor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return capacidade;
    }

    public boolean atualizarCapacidadeMaxima(int novaCapacidade) {
        String sql = "UPDATE configuracao SET valor = ? WHERE chave = 'capacidade_maxima'";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, String.valueOf(novaCapacidade));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
