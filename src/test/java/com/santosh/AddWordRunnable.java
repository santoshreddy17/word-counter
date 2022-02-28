package com.santosh;

import com.santosh.counter.WordCounter;
import com.santosh.exception.WordCounterException;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class AddWordRunnable implements Runnable {
	private final CyclicBarrier barrier;
	private final WordCounter wordCounter;
	private final String word;

	public AddWordRunnable(CyclicBarrier barrier, WordCounter wordCounter, String word) {
		this.barrier = barrier;
		this.wordCounter = wordCounter;
		this.word = word;
	}

	@Override
	public void run() {
		try {
			wordCounter.addWord(word);
			try {
				barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		} catch (WordCounterException e) {
			e.printStackTrace();
		}
	}

}
