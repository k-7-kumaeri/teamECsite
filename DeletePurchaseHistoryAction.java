package com.internousdev.olive.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.olive.dao.PurchaseHistoryInfoDAO;
import com.internousdev.olive.dto.PurchaseHistoryInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class DeletePurchaseHistoryAction extends ActionSupport implements SessionAware{

	private List<PurchaseHistoryInfoDTO> purchaseHistoryInfoDTOList;
	private Map<String, Object> session;

	public String execute() {
		//sessionがタイムアウトのチェックで、判定される
		if(!session.containsKey("tempUserId") && !session.containsKey("userId")) {
				return "sessionTimeout";
		}

		//未ログインの場合、強制的にセッションタイムアウトされる
		int logined = Integer.parseInt(session.get("logined").toString());
		if(logined != 1) {
				return "sessionTimeout";
		}

		//resultをERRORで初期化し、商品購入履歴情報を削除する(ログインしている状態)
		String result = ERROR;
		PurchaseHistoryInfoDAO purchaseHistoryInfoDAO = new PurchaseHistoryInfoDAO();
		int count = purchaseHistoryInfoDAO.deleteAll(session.get("userId").toString());
		if(count > 0) {
				purchaseHistoryInfoDTOList = purchaseHistoryInfoDAO.getPurchaseHistoryList(session.get("userId").toString());
				result=SUCCESS;
		}
		return result;
	}

	public List<PurchaseHistoryInfoDTO> getPurchaseHistoryInfoDTOList() {
		return purchaseHistoryInfoDTOList;
	}

	public void setPurchaseHistoryInfoDTOList(List<PurchaseHistoryInfoDTO> purchaseHistoryInfoDTOList){
			this.purchaseHistoryInfoDTOList = purchaseHistoryInfoDTOList;
	}

	public Map<String, Object> getSession() {
			return session;
	}

	public void setSession(Map<String, Object> session) {
			this.session = session;
	}
}
