/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petpetrusko.telas;

import java.sql.*;
import br.com.petpetrusko.dal.ModuloConexao;
import javax.swing.JOptionPane;
// a linha abaixo importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

/**
 *
 * @author klebe
 */
public class TelaProduto extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaProduto
     */
    public TelaProduto() {
        initComponents();

        conexao = ModuloConexao.conector();
    }

    // metodo para adicionar produtos
    private void adicionar() {
        String sql = "insert into tbprodutos(codigo,descricao,qtd,valor) values (?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtProdCod.getText());
            pst.setString(2, txtProdDescri.getText());
            pst.setString(3, txtProQtd.getText());
            pst.setString(4, txtProdVal.getText().replace(",", "."));

            // validação dos campos obrigatórios
            if ((((txtProdCod.getText().isEmpty()) || (txtProdDescri.getText().isEmpty())) || (txtProQtd.getText().isEmpty())) || (txtProdVal.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios !");

            } else {

                // a linha abaixo é usada para confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve de apoio ao entendimento da lógica
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso !");
                }

                // as linhas abaixo limpa os campos
                txtProdCod.setText(null);
                txtProdDescri.setText(null);
                txtProQtd.setText(null);
                txtProdVal.setText(null);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // metodo para pesquisar pelo nome com filtro
    private void pesquisar_produtos() {
        String sql = "select * from tbprodutos where descricao like ?";
        try {
            pst = conexao.prepareStatement(sql);
            // passando o conteudo da caixa de pesquisa para o ?
            // atenção ao "%" -  continução da String sql
            pst.setString(1, txtProPesquisar.getText() + "%");
            rs = pst.executeQuery();
            // a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblProduto.setModel(DbUtils.resultSetToTableModel(rs));

            // as linhas abaixo limpa os campos
            txtProdCod.setText(null);
            txtProdDescri.setText(null);
            txtProQtd.setText(null);
            txtProdVal.setText(null);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    // metodo para setar os campos do formulario com o conteúdo da tabela
    public void setar_campos() {
        int setar = tblProduto.getSelectedRow();
        txtProdId.setText(tblProduto.getModel().getValueAt(setar, 0).toString());
        txtProdCod.setText(tblProduto.getModel().getValueAt(setar, 1).toString());
        txtProdDescri.setText(tblProduto.getModel().getValueAt(setar, 2).toString());
        txtProQtd.setText(tblProduto.getModel().getValueAt(setar, 3).toString());
        txtProdVal.setText(tblProduto.getModel().getValueAt(setar, 4).toString());
        // a linha abaixo habilita o botão adicionar
        btnAdicionar.setEnabled(false);

    }

    ;
     // criando o metodo para alterar dados do produto
    private void alterar() {
        String sql = "update tbprodutos set codigo=?,descricao=?,qtd=?,valor=? where idproduto=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtProdCod.getText());
            pst.setString(2, txtProdDescri.getText());
            pst.setString(3, txtProQtd.getText());
            pst.setString(4, txtProdVal.getText().replace(",", "."));
            pst.setString(5, txtProdId.getText());

            // validação dos campos obrigatórios
            if (((((txtProdCod.getText().isEmpty()) || (txtProdDescri.getText().isEmpty())) || (txtProQtd.getText().isEmpty()))) || (txtProdVal.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios !");
            } else {

                // a linha abaixo atualiza a tabela usuarios com o dados do formulario
                // a linha abaixo é usada para confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve de apoio ao entendimento da lógica
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Produto alterado com sucesso !");
                    // as linhas abaixo limpa os campos
                    txtProdCod.setText(null);
                    txtProdDescri.setText(null);
                    txtProQtd.setText(null);
                    txtProdVal.setText(null);
                    // a linha abaixo desabilita o botão adicionar
                    btnAdicionar.setEnabled(true);

                }
            }

            // as linhas abaixo limpa os campos
            //txtUsuId.setText(null);
            //txtUsuNome.setText(null);
            //txtUsuFone.setText(null);
            //txtUsuLogin.setText(null);
            //txtUsuSenha.setText(null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void remover() {
        //a estrutura abaixo confirma a remoção do produtos
        if ((((((((((txtProdCod.getText().isEmpty()) || (txtProdDescri.getText().isEmpty())) || (txtProQtd.getText().isEmpty()))) || (txtProdVal.getText().isEmpty()))))))) {

            JOptionPane.showMessageDialog(null, "Nenhum produto informado para realizar a exclusão!");

        } else {

            int confirma = JOptionPane.showConfirmDialog(null, " Tem certeza que deseja excluir este produto", "Atenção", JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_NO_OPTION) {
                String sql = "delete from tbprodutos where idproduto=?";
                try {
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, txtProdId.getText());
                    int excluido = pst.executeUpdate();

                    // a linha abaixo serve de apoio ao entendimento da lógica
                    //System.out.println(excluido);
                    if (excluido > 0) {
                        JOptionPane.showMessageDialog(null, "Produto exluido com sucesso !");
                        // as linhas abaixo limpa os campos
                        txtProdCod.setText(null);
                        txtProdDescri.setText(null);
                        txtProQtd.setText(null);
                        txtProdVal.setText(null);

                        // a linha abaixo habilita o botão adicionar
                        btnAdicionar.setEnabled(true);
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }

    private void limpar() {
        // as linhas abaixo limpa os campos
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja limpar todos os campos", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_NO_OPTION) {
            txtProdId.setText(null);
            txtProdCod.setText(null);
            txtProdDescri.setText(null);
            txtProdDescri.setText(null);
            txtProQtd.setText(null);
            txtProdVal.setText(null);
            
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtProPesquisar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtProdCod = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtProdDescri = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtProQtd = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtProdVal = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProduto = new javax.swing.JTable();
        btnAdicionar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtProdId = new javax.swing.JTextField();
        btnLimpar = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("                         Produtos");
        setPreferredSize(new java.awt.Dimension(642, 481));

        txtProPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProPesquisarKeyReleased(evt);
            }
        });

        jLabel1.setText("*Campos obrigatórios");

        jLabel2.setText("*Codigo");

        jLabel3.setText("*Descrição");

        jLabel4.setText("*QDT");

        jLabel5.setText("*Valor");

        tblProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código", "Descrição", "QTD", "Valor"
            }
        ));
        tblProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdutoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblProduto);

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petpetrusko/icones/adicionar.png"))); // NOI18N
        btnAdicionar.setToolTipText("Adicionar");
        btnAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petpetrusko/icones/update.png"))); // NOI18N
        btnEditar.setToolTipText("Editar");
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petpetrusko/icones/delete.png"))); // NOI18N
        btnExcluir.setToolTipText("Excluir");
        btnExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/petpetrusko/icones/pesquisar.png"))); // NOI18N

        jLabel7.setText("Id Produto");

        txtProdId.setEnabled(false);

        btnLimpar.setText("Limpar");
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addComponent(jLabel7)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtProdDescri, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtProQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtProdId, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtProdCod, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txtProPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel6))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnLimpar))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProdVal, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAdicionar)
                        .addGap(98, 98, 98)
                        .addComponent(btnEditar)
                        .addGap(95, 95, 95)
                        .addComponent(btnExcluir)
                        .addGap(91, 91, 91))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtProPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(3, 3, 3)
                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txtProQtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtProdCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtProdDescri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(txtProdId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProdVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdicionar)
                    .addComponent(btnEditar)
                    .addComponent(btnExcluir))
                .addGap(104, 104, 104))
        );

        getAccessibleContext().setAccessibleName("                                   Produtos");

        setBounds(0, 0, 642, 492);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // chamando metodo adicionar
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed
    // o evento abaixo é do tipo "enquanto for digitado"
    private void txtProPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProPesquisarKeyReleased
        // chamar o metodo pesquisar_produtos
        pesquisar_produtos();
    }//GEN-LAST:event_txtProPesquisarKeyReleased
    // evento que será usado para setar os campos da tabela (clicando com botão esquerdo do mouse)
    private void tblProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutoMouseClicked
        // chamando metodo selecionar
        setar_campos();
    }//GEN-LAST:event_tblProdutoMouseClicked

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // chamando metodo selecionar
        alterar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // chamando metodo remover
        remover();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // TODO add your handling code here:
        limpar();
    }//GEN-LAST:event_btnLimparActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblProduto;
    private javax.swing.JTextField txtProPesquisar;
    private javax.swing.JTextField txtProQtd;
    private javax.swing.JTextField txtProdCod;
    private javax.swing.JTextField txtProdDescri;
    private javax.swing.JTextField txtProdId;
    private javax.swing.JTextField txtProdVal;
    // End of variables declaration//GEN-END:variables
}
