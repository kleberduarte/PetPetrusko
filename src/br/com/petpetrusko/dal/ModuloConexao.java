/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petpetrusko.dal;

import java.sql.*;

/**
 *
 * @author klebe
 */
public class ModuloConexao {

    //metodo responsavel por estabelecer a conexao com o banco
    public static Connection conector() {
        java.sql.Connection conexao = null;
        // a linha abaixo "chama" o driver
        String driver = "com.mysql.jdbc.Driver";
        // Armazenando informações referente ao banco
        String url = "jdbc:mysql://localhost:3306/dbpet";
        String user = "root";
        String password = "";
        // Estabelecendo a conexão com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            //a linha abaixo server de apoio para verificar se foi conectado corretamente ao banco de dados
            System.out.println(conexao);
            return conexao;
        } catch (Exception e) {
            //a linha abaixo server de apoio para esclarecer o erro
            System.out.println(e);
            return null;
        }

    }

}
