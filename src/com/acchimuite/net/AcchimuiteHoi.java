package com.acchimuite.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import net.arnx.jsonic.JSON;
import android.os.AsyncTask;

public class AcchimuiteHoi {
	private final String apiUri = "http://";
	private int sessionId;
	private JankenDto jsonResult;
	
	/**
	 * Ç¬Ç»Ç¨Ç…çsÇ≠ÇÊ
	 * @param userName
	 * @param userId
	 * @return visitorName
	 */
	public String sessionOpen(String userName, int userId) {
		StringBuffer uri = new StringBuffer(apiUri);
		uri.append("?command=").append("sessionOpen");
		uri.append("&userName=").append(userName);
		uri.append("&userId=").append(userId);
		JankenTask task = new JankenTask();
		do {
			task.execute(uri.toString());
		} while(!isResultOk());
		sessionId = jsonResult.getSessionId();
		return jsonResult.getVisitorName();
	}
	
	/**
	 * èoÇµÇΩéËÇëäéËÇ…ìnÇ∑ÇÊ
	 * @param shoot
	 * @return
	 */
	public int setShoot(int shoot) {
		StringBuffer uri = new StringBuffer(apiUri);
		uri.append("?command=").append("setShoot");
		uri.append("&sessionId=").append(sessionId);
		uri.append("&shoot=").append(shoot);
		JankenTask task = new JankenTask();
		do {
			task.execute(uri.toString());
		} while(!isResultOk());
		return pollResult();
	}
	
	/**
	 * ëäéËÇ™èoÇ∑ÇÃÇë“Ç¬ÇÊ
	 * @param direction
	 * @return
	 */
	public int pollResult() {
		StringBuffer uri = new StringBuffer(apiUri);
		uri.append("?command=").append("pollRequest");
		uri.append("&sessionId=").append(sessionId);
		JankenTask task = new JankenTask();
		do {
			task.execute(uri.toString());
		} while(!isResultOk());
		return jsonResult.getShotted();
	}
	
	/**
	 * APIÇÃåãâ Ç™OKÇ©
	 * @return
	 */
	private boolean isResultOk() {
		boolean flg = false;
		if (jsonResult != null) {
			if (jsonResult.getAccept() == "true") {
				flg = true;
			}
		}
		return flg;
	}
	
	/**
	 * ê⁄ë±Çó†Ç≈Ç∑ÇÈÇÊ
	 * @author ttymsd
	 *
	 */
	class JankenTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			try {
				URL url = new URL(params[0]);
				StringBuffer sb = new StringBuffer();
				BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream() ,"UTF-8"));
				String line;
				while((line = br.readLine()) != null){
					sb.append(line);
				}
				br.close();
				return sb.toString();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				jsonResult = JSON.decode(result, JankenDto.class);
			}
		}
	}
	
	class JankenDto {
		private String visitorName;
		private int sessionId;
		private Date expireTime;
		private String result;
		private String status;
		private int shotted;
		private String accept;
		
		public String getVisitorName() {
			return visitorName;
		}
		public void setVisitorName(String visitorName) {
			this.visitorName = visitorName;
		}
		public int getSessionId() {
			return sessionId;
		}
		public void setSessionId(int sessionId) {
			this.sessionId = sessionId;
		}
		public Date getExpireTime() {
			return expireTime;
		}
		public void setExpireTime(Date expireTime) {
			this.expireTime = expireTime;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public int getShotted() {
			return shotted;
		}
		public void setShotted(int shotted) {
			this.shotted = shotted;
		}
		public String getAccept() {
			return accept;
		}
		public void setAccept(String accept) {
			this.accept = accept;
		}
	}
}
