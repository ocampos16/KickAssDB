package kickassdb;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

public class ViewSchema extends javax.swing.JFrame 
{
    public ViewSchema() 
    {
        initComponents();
        
        /* Allow only one table to be selected at a time */
        schemaTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        /* Add scrollbar */
        //JScrollPane scroller = new JScrollPane(dataPanel);
        //this.getContentPane().add(scroller, BorderLayout.CENTER); 
        
        /* Populate the tree */
        populateTree();
        
        /* Set Schema name to JFrame window title */
        this.setTitle(MainWindow.getDefaultSchema().getName());
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
        schemaTree = new javax.swing.JTree();
        viewData = new javax.swing.JButton();
        dataScroll = new javax.swing.JScrollPane();
        dataPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Schema");
        schemaTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(schemaTree);

        viewData.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        viewData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/table_sheet.png"))); // NOI18N
        viewData.setText("View Data");
        viewData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dataPanelLayout = new javax.swing.GroupLayout(dataPanel);
        dataPanel.setLayout(dataPanelLayout);
        dataPanelLayout.setHorizontalGroup(
            dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 606, Short.MAX_VALUE)
        );
        dataPanelLayout.setVerticalGroup(
            dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 304, Short.MAX_VALUE)
        );

        dataScroll.setViewportView(dataPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(viewData, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dataScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(dataScroll)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(viewData)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void viewDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewDataActionPerformed
        
        /* Reset the Panel */
        dataPanel.removeAll();
        dataPanel.updateUI();
        
        /* Get Selected Table from the tree */
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) schemaTree.getLastSelectedPathComponent();
        
        
        /* Check that node is not root */
        if ( !selectedNode.isRoot() )
        {
            /* Find the table selected on the schema */
            Table selectedTable = new Table();
            for ( Table t : MainWindow.getDefaultSchema().getSchema() )
            {
                if ( selectedNode.toString().equals(t.getTable_name()))
                {
                    selectedTable = t;
                    break;
                }
            }
            
            /* Get the table's domain */
            String[] heading = new String[selectedTable.getTable_domain().size()];
            int i = 0;
            
            for ( Attribute a : selectedTable.getTable_domain() )
            {
                heading[i] = a.getAttribute_name();                
                i++;
            }
            
            /* Get the table's data */
            Object[][] data = new Object[selectedTable.getTable_tuples().size()][selectedTable.getTable_domain().size()];
            i = 0;
            int j = 0;
            
            for ( Tuple tuple : selectedTable.getTable_tuples() )
            {                
                for ( Value v : tuple.getTuple_values() )
                {
                    data[i][j] = v.getValue();
                    j++;
                }
                i++;
                j = 0;
            }
            
            /* Instantiate GUI Table Object */
            JTable gui_table = new JTable(data, heading);
            gui_table.setEnabled(false);
            
            dataPanel.setLayout(new BorderLayout());
            dataPanel.add(gui_table.getTableHeader(), BorderLayout.PAGE_START);
            dataPanel.add(gui_table, BorderLayout.CENTER);
            dataPanel.revalidate();
            dataPanel.repaint();
        }
        
        
    }//GEN-LAST:event_viewDataActionPerformed

    private void populateTree()
    {
        /* Get Leaf Icon path and create icon */
        java.net.URL leafURL = ViewSchema.class.getClassLoader().getResource("Images/table.png");        
        ImageIcon leafIcon = new ImageIcon(leafURL);
        
        /* Set Icon for Expanded nodes (root) */
        java.net.URL rootURL = ViewSchema.class.getClassLoader().getResource("Images/database.png");
        ImageIcon rootIcon = new ImageIcon(rootURL);
        
        if ( MainWindow.getDefaultSchema().getSchema().size() == 0 )
            leafIcon = rootIcon;
            
        /* Assign icons to the tree renderer */
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setLeafIcon(leafIcon);
        renderer.setOpenIcon(rootIcon);
        renderer.setClosedIcon(rootIcon);
        
        schemaTree.setCellRenderer(renderer);
        
        /* Get the tree model */
        DefaultTreeModel model = (DefaultTreeModel) schemaTree.getModel();
        
        /* Set the root (schema name) and assign it to the model */
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(MainWindow.getDefaultSchema().getName(), true);
        model.setRoot(root);
        
        /* Create node for each table in the schema */
        for ( Table t : MainWindow.getDefaultSchema().getSchema() )
        {
            /* Add a new node */
            root.add(new DefaultMutableTreeNode(t.getTable_name()));
        }
        
        /* Refresh the model */
        model.reload();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewSchema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewSchema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewSchema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewSchema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewSchema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel dataPanel;
    private javax.swing.JScrollPane dataScroll;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree schemaTree;
    private javax.swing.JButton viewData;
    // End of variables declaration//GEN-END:variables
}
