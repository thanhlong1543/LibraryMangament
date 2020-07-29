package kits.personal.thread;

public class ThreadCountDown extends Thread {
	@Override
	public synchronized void run() {
		for (int i = 4; i > 0; i--) {
			System.out.println("Waiting....." + i);
			
			try {
				Thread.sleep(1000);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		System.out.println("Success");
	}
}