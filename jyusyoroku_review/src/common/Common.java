package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Common {
	private static final String URL = "jdbc:mysql://localhost:3306/ishibashi?serverTimezone=JST";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";

	public static String getErr(final String name, final String address, final String tel) {
		//エラー内容の宣言
		final String ERRMSG_NAME01 = "名前は全角20文字以内で入力してください";
		final String ERRMSG_NAME02 = "名前は必須項目です";
		final String ERRMSG_ADDRESS01 = "住所は全角40文字以内で入力してください";
		final String ERRMSG_ADDRESS02 = "住所は必須項目です";
		final String ERRMSG_TEL01 = "電話番号は「000-0000-0000」の形式で入力してください";
		String errmsg = "";
		//エラーがないかチェック
		errmsg += (stringDigits(name) > 40) ? ERRMSG_NAME01 + "<br>"
				: (stringDigits(name) == 0) ? ERRMSG_NAME02 + "<br>" : "";
		errmsg += (stringDigits(address) > 80) ? ERRMSG_ADDRESS01 + "<br>"
				: (stringDigits(address) == 0) ? ERRMSG_ADDRESS02 + "<br>" : "";
		errmsg += tel.matches("^\\d{3}-\\d{4}-\\d{4}$") ? "" : ERRMSG_TEL01 + "<br>";

		return errmsg;//チェックの結果を返す
	}

	//文字列の長さを計算
	public static int stringDigits(String s) {
		char[] chars = s.toCharArray();
		int digits = 0;
		for (int i = 0; i < chars.length; i++) {
			digits += (String.valueOf(chars[i]).getBytes().length < 2) ? 1 : 2;
		}
		return digits;
	}

	public ResultSet getCategoryAll() {
		//宣言
		String getQuery;
		try {
			//DBへ接続
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement stmt = connect.createStatement();
			getQuery = "select categoryid,categoryname from category order by categoryid asc;";
			ResultSet rs = stmt.executeQuery(getQuery);

			return rs;//カテゴリテーブルからすべての値を昇順に並べ替えた値を返す

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getCategoryName(String id) {
		//宣言
		Statement stmt;
		String getQuery;
		try {
			//DBへ接続
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			stmt = connect.createStatement();
			getQuery = "select categoryid,categoryname from category where categoryid=" + id + ";";
			ResultSet rs = stmt.executeQuery(getQuery);
			rs.next();
			//受け取った値をもとにカテゴリテーブルからカテゴリ名を返す
			return rs.getString("categoryname");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return "えらー";
	}
}
