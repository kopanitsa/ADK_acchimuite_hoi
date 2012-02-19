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
	private final String apiUri = "http://janjackjanson.appspot.com/";
	private int sessionId;
	private JankenDto jsonResult;
	private boolean con;
	
	/**
	 * つなぎに行くよ
	 * @param userName
	 * @param userId
	 * @return visitorName
	 */
	public String openSession(String userName, int userId) {
		StringBuffer uri = new StringBuffer(apiUri);
		uri.append("?command=").append("sessionOpen");
		uri.append("&userName=").append(userName);
		uri.append("&userId=").append(userId);
		JankenTask task = new JankenTask();
		do {
			con = true;
			task.execute(uri.toString());
			sleep();
		} while(!isResultOk());
		sessionId = jsonResult.getSessionId();
		return jsonResult.getVisitorName();
	}
	
	/**
	 * 出した手を相手に渡すよ
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
			con = true;
			task.execute(uri.toString());
			sleep();
		} while(!isResultOk());
		return pollResult();
	}
	
	/**
	 * 相手が出すのを待つよ
	 * @param direction
	 * @return
	 */
	private int pollResult() {
		StringBuffer uri = new StringBuffer(apiUri);
		uri.append("?command=").append("pollRequest");
		uri.append("&sessionId=").append(sessionId);
		JankenTask task = new JankenTask();
		do {
			con = true;
			task.execute(uri.toString());
			sleep();
		} while(!isResultOk());
		return jsonResult.getShotted();
	}
	
	/**
	 * セッションを閉じる
	 * @return
	 */
	public boolean closeSession() {
		StringBuffer uri = new StringBuffer(apiUri);
		uri.append("?command=").append("closeSession");
		uri.append("&sessionId=").append(sessionId);
		JankenTask task = new JankenTask();
		task.execute(uri.toString());
		return jsonResult.getStatus().equals("true");
	}
	
	private void sleep() {
		while (con) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * APIの結果がOKか
	 * @return
	 */
	private boolean isResultOk() {
		boolean flg = false;
		if (jsonResult != null) {
			flg = jsonResult.isSuccess();
		}
		return flg;
	}
	
	/**
	 * 接続を裏でするよ
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
			con = false;
			if (result != null) {
				jsonResult = JSON.decode(result, JankenDto.class);
			}
		}
	}
	
	/**
	 * JSONの結果を保持するクラス
	 * @author ttymsd
	 *
	 */
	class JankenDto {
		private String visitorName;
		private int sessionId;
		private Date expireTime;
		private String result;
		private String status;
		private int shotted;
		private boolean success;
		
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
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
	}
}
