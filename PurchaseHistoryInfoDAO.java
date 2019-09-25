package com.internousdev.olive.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.olive.dto.PurchaseHistoryInfoDTO;
import com.internousdev.olive.util.DBConnector;

public class PurchaseHistoryInfoDAO {

	public List<PurchaseHistoryInfoDTO> getPurchaseHistoryList(String userId){

		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();

		List<PurchaseHistoryInfoDTO> purchaseHistoryInfoDTOList = new ArrayList<PurchaseHistoryInfoDTO>();

		//商品購入履歴情報の項目一覧を表示する
		String sql="SELECT"
				+ " phi.id as id," /* ID */
				+ " phi.user_id as user_id," /* ユーザーID */
				+ " phi.product_count as product_count," /* 個数 */
				+ " pi.product_id as product_id," /* 商品ID */
				+ " pi.product_name as product_name," /* 商品名 */
				+ " pi.product_name_kana as product_name_kana," /* 商品名かな */
				+ " pi.product_description as product_description," /* 商品詳細 */
				+ " pi.category_id as category_id," /* カテゴリID */
				+ " pi.image_file_name as image_file_name," /* 画像ファイル名 */
				+ " pi.image_file_path as image_file_path," /* 画像ファイルパス */
				+ " pi.release_company," /* 発売会社名 */
				+ " pi.release_date," /* 発売年月日 */
				+ " phi.price as price," /* 値段 */
				+ " phi.price * phi.product_count as subtotal," /* 合計金額 */
				+ " phi.regist_date as regist_date," /* 登録日 */
				+ " di.family_name as family_name," /* 姓 */
				+ " di.first_name as first_name," /* 名 */
				+ " di.user_address as user_address" /* 住所 */
				+ " FROM purchase_history_info as phi"
				+ " LEFT JOIN product_info as pi"
				+ " ON phi.product_id = pi.product_id"
				+ " LEFT JOIN destination_info as di"
				+ " ON phi.destination_id = di.id"
				+ " WHERE phi.user_id = ?"
				+ " ORDER BY regist_date DESC";

		//ログインしているユーザーに紐づいた商品購入履歴情報の内容を表示する
		try{
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while(resultSet.next()){

				PurchaseHistoryInfoDTO purchaseHistoryInfoDTO = new PurchaseHistoryInfoDTO();

				purchaseHistoryInfoDTO.setId(resultSet.getInt("id"));
				purchaseHistoryInfoDTO.setUserId(resultSet.getString("user_id"));
				purchaseHistoryInfoDTO.setProductId(resultSet.getInt("product_id"));
				purchaseHistoryInfoDTO.setProductName(resultSet.getString("product_name"));
				purchaseHistoryInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				purchaseHistoryInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				purchaseHistoryInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				purchaseHistoryInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				purchaseHistoryInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				purchaseHistoryInfoDTO.setPrice(resultSet.getInt("price"));
				purchaseHistoryInfoDTO.setProductCount(resultSet.getInt("product_count"));
				purchaseHistoryInfoDTO.setSubtotal(resultSet.getInt("subtotal"));
				purchaseHistoryInfoDTO.setFamilyName(resultSet.getString("family_name"));
				purchaseHistoryInfoDTO.setFirstName(resultSet.getString("first_name"));
				purchaseHistoryInfoDTO.setUserAddress(resultSet.getString("user_address"));

				purchaseHistoryInfoDTOList.add(purchaseHistoryInfoDTO);

				}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				connection.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return purchaseHistoryInfoDTOList;
	}

	//商品購入履歴情報が存在する場合、登録日の降順に表示する
	public int regist(String userId,int productId,int productCount,int destinationId,int price){

		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();

		String sql = "insert into purchase_history_info"
						+"(user_id, product_id, product_count, price, destination_id, regist_date, update_date)"
						+"values(?,?,?,?,?,now(),now())";
		int count = 0;

		try{
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userId);
			preparedStatement.setInt(2, productId);
			preparedStatement.setInt(3, productCount);
			preparedStatement.setInt(4, price);
			preparedStatement.setInt(5, destinationId);
				count = preparedStatement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				connection.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return count;
	}

	//履歴削除ボタンが押下された場合、全ての購入履歴情報を削除する
public int deleteAll(String userId){
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		String sql = "delete from purchase_history_info where user_id=?";
		int count = 0;

		try{
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userId);
			count = preparedStatement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				connection.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return count;
	}
}
