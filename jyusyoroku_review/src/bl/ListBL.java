package bl;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListBL
 */
@WebServlet("/ListBL")
public class ListBL extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String URL = "jdbc:mysql://localhost:3306/ishibashi?serverTimezone=JST";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListBL() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ページングに関する値を取得
		String pagerq = request.getParameter("page");
		//住所検索に関する値を取得
		String SNrq = request.getParameter("SerchName");

		//宣言
		Statement stmt;
		ResultSet rs = null;
		int listCnt = 0;
		String SelectQuery = "select id,name,address,tel,categoryname from jyusyoroku join category on jyusyoroku.categoryid = category.categoryid where ";
		String CntQuery;
		String nowPage;
		String SerchName = "";
		int limitSta;

		try {
			//値の代入及びDBへ接続
			nowPage = pagerq == null ? "1" : pagerq;
			limitSta = (Integer.parseInt(nowPage) - 1) * 10;
			request.setCharacterEncoding("UTF-8");

			Class.forName("com.mysql.cj.jdbc.Driver");

			stmt = DriverManager.getConnection(URL, USERNAME, PASSWORD).createStatement();

			//住所検索があったかどうかを調べる
			if (SNrq == null) {
				//データが何件あるかを調べるSQL
				CntQuery = "select count(*) as cnt from jyusyoroku;";
				rs = stmt.executeQuery(CntQuery);
				rs.next();
				listCnt = rs.getInt("cnt");

				//接続したDBを閉じる
				rs.close();

				//住所検索がなかった場合全データを1ページに10件ずつ表示
				SelectQuery += "delete_flg = 0  order by id asc limit " + limitSta + ",10;";
			} else {

				//住所検索があった場合検索結果を1ページに10件ずつ表示
				SerchName = "'%" + SNrq + "%'";
				SelectQuery += "address like " + SerchName + " and delete_flg = 0 order by id asc  limit " + limitSta
						+ ",10;";

				//データが何件あるかを調べるSQL
				CntQuery = "select count(*) as cnt from jyusyoroku join category on jyusyoroku.categoryid = category.categoryid where address like "
						+ SerchName +
						" and delete_flg = 0 order by id asc;";
				rs = stmt.executeQuery(CntQuery);
				rs.next();
				listCnt = rs.getInt("cnt");

				//接続したDBを閉じる
				rs.close();
			}
			//DBへ接続
			rs = stmt.executeQuery(SelectQuery);

			//リクエストの追加
			request.setAttribute("ListCnt", listCnt);
			request.setAttribute("Result", rs);
			request.setAttribute("page", nowPage);
			request.setAttribute("SerchName", SNrq);

			//List.jspへ遷移
			getServletContext().getRequestDispatcher("/List.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
