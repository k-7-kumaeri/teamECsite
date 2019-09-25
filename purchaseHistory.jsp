<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/style.css">
<link rel="stylesheet" href="./css/form.css">
<link rel="stylesheet" href="./css/error.css">
<link rel="stylesheet" href="./css/table.css">
<title>商品購入履歴画面</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<div id="contents">
		<h1>商品購入履歴画面</h1>
		<!-- もし「purchaseHistoryInfoDTOList」が空ではなく、0件を超える場合 -->
		<s:if
			test="purchaseHistoryInfoDTOList!=null && purchaseHistoryInfoDTOList.size()>0">
			<table class="horizontal-list">
				<thead>
					<tr>
						<!-- 商品購入履歴一覧画面表示 -->
						<th><s:label value="商品名" /></th>
						<th><s:label value="ふりがな" /></th>
						<th><s:label value="商品画像" /></th>
						<th><s:label value="発売会社名" /></th>
						<th><s:label value="発売年月日" /></th>
						<th><s:label value="値段" /></th>
						<th><s:label value="個数" /></th>
						<th><s:label value="合計金額" /></th>
						<th><s:label value="宛先名前" /></th>
						<th><s:label value="宛先住所" /></th>
					</tr>
				</thead>
				<tbody>
					<!-- 商品購入履歴情報の内容を表示 -->
					<s:iterator value="purchaseHistoryInfoDTOList">
						<tr>
							<!-- 商品名を表示 -->
							<td><s:property value="productName" /></td>

							<!-- 商品名かなを表示 -->
							<td><s:property value="productNameKana" /></td>

							<!-- 商品画像を表示 -->
							<td><img
								src='<s:property value="imageFilePath"/>/<s:property value="imageFileName"/>'
								width="50px" height="50px" /></td>

							<!-- 発売会社名を表示 -->
							<td><s:property value="releaseCompany" /></td>

							<!-- 発売年月日を表示 -->
							<td><s:property value="releaseDate" /></td>

							<!-- 値段を表示 -->
							<td><s:property value="price" />円</td>

							<!-- 個数を表示 -->
							<td><s:property value="productCount" />個</td>

							<!-- 合計金額を表示 -->
							<td><s:property value="subtotal" />円</td>

							<!-- 宛先名前を表示 -->
							<td><s:property value="familyName" /><span> </span> <s:property
									value="firstName" /></td>

							<!-- 宛先住所を表示 -->
							<td><s:property value="userAddress" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>

			<!-- 履歴削除ボタンを押下された場合、全ての商品購入履歴が削除される -->
			<s:form action="DeletePurchaseHistoryAction">
				<div class="submit_button">
					<s:submit class="button" value="履歴削除" />
				</div>
			</s:form>
		</s:if>

		<s:else>
			<!-- 商品購入履歴情報が1件も存在しない場合 -->
			<div class="info">商品購入履歴情報がありません。</div>
		</s:else>
	</div>
</body>
</html>