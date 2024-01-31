package br.com.jogo.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.jogo.domain.NoArvoreBinaria;

public class SQLite {

	private static Connection connection;

	private static String QUERY_CREATE = "CREATE TABLE IF NOT EXISTS ARVORE( ID INTEGER, VALOR VARCHAR, PARENT INTEGER )";

	private static Connection connect() {

		if (connection == null) {
			try {
				connection = DriverManager.getConnection("jdbc:sqlite:banco.db");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		} else {

		}
		return connection;
	}

	public void criaNoArvoreBinaria(int id, String valor, int parent) {

		Connection connection = connect();
		try {
			Statement statement = connection.createStatement();
			statement.execute(QUERY_CREATE);

			statement.execute(
					"INSERT INTO ARVORE( ID, VALOR, PARENT) VALUES (" + id + ", '" + valor + "', " + parent + ")");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void alteraNoArvoreBinaria(int id, String valor) {

		Connection connection = connect();
		try {
			Statement statement = connection.createStatement();
			statement.execute(QUERY_CREATE);

			statement.execute("UPDATE ARVORE SET VALOR = '" + valor + "' WHERE ID = " + id + "");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<NoArvoreBinaria> buscaNoInicioArvoreBinaria() {

		ArrayList<NoArvoreBinaria> list = new ArrayList<NoArvoreBinaria>();
		NoArvoreBinaria noArvore = null;
		Connection connection = connect();
		PreparedStatement stmt;
		try {
			Statement statement = connection.createStatement();
			statement.execute(QUERY_CREATE);
			stmt = connection.prepareStatement("select * from ARVORE");

			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				Integer id = resultSet.getInt("ID");
				String valor = resultSet.getString("VALOR");
				Integer parent = resultSet.getInt("PARENT");

				noArvore = new NoArvoreBinaria();

				noArvore.elemento = id;
				noArvore.valor = valor;
				noArvore.parent = parent;

				list.add(noArvore);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;

	}

	public static void deleteArvore() {
		Connection connection = connect();
		try {
			Statement statement = connection.createStatement();
			statement.execute("DROP TABLE ARVORE");
			System.out.println(" Tabela excluida ");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void selectAll() {
		Connection connection = connect();
		try {
			// lendo os registros
			PreparedStatement stmt = connection.prepareStatement("select * from ARVORE");
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				Integer id = resultSet.getInt("ID");
				String valor = resultSet.getString("VALOR");
				Integer parent = resultSet.getInt("PARENT");

				System.out.println(id + " - " + valor + " - " + parent);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//connect();
		deleteArvore();
		//selectAll();
	}
}