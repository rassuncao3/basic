/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.basic.view;

import br.com.basic.dal.ModuleConection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Clock;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class FrmOs extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private String status;
    private String type;

    public FrmOs() {
        initComponents();
        conexao = ModuleConection.conector();
    }

    private void pesquisar_cli() {
        String sql = "select idcostumers as id,name as nome,celular as cel from tblcostumers where name like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, Pesquisa.getText() + "%");
            rs = pst.executeQuery();
            Table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Pesquisar");
        }
    }

    private void set_campos() {
        int set = Table.getSelectedRow();
        id.setText(Table.getModel().getValueAt(set, 0).toString());
    }

    private void emission_os() {
        String sql = "insert into tblos (tipo,status,equipament,defect,service,technical,value,idcostumers)values(?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, type);
            pst.setString(2, CbStatus.getSelectedItem().toString());
            pst.setString(3, equipamento.getText());
            pst.setString(4, Defeito.getText());
            pst.setString(5, Servico.getText());
            pst.setString(6, Tecnico.getText());
            pst.setString(7, Valor.getText().replace(",", "."));
            pst.setString(8, id.getText());

            if ((id.getText().isEmpty()) || (equipamento.getText().isEmpty()) || Defeito.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos Obrigatorios");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Emitida a OS");
                    id.setText(null);
                    equipamento.setText(null);
                    Defeito.setText(null);
                    Servico.setText(null);
                    Tecnico.setText(null);
                    Valor.setText(null);
                }
            }

        } catch (Exception e) {
        }

    }

    private void pesquisar_os() {
        String num_os = JOptionPane.showInputDialog("Numero da Ordem de Servico");
        String sql = "Select *from tblos where os=" + num_os;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            //   
            if (rs.next()) {
                Nos.setText(rs.getString(1));
                Data.setText(rs.getString(2));
                String option = rs.getString(3);
                if (option.equals("os")) {
                    OrdemServico.setSelected(true);
                    type = "os";

                } else {
                    Orcamento.setSelected(true);
                    type = "Orcamento";

                }
                CbStatus.setSelectedItem(rs.getString(4));
                equipamento.setText(rs.getString(5));
                Defeito.setText(rs.getString(6));
                Servico.setText(rs.getString(7));
                Tecnico.setText(rs.getString(8));
                Valor.setText(rs.getString(9));
                id.setText(rs.getString(10));
                btnSalvar.setEnabled(false);
                Pesquisa.setEnabled(false);
                Table.setVisible(false);

            } else {
                JOptionPane.showMessageDialog(null, "OS nao encontrada");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO" + e);
            //System.out.println(e);

        }
    }

    private void alter_os() {
        String sql = "update tblos set status=?,tipo=?,equipament=?,defect=?,service=?,technical=?,value=? where os=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, type);
            pst.setString(2, CbStatus.getSelectedItem().toString());
            pst.setString(3, equipamento.getText());
            pst.setString(4, Defeito.getText());
            pst.setString(5, Servico.getText());
            pst.setString(6, Tecnico.getText());
            pst.setString(7, Valor.getText());
            pst.setString(8, id.getText());
            if ((id.getText().isEmpty()) || (equipamento.getText().isEmpty()) || Defeito.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos Obrigatorios");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Alterado a  OS");
                    Data.setText(null);
                    Nos.setText(null);
                    id.setText(null);
                    equipamento.setText(null);
                    Defeito.setText(null);
                    Servico.setText(null);
                    Tecnico.setText(null);
                    Valor.setText(null);
                    btnSalvar.setEnabled(true);
                    Pesquisa.setEnabled(true);
                    Table.setEnabled(true);
                }
            }
        } catch (Exception e) {

        }
    }

    private void delete() {
        int confirma = JOptionPane.showConfirmDialog(null, "apagar ?", "Aencao", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tblos where os=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, id.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Os excluida com sucesso");
                    Data.setText(null);
                    Nos.setText(null);
                    id.setText(null);
                    equipamento.setText(null);
                    Defeito.setText(null);
                    Servico.setText(null);
                    Tecnico.setText(null);
                    Valor.setText(null);
                    btnSalvar.setEnabled(true);
                    Pesquisa.setEnabled(true);
                    Table.setEnabled(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "APAGAR ?" + e);
            }

        } else {
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
        jPanel1 = new javax.swing.JPanel();
        jDesktopPane4 = new javax.swing.JDesktopPane();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        OrdemServico = new javax.swing.JRadioButton();
        Orcamento = new javax.swing.JRadioButton();
        CbStatus = new javax.swing.JComboBox<>();
        Nos = new javax.swing.JTextField();
        Data = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        Pesquisa = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        equipamento = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        Defeito = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Servico = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        Tecnico = new javax.swing.JTextField();
        Valor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnDeletar = new javax.swing.JButton();
        btnPrint1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ordem de Servico/Orcamento");
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

        jDesktopPane4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("DATA");

        jLabel3.setText("Status Atual:.");

        jDesktopPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tipo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        buttonGroup1.add(OrdemServico);
        OrdemServico.setText("Os");
        OrdemServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrdemServicoActionPerformed(evt);
            }
        });

        buttonGroup1.add(Orcamento);
        Orcamento.setText("Orcamento");
        Orcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrcamentoActionPerformed(evt);
            }
        });

        jDesktopPane1.setLayer(OrdemServico, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(Orcamento, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                .addComponent(Orcamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(OrdemServico)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OrdemServico)
                    .addComponent(Orcamento))
                .addContainerGap())
        );

        CbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entrega ok", "Aguardando Aprovacao", "Aguardando pecas", "Abandonado pelo Cliente", "Na Bancada", "Retornou" }));

        Nos.setEditable(false);

        Data.setEditable(false);
        Data.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));

        jLabel1.setText("N ORDEM SERVICO");

        jDesktopPane4.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane4.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane4.setLayer(jDesktopPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane4.setLayer(CbStatus, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane4.setLayer(Nos, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane4.setLayer(Data, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane4.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane4Layout = new javax.swing.GroupLayout(jDesktopPane4);
        jDesktopPane4.setLayout(jDesktopPane4Layout);
        jDesktopPane4Layout.setHorizontalGroup(
            jDesktopPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jDesktopPane4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CbStatus, 0, 1, Short.MAX_VALUE))
                    .addGroup(jDesktopPane4Layout.createSequentialGroup()
                        .addGroup(jDesktopPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(Nos, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jDesktopPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(Data, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jDesktopPane4Layout.setVerticalGroup(
            jDesktopPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Nos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDesktopPane4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDesktopPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap())
        );

        Pesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PesquisaKeyReleased(evt);
            }
        });

        jLabel4.setText("Cliente");

        id.setEditable(false);

        jLabel5.setText("ID");

        Table.setModel(new javax.swing.table.DefaultTableModel(
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
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        Table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(Table);

        jLabel6.setText("Equipamento");

        jLabel7.setText("Defeito:.");

        jLabel8.setText("Servico:.");

        jLabel9.setText("Tecnico:.");

        Valor.setText("0");

        jLabel10.setText("Valor Total:.");

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/basic/icons/Save.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/basic/icons/BussinessEdit.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/basic/icons/Search.png"))); // NOI18N
        btnPrint.setText("Busca");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/basic/icons/BussinessDelete.png"))); // NOI18N
        btnDeletar.setText("Deletar");
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        btnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/basic/icons/Printer.png"))); // NOI18N
        btnPrint1.setText("Imprimir");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jDesktopPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(Pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(equipamento))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(Defeito))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(Servico))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(Tecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(Valor, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeletar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrint)
                        .addGap(26, 26, 26)
                        .addComponent(btnPrint1)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jDesktopPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(equipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Defeito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Servico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(Valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnEditar)
                    .addComponent(btnDeletar)
                    .addComponent(btnPrint1)
                    .addComponent(btnPrint))
                .addGap(30, 30, 30))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDeletar, btnEditar, btnPrint1, btnSalvar});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(0, 0, 621, 425);
    }// </editor-fold>//GEN-END:initComponents

    private void PesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PesquisaKeyReleased
        pesquisar_cli();        // TODO add your handling code here:
    }//GEN-LAST:event_PesquisaKeyReleased

    private void TableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableKeyPressed
        set_campos();        // TODO add your handling code here:
    }//GEN-LAST:event_TableKeyPressed

    private void OrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrcamentoActionPerformed
        status = "orcamento";
    }//GEN-LAST:event_OrcamentoActionPerformed

    private void OrdemServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrdemServicoActionPerformed
        status = "Ordemservico";        // TODO add your handling code here:
    }//GEN-LAST:event_OrdemServicoActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Orcamento.setSelected(true);
        type = "orcamento";

    }//GEN-LAST:event_formInternalFrameOpened

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        emission_os();        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        set_campos();        // TODO add your handling code here:
    }//GEN-LAST:event_TableMouseClicked

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        pesquisar_os();        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        alter_os();        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
delete();        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeletarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CbStatus;
    private javax.swing.JFormattedTextField Data;
    private javax.swing.JTextField Defeito;
    private javax.swing.JTextField Nos;
    private javax.swing.JRadioButton Orcamento;
    private javax.swing.JRadioButton OrdemServico;
    private javax.swing.JTextField Pesquisa;
    private javax.swing.JTextField Servico;
    private javax.swing.JTable Table;
    private javax.swing.JTextField Tecnico;
    private javax.swing.JTextField Valor;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnPrint1;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField equipamento;
    private javax.swing.JTextField id;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane4;
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
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
