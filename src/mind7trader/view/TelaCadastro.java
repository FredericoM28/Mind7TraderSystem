/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view; 
import model.Cliente;
import control.LoginCliente;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.Random;

public class TelaCadastro extends JFrame {

    // Labels
    JLabel lblNome;
    JLabel lblBI;
    JLabel lblTelefone;
    JLabel lblEmail;
    JLabel lblMorada;
    JLabel lblSenha;
    JLabel lblDataNascimento;
    JLabel lblNomeHerdeiro;
    JLabel lblIdade;

    // TextFields
    JTextField txtNome;
    JTextField txtBI;
    JTextField txtTelefone;
    JTextField txtEmail;
    JTextField txtMorada;
    JPasswordField txtSenha;
    JTextField txtNomeHerdeiro;
    
    // Componentes para data de nascimento
    JComboBox<String> cmbDia;
    JComboBox<String> cmbMes;
    JComboBox<String> cmbAno;
    JLabel lblIdadeValor;

    JButton btnSalvar;

    public TelaCadastro() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastro Cliente - Mind7Trader");
        setSize(700, 680);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 248, 245));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Título
        JLabel titulo = new JLabel("Cadastro de Cliente");
        titulo.setBounds(220, 10, 250, 40);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(20, 40, 80));
        add(titulo);

        JSeparator separator = new JSeparator();
        separator.setBounds(30, 55, 630, 2);
        separator.setForeground(new Color(0, 153, 102));
        add(separator);

        // Linha 1 - Nome
        lblNome = new JLabel("Nome Completo:");
        lblNome.setBounds(50, 80, 130, 30);
        lblNome.setFont(new Font("Arial", Font.BOLD, 13));
        lblNome.setForeground(new Color(50, 50, 50));
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(200, 80, 430, 35);
        txtNome.setFont(new Font("Arial", Font.PLAIN, 13));
        txtNome.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        add(txtNome);

        // Linha 2 - BI
        lblBI = new JLabel("BI/Número ID:");
        lblBI.setBounds(50, 130, 130, 30);
        lblBI.setFont(new Font("Arial", Font.BOLD, 13));
        lblBI.setForeground(new Color(50, 50, 50));
        add(lblBI);

        txtBI = new JTextField();
        txtBI.setBounds(200, 130, 430, 35);
        txtBI.setFont(new Font("Arial", Font.PLAIN, 13));
        txtBI.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        add(txtBI);

        // Linha 3 - Telefone
        lblTelefone = new JLabel("Telefone:");
        lblTelefone.setBounds(50, 180, 130, 30);
        lblTelefone.setFont(new Font("Arial", Font.BOLD, 13));
        lblTelefone.setForeground(new Color(50, 50, 50));
        add(lblTelefone);

        txtTelefone = new JTextField();
        txtTelefone.setBounds(200, 180, 430, 35);
        txtTelefone.setFont(new Font("Arial", Font.PLAIN, 13));
        txtTelefone.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        add(txtTelefone);

        // Linha 4 - Email
        lblEmail = new JLabel("Email:");
        lblEmail.setBounds(50, 230, 130, 30);
        lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
        lblEmail.setForeground(new Color(50, 50, 50));
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(200, 230, 430, 35);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 13));
        txtEmail.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        add(txtEmail);

        // Linha 5 - Morada
        lblMorada = new JLabel("Morada:");
        lblMorada.setBounds(50, 280, 130, 30);
        lblMorada.setFont(new Font("Arial", Font.BOLD, 13));
        lblMorada.setForeground(new Color(50, 50, 50));
        add(lblMorada);

        txtMorada = new JTextField();
        txtMorada.setBounds(200, 280, 430, 35);
        txtMorada.setFont(new Font("Arial", Font.PLAIN, 13));
        txtMorada.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        add(txtMorada);

        // Linha 6 - Data de Nascimento (COM COMBO BOXES)
        lblDataNascimento = new JLabel("Data Nascimento:");
        lblDataNascimento.setBounds(50, 330, 130, 30);
        lblDataNascimento.setFont(new Font("Arial", Font.BOLD, 13));
        lblDataNascimento.setForeground(new Color(50, 50, 50));
        add(lblDataNascimento);

        // ComboBox para Dia (1-31)
        cmbDia = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            cmbDia.addItem(String.format("%02d", i));
        }
        cmbDia.setBounds(200, 330, 70, 35);
        cmbDia.setFont(new Font("Arial", Font.PLAIN, 13));
        cmbDia.setBackground(Color.WHITE);
        add(cmbDia);

        // ComboBox para Mês (1-12)
        cmbMes = new JComboBox<>();
        String[] meses = {"Jan (01)", "Fev (02)", "Mar (03)", "Abr (04)", "Mai (05)", "Jun (06)", 
                          "Jul (07)", "Ago (08)", "Set (09)", "Out (10)", "Nov (11)", "Dez (12)"};
        for (int i = 0; i < meses.length; i++) {
            cmbMes.addItem(meses[i]);
        }
        cmbMes.setBounds(280, 330, 100, 35);
        cmbMes.setFont(new Font("Arial", Font.PLAIN, 13));
        cmbMes.setBackground(Color.WHITE);
        add(cmbMes);

        // ComboBox para Ano (1900 até ano atual)
        cmbAno = new JComboBox<>();
        int anoAtual = Year.now().getValue();
        for (int i = anoAtual; i >= 1900; i--) {
            cmbAno.addItem(String.valueOf(i));
        }
        cmbAno.setBounds(390, 330, 80, 35);
        cmbAno.setFont(new Font("Arial", Font.PLAIN, 13));
        cmbAno.setBackground(Color.WHITE);
        add(cmbAno);

        // Label para mostrar a idade calculada
        lblIdade = new JLabel("Idade:");
        lblIdade.setBounds(490, 330, 50, 30);
        lblIdade.setFont(new Font("Arial", Font.BOLD, 13));
        lblIdade.setForeground(new Color(50, 50, 50));
        add(lblIdade);

        lblIdadeValor = new JLabel("-- anos");
        lblIdadeValor.setBounds(540, 330, 100, 30);
        lblIdadeValor.setFont(new Font("Arial", Font.BOLD, 14));
        lblIdadeValor.setForeground(new Color(0, 153, 102));
        add(lblIdadeValor);

        // Adicionar listeners para calcular idade automaticamente
        cmbDia.addActionListener(e -> calcularIdade());
        cmbMes.addActionListener(e -> calcularIdade());
        cmbAno.addActionListener(e -> calcularIdade());

        // Linha 7 - Nome do Herdeiro
        lblNomeHerdeiro = new JLabel("Nome do Herdeiro:");
        lblNomeHerdeiro.setBounds(50, 390, 130, 30);
        lblNomeHerdeiro.setFont(new Font("Arial", Font.BOLD, 13));
        lblNomeHerdeiro.setForeground(new Color(50, 50, 50));
        add(lblNomeHerdeiro);

        txtNomeHerdeiro = new JTextField();
        txtNomeHerdeiro.setBounds(200, 390, 430, 35);
        txtNomeHerdeiro.setFont(new Font("Arial", Font.PLAIN, 13));
        txtNomeHerdeiro.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        txtNomeHerdeiro.setToolTipText("Nome da pessoa que receberá os fundos em caso de falecimento");
        add(txtNomeHerdeiro);

        // Linha 8 - Senha
        lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(50, 450, 130, 30);
        lblSenha.setFont(new Font("Arial", Font.BOLD, 13));
        lblSenha.setForeground(new Color(50, 50, 50));
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(200, 450, 430, 35);
        txtSenha.setFont(new Font("Arial", Font.PLAIN, 13));
        txtSenha.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        add(txtSenha);

        // Botão Salvar
        btnSalvar = new JButton("Salvar Cliente");
        btnSalvar.setBounds(200, 520, 200, 45);
        btnSalvar.setBackground(new Color(0, 153, 102));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnSalvar);

        // Botão Cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(430, 520, 130, 45);
        btnCancelar.setBackground(new Color(200, 60, 60));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);

        // Botão Limpar
        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(580, 520, 80, 45);
        btnLimpar.setBackground(new Color(100, 100, 150));
        btnLimpar.setForeground(Color.WHITE);
        btnLimpar.setFont(new Font("Arial", Font.BOLD, 12));
        btnLimpar.setFocusPainted(false);
        btnLimpar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpar.addActionListener(e -> limparCampos());
        add(btnLimpar);

        // Rodapé informativo
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setBounds(50, 590, 600, 40);
        infoPanel.setBackground(new Color(255, 245, 235));
        infoPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 100, 0), 1));
        
        JLabel lblInfo = new JLabel("⚠️ É necessário ter mais de 18 anos para abrir uma conta. O herdeiro não pode ser o próprio cliente.");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 11));
        lblInfo.setForeground(new Color(200, 100, 0));
        infoPanel.add(lblInfo);
        add(infoPanel);

        // Ação do botão Salvar
        btnSalvar.addActionListener(e -> salvarCliente());

        setVisible(true);
    }

    /**
     * Obtém a data de nascimento selecionada nos ComboBoxes
     */
    private LocalDate getDataNascimento() {
        try {
            int dia = Integer.parseInt((String) cmbDia.getSelectedItem());
            int mes = cmbMes.getSelectedIndex() + 1;
            int ano = Integer.parseInt((String) cmbAno.getSelectedItem());
            return LocalDate.of(ano, mes, dia);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Calcula a idade automaticamente baseado nos valores dos ComboBoxes
     */
    private void calcularIdade() {
        LocalDate dataNascimento = getDataNascimento();
        if (dataNascimento == null) {
            lblIdadeValor.setText("-- anos");
            lblIdadeValor.setForeground(new Color(0, 153, 102));
            return;
        }

        try {
            LocalDate hoje = LocalDate.now();
            int idade = Period.between(dataNascimento, hoje).getYears();
            
            if (idade >= 0) {
                lblIdadeValor.setText(idade + " anos");
                
                if (idade < 18) {
                    lblIdadeValor.setForeground(Color.RED);
                } else {
                    lblIdadeValor.setForeground(new Color(0, 153, 102));
                }
            } else {
                lblIdadeValor.setText("Data futura!");
                lblIdadeValor.setForeground(Color.RED);
            }
        } catch (Exception e) {
            lblIdadeValor.setText("Erro");
            lblIdadeValor.setForeground(Color.RED);
        }
    }

    /**
     * Obtém a idade do cliente
     */
    private int getIdade() {
        LocalDate dataNascimento = getDataNascimento();
        if (dataNascimento == null) {
            return -1;
        }
        LocalDate hoje = LocalDate.now();
        return Period.between(dataNascimento, hoje).getYears();
    }

    /**
     * Formata a data para string no padrão DD/MM/AAAA
     */
    private String getDataFormatada() {
        String dia = (String) cmbDia.getSelectedItem();
        int mesNum = cmbMes.getSelectedIndex() + 1;
        String mes = String.format("%02d", mesNum);
        String ano = (String) cmbAno.getSelectedItem();
        return dia + "/" + mes + "/" + ano;
    }

    /**
     * Valida todos os campos e salva o cliente
     */
    private void salvarCliente() {
        // Validar campos obrigatórios
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o Nome Completo!", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            txtNome.requestFocus();
            return;
        }

        if (txtBI.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o BI/Número de Identificação!", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            txtBI.requestFocus();
            return;
        }

        if (txtTelefone.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o Telefone!", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            txtTelefone.requestFocus();
            return;
        }

        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o Email!", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            txtEmail.requestFocus();
            return;
        }

        if (txtMorada.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha a Morada!", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            txtMorada.requestFocus();
            return;
        }

        if (txtNomeHerdeiro.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o Nome do Herdeiro!\nEsta pessoa receberá os fundos em caso de falecimento.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            txtNomeHerdeiro.requestFocus();
            return;
        }

        String senha = new String(txtSenha.getPassword());
        if (senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha a Senha!", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            txtSenha.requestFocus();
            return;
        }

        // Validar telefone
        LoginCliente control = new LoginCliente();
        if (!control.validarTelefone(txtTelefone.getText())) {
            JOptionPane.showMessageDialog(this, "Número de telefone inválido!\nUse números: 84,85,86,87 seguido de 7 dígitos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            txtTelefone.requestFocus();
            return;
        }

        // Validar email
        if (!control.validarEmail(txtEmail.getText())) {
            JOptionPane.showMessageDialog(this, "Email inválido!\nExemplo: nome@dominio.com", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return;
        }

        // Validar data de nascimento
        LocalDate dataNascimento = getDataNascimento();
        if (dataNascimento == null) {
            JOptionPane.showMessageDialog(this, "Data de nascimento inválida!\nVerifique os valores selecionados.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar se data não é futura
        if (dataNascimento.isAfter(LocalDate.now())) {
            JOptionPane.showMessageDialog(this, "Data de nascimento não pode ser no futuro!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // VALIDAÇÃO DE IDADE - Deve ser maior de 18 anos
        int idade = getIdade();
        if (idade < 18) {
            JOptionPane.showMessageDialog(this, 
                " Não é possível criar a conta!\n\n" +
                "O cliente tem apenas " + idade + " anos.\n" +
                "É necessário ter pelo menos 18 anos para abrir uma conta de poupança.\n\n" +
                "Data de nascimento selecionada: " + getDataFormatada(),
                "Idade Mínima não atingida", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar se o herdeiro não é o próprio cliente
        String nomeHerdeiro = txtNomeHerdeiro.getText().trim();
        String nomeCliente = txtNome.getText().trim();
        if (nomeHerdeiro.equalsIgnoreCase(nomeCliente)) {
            JOptionPane.showMessageDialog(this, 
                " Atenção: O herdeiro não pode ser o próprio cliente!\n\n" +
                "Por favor, indique outra pessoa como herdeiro.",
                "Herdeiro Inválido",
                JOptionPane.WARNING_MESSAGE);
            txtNomeHerdeiro.requestFocus();
            return;
        }

        // Gerar número da conta
        String conta = gerarNumeroConta();

        // Criar o cliente
        Cliente cliente = new Cliente(
            conta,
            txtNome.getText().trim(),
            txtBI.getText().trim(),
            txtTelefone.getText().trim(),
            txtEmail.getText().trim(),
            txtMorada.getText().trim(),
            0,  // Saldo inicial
            senha,
            true,  // Ativo
            dataNascimento,
            nomeHerdeiro
        );

        // Salvar cliente
        control.salvar(cliente);

        // Mostrar mensagem de sucesso
        JOptionPane.showMessageDialog(this, 
            " Cliente registado com sucesso!\n\n" +
            " Dados do Cliente:\n" +
            "─────────────────────\n" +
            " Nº Conta: " + conta + "\n" +
            " Nome: " + cliente.getNome() + "\n" +
            " Data Nascimento: " + getDataFormatada() + "\n" +
            " Idade: " + idade + " anos\n" +
            " Herdeiro: " + nomeHerdeiro + "\n" +
            "─────────────────────\n\n" +
            " Guarde o número da conta com segurança!\n" +
            "Ele será necessário para futuras operações.",
            "Sucesso", 
            JOptionPane.INFORMATION_MESSAGE);

        // Limpar campos para próximo cadastro
        limparCampos();
    }

    /**
     * Limpa todos os campos do formulário
     */
    private void limparCampos() {
        txtNome.setText("");
        txtBI.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtMorada.setText("");
        txtNomeHerdeiro.setText("");
        txtSenha.setText("");
        
        // Resetar ComboBoxes de data
        cmbDia.setSelectedIndex(0);
        cmbMes.setSelectedIndex(0);
        cmbAno.setSelectedIndex(0);
        
        lblIdadeValor.setText("-- anos");
        lblIdadeValor.setForeground(new Color(0, 153, 102));
        
        txtNome.requestFocus();
    }

    /**
     * Gera número da conta automaticamente
     */
    public String gerarNumeroConta() {
        int ano = Year.now().getValue();
        Random random = new Random();
        int numero = random.nextInt(9000) + 1000;
        return ano + String.valueOf(numero);
    }
}