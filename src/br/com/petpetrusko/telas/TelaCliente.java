/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petpetrusko.telas;

import java.sql.*;
import br.com.petpetrusko.dal.ModuloConexao;
import java.awt.HeadlessException;
import java.util.Arrays;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
// a linha abaixo importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

/**
 *
 * @author klebe
 */
public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();

    }

    // metodo para adicionar clientes
    private void adicionar() {
        String sql = "insert into tbclientes(nomecli,fonecli,emailcli,endcli,cepcli,cidadecli,ufcli,nomeanimalcli,racacli) values (?,?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliFone.getText());
            pst.setString(3, txtCliEmail.getText());
            pst.setString(4, txtCliEndereco.getText());
            pst.setString(5, txtCliCep.getText());
            pst.setString(6, txtCliCidade.getText());
            pst.setString(7, txtCliUf.getText());
            pst.setString(8, txtCliNomeAni.getText());
            pst.setString(9, cboCliRaca.getSelectedItem().toString());

            // validação dos campos obrigatórios
            if (((txtCliNome.getText().isEmpty()) || (txtCliFone.getText().isEmpty())) || (txtCliCep.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios !");

            } else {

                // a linha abaixo atualiza a tabela usuarios com o dados do formulario
                // a linha abaixo é usada para confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve de apoio ao entendimento da lógica
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso !");
                }

                // as linhas abaixo limpa os campos
                txtCliNome.setText(null);
                txtCliEndereco.setText(null);
                txtCliFone.setText(null);
                txtCliEmail.setText(null);
                txtCliCep.setText(null);
                txtCliCidade.setText(null);
                txtCliNomeAni.setText(null);
                cboCliRaca.setSelectedIndex(0);
                txtCliUf.setText(null);

            }

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            //System.out.println(e);
        }
    }

    // metodo para pesquisar pelo nome com filtro
    private void pesquisar_cliente() {

        String sql = "select idcli as ID,nomecli as Nome, endcli as Endereço,fonecli as Fone,emailcli as Email,cepcli as Cep,cidadecli as Cidade,ufcli as UF,nomeanimalcli as Animal,racacli as Raça from tbclientes where nomecli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            // passando o conteudo da caixa de pesquisa para o ?
            // atenção ao "%" -  continução da String sql
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();
            // a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
            // as linhas abaixo limpa os campos
            txtCliNome.setText(null);
            txtCliEndereco.setText(null);
            txtCliFone.setText(null);
            txtCliEmail.setText(null);
            txtCliCep.setText(null);
            txtCliCidade.setText(null);
            txtCliNomeAni.setText(null);
            cboCliRaca.setSelectedIndex(0);
            txtCliUf.setText(null);
            btnAdicionar.setEnabled(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    ;
      // metodo para setar os campos do formulario com o conteúdo da tabela
    public void setar_campos() {
        int setar = tblClientes.getSelectedRow();
        txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
        txtCliNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
        txtCliEndereco.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
        txtCliFone.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
        txtCliEmail.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
        txtCliCep.setText(tblClientes.getModel().getValueAt(setar, 5).toString());
        txtCliCidade.setText(tblClientes.getModel().getValueAt(setar, 6).toString());
        txtCliUf.setText(tblClientes.getModel().getValueAt(setar, 7).toString());
        txtCliNomeAni.setText(tblClientes.getModel().getValueAt(setar, 8).toString());
        cboCliRaca.setSelectedItem(tblClientes.getModel().getValueAt(setar, 9).toString());

        // a linha abaixo desabilita o botão adicionar
        btnAdicionar.setEnabled(false);

    }

    // criando o metodo para alterar dados do cliente
    private void alterar() {
        String sql = "update tbclientes set nomecli=?,fonecli=?,emailcli=?,endcli=?,cepcli =?,cidadecli=?,ufcli=?,nomeanimalcli=?,racacli=? where idcli=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliFone.getText());
            pst.setString(3, txtCliEmail.getText());
            pst.setString(4, txtCliEndereco.getText());
            pst.setString(5, txtCliCep.getText());
            pst.setString(6, txtCliCidade.getText());
            pst.setString(7, txtCliUf.getText());
            pst.setString(8, txtCliNomeAni.getText());
            pst.setString(9, cboCliRaca.getSelectedItem().toString());
            pst.setString(10, txtCliId.getText());

            // validação dos campos obrigatórios
            if ((((txtCliNome.getText().isEmpty()) || (txtCliFone.getText().isEmpty())) || (txtCliCep.getText().isEmpty()))) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios !");
            } else {

                // a linha abaixo atualiza a tabela usuarios com o dados do formulario
                // a linha abaixo é usada para confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve de apoio ao entendimento da lógica
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do cliente alterados com sucesso !");
                    // as linhas abaixo limpa os campos
                    txtCliNome.setText(null);
                    txtCliFone.setText(null);
                    txtCliEmail.setText(null);
                    txtCliEndereco.setText(null);
                    txtCliCep.setText(null);
                    txtCliCidade.setText(null);
                    txtCliNomeAni.setText(null);
                    cboCliRaca.setSelectedIndex(0);
                    txtCliUf.setText(null);

                    // a linha abaixo habilita o botão adicionar
                    btnAdicionar.setEnabled(true);

                }
            }

            // as linhas abaixo limpa os campos
            //txtUsuId.setText(null);
            //txtUsuNome.setText(null);
            //txtUsuFone.setText(null);
            //txtUsuLogin.setText(null);
            //txtUsuSenha.setText(null);
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    // metodo para excluir clientes

    private void remover() {
        //a estrutura abaixo confirma a remoção do cliente
        if ((((txtCliNome.getText().isEmpty()) || (txtCliFone.getText().isEmpty())) || (txtCliCep.getText().isEmpty()))) {

            JOptionPane.showMessageDialog(null, "Nenhum cliente informado para realizar a exclusão!");

        } else {

            int confirma = JOptionPane.showConfirmDialog(null, " Tem certeza que deseja excluir este cliente", "Atenção", JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_NO_OPTION) {
                String sql = "delete from tbclientes where idcli=?";
                try {
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, txtCliId.getText());
                    int excluido = pst.executeUpdate();

                    // a linha abaixo serve de apoio ao entendimento da lógica
                    //System.out.println(excluido);
                    if (excluido > 0) {
                        JOptionPane.showMessageDialog(null, "Dados do cliente exluido com sucesso !");
                        // as linhas abaixo limpa os campos
                        txtCliNome.setText(null);
                        txtCliEndereco.setText(null);
                        txtCliFone.setText(null);
                        txtCliEmail.setText(null);
                        txtCliCep.setText(null);
                        txtCliCidade.setText(null);
                        txtCliNomeAni.setText(null);
                        cboCliRaca.setSelectedIndex(0);
                        txtCliUf.setText(null);
                        // a linha abaixo habilita o botão adicionar
                        btnAdicionar.setEnabled(true);
                    }

                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }
    // metodo para limpar todos os campos

    private void limpar() {
        // as linhas abaixo limpa os campos
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja limpar todos os campos", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_NO_OPTION) {
            txtCliId.setText(null);
            txtCliNome.setText(null);
            txtCliEndereco.setText(null);
            txtCliFone.setText(null);
            txtCliEmail.setText(null);
            txtCliCep.setText(null);
            txtCliCidade.setText(null);
            txtCliNomeAni.setText(null);
            cboCliRaca.setSelectedIndex(0);
            txtCliUf.setText(null);
            txtCliPesquisar.setText(null);
            btnAdicionar.setEnabled(true);

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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCliNome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCliEndereco = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCliCep = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCliCidade = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCliFone = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCliEmail = new javax.swing.JTextField();
        btnAdicionar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        txtCliPesquisar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtCliId = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCliNomeAni = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnLimpar = new javax.swing.JButton();
        btnConsultarCep = new javax.swing.JButton();
        txtCliUf = new javax.swing.JTextField();
        cboCliRaca = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Clientes");
        setPreferredSize(new java.awt.Dimension(642, 481));

        jLabel1.setText("*Campos obrigatórios");

        jLabel2.setText("*Nome");

        jLabel3.setText("Endereço");

        jLabel4.setText("*CEP");

        jLabel5.setText("Cidade");

        jLabel6.setText("UF");

        jLabel7.setText("*Telefone");

        jLabel8.setText("Email");

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petpetrusko/icones/adicionar.png"))); // NOI18N
        btnAdicionar.setToolTipText("Adicionar");
        btnAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petpetrusko/icones/update.png"))); // NOI18N
        btnAlterar.setToolTipText("Editar");
        btnAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petpetrusko/icones/delete.png"))); // NOI18N
        btnRemover.setToolTipText("Excluir");
        btnRemover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petpetrusko/icones/pesquisar.png"))); // NOI18N

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nome", "Telefone", "Email", "Nome Animal", "Raça", "CEP", "Endereço", "Cidade", "UF"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        jLabel9.setText("Id Cliente");

        txtCliId.setEnabled(false);

        jLabel11.setText("Nome Animal");

        jLabel12.setText("Raça");

        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnConsultarCep.setText("Consultar CEP");
        btnConsultarCep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarCepActionPerformed(evt);
            }
        });

        cboCliRaca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Outros", "Basset", "Border colie", "Buldogue  inglês", "Buldogue franês", "Chihuahua", "Dámalta", "Fila", "Golden Retriever", "Husky siberiano", "Labrador", "Lhasa", "MAltês", "Poodle", "Pug", "Salsicha", "Samoieda", "Schunauzer", "Shiatsu", "SRD", "Terra nova", "West Terrier", "Yorshire" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(25, 25, 25))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(3, 3, 3)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel9)
                                        .addGap(24, 24, 24)
                                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtCliNomeAni)
                                                .addGap(78, 78, 78)
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cboCliRaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtCliFone, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtCliEndereco)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(txtCliCep, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(31, 31, 31)
                                                        .addComponent(btnConsultarCep, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(txtCliCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(26, 26, 26)
                                                        .addComponent(jLabel6)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(txtCliUf, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 0, Short.MAX_VALUE))))))
                            .addComponent(btnLimpar))
                        .addContainerGap(16, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116)
                .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116)
                .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCliFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboCliRaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txtCliNomeAni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCliCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnConsultarCep))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtCliCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCliUf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRemover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAlterar))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName("                                              Clientes");

        setBounds(0, 0, 642, 492);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // Chamando o metodo adicionar
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed
    // o evento abaixo é do tipo "enquanto for digitado"
    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // chamar o metodo pesquisar_clientes
        pesquisar_cliente();

    }//GEN-LAST:event_txtCliPesquisarKeyReleased
    // evento que será usado para setar os campos da tabela (clicando com botão esquerdo do mouse)
    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // chamando o metodo setar os campos
        setar_campos();


    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // chamando metodo alterar
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // chamando metodo remover
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // metodo limpar (botão limpar)
        limpar();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnConsultarCepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarCepActionPerformed
        // consulta Cep Correios

        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();

        try {
            String cep = txtCliCep.getText();
            br.com.correios.bsb.sigep.master.bean.cliente.EnderecoERP result = port.consultaCEP(cep);

            txtCliEndereco.setText(result.getEnd() + " - " + result.getBairro());
            txtCliCidade.setText(result.getCidade());
            txtCliUf.setText(result.getUf());

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro ao consultar WebServices dos Correios", "Erro", JOptionPane.ERROR_MESSAGE);

        }


    }//GEN-LAST:event_btnConsultarCepActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnConsultarCep;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JComboBox<String> cboCliRaca;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliCep;
    private javax.swing.JTextField txtCliCidade;
    private javax.swing.JTextField txtCliEmail;
    private javax.swing.JTextField txtCliEndereco;
    private javax.swing.JTextField txtCliFone;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliNomeAni;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtCliUf;
    // End of variables declaration//GEN-END:variables
}
