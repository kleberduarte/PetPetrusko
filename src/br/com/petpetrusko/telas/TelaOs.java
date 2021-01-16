/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petpetrusko.telas;

import java.sql.*;
import br.com.petpetrusko.dal.ModuloConexao;
import java.awt.HeadlessException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author klebe
 */
public class TelaOs extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // a linha abaixo cria uma variavel para armazenar um texto de acordo com o radion button selecionado
    private String tipo;

    /**
     * Creates new form TelaOs
     */
    public TelaOs() {
        initComponents();
        conexao = ModuloConexao.conector();

    }

    private void pesquisar_cliente() {
        String sql = "select idcli as Id,nomecli as Nome, fonecli as Fone from tbclientes where nomecli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliPesquisar.getText() + "%");

            rs = pst.executeQuery();
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void setar_campos() {
        int setar = tblClientes.getSelectedRow();
        txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());

    }

    // metodo cadastrar um OS
    private void emitir_os() {
        String sql = "insert into tbos(tipo,situacao,nome_animal,observacao,outros,funcionario,valor,idcli) values (?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOsSit.getSelectedItem().toString());
            pst.setString(3, txtOsNomeAni.getText());
            pst.setString(4, cboOsObserv.getSelectedItem().toString());
            pst.setString(5, txtOsOutros.getText());
            pst.setString(6, txtOsFunc.getText());
            pst.setString(7, txtOsValor.getText().replace(",", "."));
            pst.setString(8, txtCliId.getText());

            // validação dos campos obrigatórios
            if (((txtCliId.getText().isEmpty())|| (txtOsNomeAni.getText().isEmpty())) || (cboOsObserv.getSelectedItem().toString().isEmpty())){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {

                    JOptionPane.showMessageDialog(null, " OS emitida com sucesso");

                    //System.out.println(txtOs);
                    txtCliId.setText(null);
                    txtOsNomeAni.setText(null);
                    cboOsObserv.setSelectedItem(null);
                    txtOsOutros.setText(null);
                    txtOsFunc.setText(null);
                    txtOsValor.setText(null);
                    txtOsPesquisaUltima.setText(null);

                }
            }

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            //System.out.println(e);
        }

    }
    // metodo para pesquisar OS

    private void pesquisa_os() {
        // a linha abaixo cria uma caixa de entrada do tipo Jotion Pane
        String num_os = JOptionPane.showInputDialog("Informe o numero da OS ");
        String sql = "select * from tbos O inner join tbclientes C on(C.idcli = O.idcli) where os =" + num_os;

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                txtOs.setText(rs.getString(1));
                txtData.setText(rs.getString(2));
                // setando os radion buttons
                String rbtTipo = rs.getString(3);
                if (rbtTipo.equals("OS")) {
                    rbtOs.setSelected(true);
                    tipo = "OS";
                } else {
                    rbtOrc.setSelected(true);
                    tipo = "Orçamento";

                }
                cboOsSit.setSelectedItem(rs.getString(4));
                txtOsNomeAni.setText(rs.getString(5));
                cboOsObserv.setSelectedItem(rs.getString(6));
                txtOsOutros.setText(rs.getString(7));
                txtOsFunc.setText(rs.getString(8));
                txtOsValor.setText(rs.getString(9));
                txtCliId.setText(rs.getString(10));
                txtCliNome.setText(rs.getString(12));

                txtOsPesquisaUltima.setText(null);
                // evitando problemas
                btnOsAdicionar.setEnabled(false);
                txtCliPesquisar.setEnabled(false);
                tblClientes.setVisible(false);

            } else {
                JOptionPane.showMessageDialog(null, "OS não cadastrada");
            }

        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS Inválida");
            //System.out.println(e);

        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);

        }
    }

    // Alterar OS
    private void alterar_os() {
        String sql = "update tbos set tipo=?,situacao=?,nome_animal=?,observacao=?,outros=?,funcionario=?,valor=? where os=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOsSit.getSelectedItem().toString());
            pst.setString(3, txtOsNomeAni.getText());
            pst.setString(4, cboOsObserv.getSelectedItem().toString());
            pst.setString(5, txtOsOutros.getText());
            pst.setString(6, txtOsFunc.getText());
            pst.setString(7, txtOsValor.getText().replace(",", "."));
            pst.setString(8, txtOs.getText());

            // validação dos campos obrigatórios
            if (txtCliId.getText().isEmpty() || (txtOsNomeAni.getText().isEmpty()) || (cboOsObserv.getSelectedItem().toString().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");

            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, " OS Alterada com sucesso");

                    txtOs.setText(null);
                    txtData.setText(null);
                    txtCliId.setText(null);
                    txtOsNomeAni.setText(null);
                    cboOsObserv.setSelectedItem(null);
                    txtOsOutros.setText(null);
                    txtOsFunc.setText(null);
                    txtOsValor.setText(null);
                    txtOsPesquisaUltima.setText(null);
                    btnOsAdicionar.setEnabled(true);
                    txtCliPesquisar.setEnabled(true);
                    tblClientes.setVisible(true);

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    // metodo para excluir OS
    private void excluir_os() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir está OS?", "Atenção", JOptionPane.YES_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbos where os=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtOs.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Os excluida com sucesso");
                    txtOs.setText(null);
                    txtData.setText(null);
                    txtCliId.setText(null);
                    txtCliNome.setText(null);
                    txtOsNomeAni.setText(null);
                    cboOsObserv.setSelectedItem(null);
                    txtOsOutros.setText(null);
                    txtOsFunc.setText(null);
                    txtOsValor.setText(null);
                    txtOsPesquisaUltima.setText(null);
                    btnOsAdicionar.setEnabled(true);
                    txtCliPesquisar.setEnabled(true);
                    tblClientes.setVisible(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }

    }

    // metodo para imprimir um OS
    private void imprimir_os() throws JRException {

        // Imprimindo uma os
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão deste OS?", "Atenção", JOptionPane.YES_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            // imprimindo relatório framewor JasperReport
            try {
                //usnado a classe HasMap para criar um flitro
                HashMap filtro = new HashMap();
                filtro.put("os", Integer.parseInt(txtOs.getText()));
                // Usando a classe JasperPrint para preparar a impressao de um relatorio
                JasperPrint print = JasperFillManager.fillReport("C:/reports/os.jasper", filtro, conexao);
                // a linha abaixo exibe o relatorio atraves da classe JasperViewer
                JasperViewer.viewReport(print, false);

            } catch (java.lang.NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Não consta OS a serem impressas");
                //System.out.println(e);
            }
        }
    }

    private void pesquisar_ultima_os() {

        String sql = "select os from tbos where os = (select max(os) from tbos)";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            if (rs.next()) {
                txtOsPesquisaUltima.setText(rs.getString(1));

            } else {
                JOptionPane.showMessageDialog(null, "Não consta OS cadastradas");

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    // metodo para limpar todos os campos
    private void limpar() {
        // as linhas abaixo limpa os campos
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja limpar todos os campos", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_NO_OPTION) {
            txtOs.setText(null);
            txtData.setText(null);
            txtCliId.setText(null);
            txtCliNome.setText(null);
            txtCliPesquisar.setText(null);
            txtOsNomeAni.setText(null);
            cboOsObserv.setSelectedItem(null);
            txtOsOutros.setText(null);
            txtOsFunc.setText(null);
            txtOsValor.setText(null);
            txtOsPesquisaUltima.setText(null);
            txtCliNome.setText(null);
            btnOsAdicionar.setEnabled(true);
            txtCliPesquisar.setEnabled(true);
            tblClientes.setVisible(true);

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOs = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        rbtOrc = new javax.swing.JRadioButton();
        rbtOs = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cboOsSit = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtCliPesquisar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCliId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        txtCliNome = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtOsNomeAni = new javax.swing.JTextField();
        txtOsOutros = new javax.swing.JTextField();
        txtOsFunc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtOsValor = new javax.swing.JTextField();
        btnOsAdicionar = new javax.swing.JButton();
        btnOsPesquisar = new javax.swing.JButton();
        btnOsAlterar = new javax.swing.JButton();
        btnOsExcluir = new javax.swing.JButton();
        btnOsImprimir = new javax.swing.JButton();
        txtOsPesquisaUltima = new javax.swing.JTextField();
        btnOsPequiUltima = new javax.swing.JButton();
        cboOsObserv = new javax.swing.JComboBox<>();
        btnLimpar = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("OS");
        setPreferredSize(new java.awt.Dimension(642, 481));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("N° OS");

        jLabel2.setText("Data");

        txtOs.setEditable(false);
        txtOs.setEnabled(false);

        txtData.setEditable(false);
        txtData.setFont(new java.awt.Font("Dialog", 1, 9)); // NOI18N
        txtData.setEnabled(false);

        buttonGroup1.add(rbtOrc);
        rbtOrc.setText("Orçamento");
        rbtOrc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrcActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOs);
        rbtOs.setText("OS");
        rbtOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(rbtOrc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtOs)
                    .addComponent(jLabel2)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOrc)
                    .addComponent(rbtOs))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setText("Situação");

        cboOsSit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em processo", "Aguardando resposta cliente", "Entregue ao cliente", " " }));
        cboOsSit.setSelectedIndex(3);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petpetrusko/icones/pesquisar.png"))); // NOI18N

        jLabel5.setText("*Id");

        txtCliId.setEnabled(false);

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nome", "Fone"
            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        txtCliNome.setEditable(false);
        txtCliNome.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCliNome)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtCliPesquisar)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addGap(20, 20, 20)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel6.setText("*Nome Animal");

        jLabel7.setText("* Serviços");

        jLabel8.setText("Outros");

        jLabel9.setText("Funcionário");

        jLabel10.setText("Valor Total");

        txtOsValor.setText("0");

        btnOsAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petpetrusko/icones/adicionar.png"))); // NOI18N
        btnOsAdicionar.setToolTipText("Adicionar");
        btnOsAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAdicionarActionPerformed(evt);
            }
        });

        btnOsPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petpetrusko/icones/read.png"))); // NOI18N
        btnOsPesquisar.setToolTipText("Pesquisar");
        btnOsPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsPesquisarActionPerformed(evt);
            }
        });

        btnOsAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petpetrusko/icones/update.png"))); // NOI18N
        btnOsAlterar.setToolTipText("Editar");
        btnOsAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAlterarActionPerformed(evt);
            }
        });

        btnOsExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petpetrusko/icones/delete.png"))); // NOI18N
        btnOsExcluir.setToolTipText("Excluir");
        btnOsExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsExcluirActionPerformed(evt);
            }
        });

        btnOsImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petpetrusko/icones/print.png"))); // NOI18N
        btnOsImprimir.setToolTipText("Imprimir OS");
        btnOsImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsImprimirActionPerformed(evt);
            }
        });

        txtOsPesquisaUltima.setEnabled(false);

        btnOsPequiUltima.setText("Pesquisar ultima OS");
        btnOsPequiUltima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsPequiUltimaActionPerformed(evt);
            }
        });

        cboOsObserv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tosa geral", "Tosa na tesoura", "Tosa cabeça e rabo", "Corte de unha", "Banho ", "Banho + higiene bucal ", "Banho + hidratação ", "Banho + hig.bucal + hidratação ", "Banho + Desembolo ", "Banho + Desembolo + hidratação ", "Banho + Desembolo +Hig.bucal ", "Banho + hig.bucal +hidratação +Desembolo ", "Banho+tosa higiênica + hig.bucal", "Banho +tosa higiênica +Desembolo + hidratação +hig.bucal", " " }));

        btnLimpar.setText("Limpar Campos");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOsAdicionar)
                .addGap(507, 507, 507))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtOsFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(btnOsPequiUltima, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtOsPesquisaUltima, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtOsNomeAni)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnOsExcluir)
                                .addGap(36, 36, 36)
                                .addComponent(btnOsImprimir))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(btnOsPesquisar)
                                .addGap(34, 34, 34)
                                .addComponent(btnOsAlterar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cboOsObserv, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtOsOutros))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboOsSit, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnLimpar))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cboOsSit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtOsNomeAni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cboOsObserv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtOsOutros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtOsFunc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(txtOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtOsPesquisaUltima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnOsPequiUltima, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimpar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOsPesquisar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnOsAlterar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnOsExcluir, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnOsImprimir, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnOsAdicionar, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(27, 27, 27))
        );

        setBounds(0, 0, 642, 492);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // chamando o metodo pesquisar_cliente
        pesquisar_cliente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // chamando o metodo setar_campos
        setar_campos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void rbtOrcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrcActionPerformed
        // atribuindo um texto a variavel tipo se selecionado
        tipo = "Orçamento";

    }//GEN-LAST:event_rbtOrcActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // ao abrir o form marcar o radion button orçamento
        rbtOrc.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnOsAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAdicionarActionPerformed
        // chamar o metodo emitir OS
        emitir_os();
    }//GEN-LAST:event_btnOsAdicionarActionPerformed

    private void btnOsPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsPesquisarActionPerformed
        // chamar o metodo pesquisa_OS
        pesquisa_os();
    }//GEN-LAST:event_btnOsPesquisarActionPerformed

    private void rbtOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOsActionPerformed
        // a linha abaixo atribui um texto a varibael tipo se o raddion button estiver seleconado
        tipo = "OS";

    }//GEN-LAST:event_rbtOsActionPerformed

    private void btnOsAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAlterarActionPerformed
        // chamando metodo Alterar
        alterar_os();
    }//GEN-LAST:event_btnOsAlterarActionPerformed

    private void btnOsExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsExcluirActionPerformed
        // chamando metodo excluir OS
        excluir_os();
    }//GEN-LAST:event_btnOsExcluirActionPerformed

    private void btnOsImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsImprimirActionPerformed
        try {
            // chamando metodo para imprimir uma OS
            imprimir_os();
        } catch (JRException ex) {
            Logger.getLogger(TelaOs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnOsImprimirActionPerformed

    private void btnOsPequiUltimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsPequiUltimaActionPerformed
        // TODO add your handling code here:

        pesquisar_ultima_os();
    }//GEN-LAST:event_btnOsPequiUltimaActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // metodo limpar
        limpar();
    }//GEN-LAST:event_btnLimparActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnOsAdicionar;
    private javax.swing.JButton btnOsAlterar;
    private javax.swing.JButton btnOsExcluir;
    private javax.swing.JButton btnOsImprimir;
    private javax.swing.JButton btnOsPequiUltima;
    private javax.swing.JButton btnOsPesquisar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboOsObserv;
    private javax.swing.JComboBox<String> cboOsSit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JRadioButton rbtOrc;
    private javax.swing.JRadioButton rbtOs;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtOs;
    private javax.swing.JTextField txtOsFunc;
    private javax.swing.JTextField txtOsNomeAni;
    private javax.swing.JTextField txtOsOutros;
    private javax.swing.JTextField txtOsPesquisaUltima;
    private javax.swing.JTextField txtOsValor;
    // End of variables declaration//GEN-END:variables
}
