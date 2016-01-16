package com.example.fe.musicplayer;

public interface IAudioPlayer {
	public boolean isPrepared();
	public boolean isPaused();
	public void playPause();
	public void playPrevious();
	public void playNext();
	public void playPrepared();
	public void play();
	
}
