package com.example.fe.musicplayer;

import java.io.IOException;

import android.R.integer;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore.Audio;
import android.widget.MediaController.MediaPlayerControl;

//when using a MediaPlayer from your main thread, you should call prepareAsync()
//rather than prepare(), and implement a MediaPlayer.OnPreparedListener in order
//to be notified when the preparation is complete and you can start playing. 
//ʹ��Service���ȥ�����ļ�����ע��
public class Myservice extends Service implements
		MediaPlayer.OnPreparedListener {
	private NetWorkAudioPlayer netWorkAudioPlayer = new NetWorkAudioPlayer(this);
	public static final int NOTIFICATION_ID = 1;
	public Myservice() {

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		// getStringExtra������������
		String songName = intent.getStringExtra("songName");
		Intent openIntent = new Intent(getApplicationContext(),
				Control.class);
		
		// FLAG_ACTIVITY_CLEAR_TOP:��һ�����򿪵Ļ�ᱻ�ս����ʵ��û��������ͬ�Ļ��ͬʱ�򿪡�
		openIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		/*
		 * PendingIntent���⼸��getActivity(Context, int, Intent, int),
		 * getActivities(Context, int, Intent[], int), getBroadcast(Context,
		 * int, Intent, int), and getService(Context, int, Intent, int); ������ʵ��
		 * ��������ת����һ��Ӧ�ó���
		 * PendingIntent��������ڴ��������������顣������֪ͨNotification��������תҳ�棬������������ת��
		 * Intentһ��������Activity��Sercvice��BroadcastReceiver֮�䴫�����ݣ�
		 * ��Pendingintent��һ������ Notification�ϣ��������Ϊ�ӳ�ִ�е�intent��
		 * PendingIntent�Ƕ�Intentһ����װ��
		 */
		// ���PendingIntent�Ѿ����ڣ�����������ֻ�滻����extra���ݡ�
		PendingIntent pendingIntent = PendingIntent.getActivity(
				getApplicationContext(), 0, openIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// Notification��һ������ȡϵͳ����
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// �ڶ�������������
		Notification notification = new Notification();
		// �������������¼�����
		notification.tickerText = "This is the tickerText.";// ������ʾ��״̬��������
		notification.icon = R.drawable.launcher;// ����ͼ��
		notification.when = System.currentTimeMillis();// ���÷���ʱ��
		//notification.defaults = Notification.DEFAULT_ALL;// ����Ĭ��������Ĭ���񶯣�Ĭ������ƣ��ǵ�ע���¼�
		/*
		 * ����flagλ FLAG_AUTO_CANCEL ��Ӧ�ó�����Զ������ FLAG_NO_CLEAR ��֪ͨ�����Զ������
		 * FLAG_ONGOING_EVENT ֪ͨ�������������� FLAG_INSISTENT �Ƿ�һֱ���У���������һֱ���ţ�ֱ���û���Ӧ
		 */
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		/*
		 * ���� 1: The context for your application / activity. 2�� The title that
		 * goes in the expanded entry. 3�� The text that goes in the expanded
		 * entry. 4�� The intent to launch when the user clicks the expanded
		 * notification. If this is an activity, it must include the
		 * FLAG_ACTIVITY_NEW_TASK flag, which requires that you take care of
		 * task management as described in the Tasks and Back Stack document.
		 */
		notification.setLatestEventInfo(getApplicationContext(), "MusicPlayer",
				"Now  Playing:" + songName, pendingIntent);
		// ���Ĳ�������Notification֪ͨ
		notificationManager.notify(NOTIFICATION_ID, notification);
		return super.onStartCommand(intent, flags, startId);

		// startForeground(NOTIFICATION_ID, notification);
		// //��ʼǰ̨����ͬʱ��notification��id��

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	@Override
	// Called when MediaPlayer is ready
	public void onPrepared(MediaPlayer player) {
		// TODO Auto-generated method stub
		player.start();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not yet implemented");
	}

}
