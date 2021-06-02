<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.sql.*"%>

<%
	ResultSet rs = (ResultSet) request.getAttribute("Result");
	int listCnt = (int) request.getAttribute("ListCnt");
	String nowPage = (String) request.getAttribute("page");
	int maxPage = listCnt % 10 == 0 ? listCnt / 10 : listCnt / 10 + 1;
	int nowpage = Integer.parseInt(nowPage);
	int start = 0;
	int end = 0;
	String SerchName = request.getAttribute("SerchName") == null ? ""
			: (String) request.getAttribute("SerchName");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="List.css">
<title>一覧</title>
</head>
<body>
	<h2>住所録管理システム：住所録一覧</h2>

	<div class="navmenu">

		<div class="navleft">
			<!-- 登録画面へ遷移するためのボタン -->
			<form action="Add.jsp">
				<input type="submit" value="新規登録" class="btn addbtn">
			</form>
		</div>

		<div class="navright">
			<!-- 住所を検索し、絞り込むためのテキストボックス、ボタン -->
			<form action="./ListBL">
				<span>検索：</span>
				<input type="text" name="SerchName" class="serchtext"><br>
				<input type="submit" value="検索" class="serch">
			</form>
		</div>

	</div>

	<div class="paging">
		<ul>
			<li>
				<%
					if (nowpage == 1) {
				%> <a><%="<<"%></a> <%
 	} else {
 %> <a href="ListBL?page=1"><%="<<"%></a> <%
 	}
 %>
			</li>
			<li>
				<%
					if (nowpage == 1) {
				%> <a><%="<"%></a> <%
 	} else {
 %> <a href="ListBL?page=<%=nowpage - 1%>"><%="<"%></a> <%
 	}
 %>
			</li>
			<%
				if (maxPage <= 5) {
					start = 1;
					end = maxPage;
				} else if (nowpage == maxPage || nowpage == maxPage - 1) {
					start = maxPage - 4;
					end = maxPage;
				} else if (nowpage >= 3) {
					start = nowpage - 2;
					end = nowpage + 2;
				} else {
					start = 1;
					end = 5;
				}
				for (int i = start; i <= end; i++) {
			%>
			<li>
				<%
					if (nowpage == i) {
				%> <a><%=i%></a> <%
 	} else {
 %> <a href="ListBL?page=<%=i%>"><%=i%></a> <%
 	}
 %>
			</li>
			<%
				}
			%>
			<li>
				<%
					if (nowpage == maxPage) {
				%> <a><%=">"%></a> <%
 	} else {
 %> <a href="ListBL?page=<%=nowpage + 1%>"><%=">"%></a> <%
 	}
 %>
			</li>
			<li>
				<%
					if (nowpage == maxPage) {
				%> <a><%=">>"%></a> <%
 	} else {
 %> <a href="ListBL?page=<%=maxPage%>"><%=">>"%></a> <%
 	}
 %>
			</li>
		</ul>
	</div>


	<table class="list_table">
		<tr>
			<th>No.</th>
			<th>名前</th>
			<th>住所</th>
			<th>電話番号</th>
			<th>カテゴリ</th>
			<th colspan="2"></th>
		</tr>
		<%
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String tel = (rs.getString("tel").equals("")) ? rs.getString("tel")
						: new StringBuilder(rs.getString("tel")).insert(3, "-").insert(8, "-").toString();
				String cname = rs.getString("categoryname");
		%>

		<tr>
			<td class="tid"><%=id%></td>
			<td class="tname"><%=name%></td>
			<td class="tadres tadresth"><%=address%></td>
			<td class="ttel"><%=tel%></td>
			<td class="tcategory"><%=cname%></td>
			<td>
				<!--
					編集画面へ行くためのボタン
					id、name、address、tel、cnameを持ってEdit.jspへ遷移
				 -->
				<form action="Edit.jsp" name="<%="ed" + id%>">
					<input type="hidden" name="id" value="<%=id%>"> <input
						type="hidden" name="name" value="<%=name%>"> <input
						type="hidden" name="address" value="<%=address%>"> <input
						type="hidden" name="tel" value="<%=tel%>"> <input
						type="hidden" name="category" value="<%=cname%>"> <input
						type="submit" value="編集" class="btn tbtn">
				</form>
			</td>
			<td>
				<!--
					削除画面へ行くためのボタン
					idを持ってDelete.jspへ遷移
				 -->
				<form action="Delete.jsp" name="<%="del" + id%>" method="get">
					<input type="hidden" name="id" value="<%=id%>"> <input
						type="hidden" name="name" value="<%=name%>"> <input
						type="hidden" name="address" value="<%=address%>"> <input
						type="hidden" name="tel" value="<%=tel%>"> <input
						type="hidden" name="category" value="<%=cname%>"> <input
						type="submit" value="削除" class="btn tbtn">
				</form>
			</td>
		</tr>

		<%
			}
		%>
	</table>

	<div class="paging">
		<ul>
			<li>
				<%
					if (nowpage == 1) {
				%> <a><%="<<"%></a> <%
 	} else {
 %> <a href="ListBL?page=1"><%="<<"%></a> <%
 	}
 %>
			</li>
			<li>
				<%
					if (nowpage == 1) {
				%> <a><%="<"%></a> <%
 	} else {
 %> <a href="ListBL?page=<%=nowpage - 1%>"><%="<"%></a> <%
 	}
 %>
			</li>
			<%
				if (maxPage <= 5) {
					start = 1;
					end = maxPage;
				} else if (nowpage == maxPage || nowpage == maxPage - 1) {
					start = maxPage - 4;
					end = maxPage;
				} else if (nowpage >= 3) {
					start = nowpage - 2;
					end = nowpage + 2;
				} else {
					start = 1;
					end = 5;
				}
				for (int i = start; i <= end; i++) {
			%>
			<li>
				<%
					if (nowpage == i) {
				%> <a><%=i%></a> <%
 	} else {
 %> <a href="ListBL?page=<%=i%>"><%=i%></a> <%
 	}
 %>
			</li>
			<%
				}
			%>
			<li>
				<%
					if (nowpage == maxPage) {
				%> <a><%=">"%></a> <%
 	} else {
 %> <a href="ListBL?page=<%=nowpage + 1%>"><%=">"%></a> <%
 	}
 %>
			</li>
			<li>
				<%
					if (nowpage == maxPage) {
				%> <a><%=">>"%></a> <%
 	} else {
 %> <a href="ListBL?page=<%=maxPage%>"><%=">>"%></a> <%
 	}
 %>
			</li>
		</ul>
	</div>
	<div class="headleft">
		<!-- 登録画面へ遷移するためのボタン -->
		<form action="Add.jsp">
			<input type="submit" value="新規登録" class="btn addbtn">
		</form>
	</div>
</body>
</html>