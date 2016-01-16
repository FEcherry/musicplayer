package com.example.fe.musicplayer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Tool {
	private static ConnectivityManager connectivityManager;
	public static boolean isNetworkAvailable(Context context){
		//ConnectivityManager��Ҫ���������������صĲ��� ,�ǵ�ע��Ȩ��
		connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);//��ȡϵͳ�����ӷ���  
		if(connectivityManager==null){
			return false;//��ʾû������
		}else{
			//��ʾ������
			/*//�����ֻ�Ŀǰ��������ķ���Χ����connec.getActiveNetworkInfo()�᷵��null
			 * 1.NetworkInfo info = connec.getActiveNetworkInfo(); 
				2.NetworkInfo []allinfo=  connec.getAllNetworkInfo(); 
				5����������
				String typeName = info.getTypeName(); //cmwap/cmnet/wifi/uniwap/uninet  
				info.getTypeName();     // �Ժ��ַ�ʽ���� [WIFI]
				info.getState();        // ����״̬ [CONNECTED]
				info.isAvailable();     // �����Ƿ���� [true]
				info.isConnected();     // �����Ƿ��Ѿ����� [true]
				info.isConnectedOrConnecting(); // �����Ƿ��Ѿ����ӻ��������� [true]
				info.isFailover();      // �����Ƿ������� [false]
				info.isRoaming();       // �����Ƿ��������� [false]
			 */
			NetworkInfo[] info = connectivityManager.getAllNetworkInfo();//��ȡ������������  
			if(info!=null){
				//info��ʾ���� network��ʾ�������Ԫ�أ���ʾ������
				for(NetworkInfo network : info){
					//������������
					if(network.getState()==NetworkInfo.State.CONNECTED){
						return true;
					}
				}
			}
		}
		return false;
		
	}
}
