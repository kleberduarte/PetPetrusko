/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petpetrusko.telas;

import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author klebe
 */
public class Utilitarios {
    
    // metodo para gerar icone sistema
        public void InserirIcone(JFrame frm){
            try {
                frm.setIconImage(Toolkit.getDefaultToolkit().getImage("src/br/com/petpetrusko/icones/IconeSistemaDog.png"));
                
            } catch (Exception e) {
                
                 JOptionPane.showMessageDialog(null, e);
            }
        }

    
}
