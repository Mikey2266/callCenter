import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Call extends Thread {
	private Semaphore em;
	private Semaphore sup;
	private Semaphore man;
	private char level;
	private String callName;

	Call(char level, String callName, Semaphore em, Semaphore sup, Semaphore man) {
		this.level = level;
		this.callName = callName;
		this.em = em;
		this.sup = sup;
		this.man = man;
	}

	@Override
	public void run() {
		long duration = ThreadLocalRandom.current().nextLong(1, 10);
//		long duration = 5;
		System.out.println(callName + " is waiting for an employee.");
		try {
			if(level == 'c') {
//				while(em.availablePermits() == 0);
				em.acquire();
				System.out.println(callName + " is connecting to an employee.");
				Thread.sleep(1000);
				em.release();
				System.out.println(callName + "'s level is c, call is transferred to a manager.");
				System.out.println(callName + " is waiting for a man.");
//				while(man.availablePermits() == 0);
				man.acquire();
				System.out.println(callName + " is connecting to a man.");
				Thread.sleep(TimeUnit.SECONDS.toMillis(duration));
				man.release();
				System.out.println(callName + " ends the phone call");
			}
			else if(level == 'b') {
//				while(em.availablePermits() == 0);
				em.acquire();
				System.out.println(callName + " is connecting to an employee.");
				Thread.sleep(1000);
				em.release();
				while(sup.availablePermits() == 0 && man.availablePermits() == 0);
				if(sup.availablePermits() > 0) {
					System.out.println(callName + "'s level is b, em is busy, so call is transferred to a sup.");
					System.out.println(callName + " is waiting for a sup.");
					sup.acquire();
					System.out.println(callName + " is connecting to a sup.");
					Thread.sleep(TimeUnit.SECONDS.toMillis(duration));
					sup.release();
					System.out.println(callName + " ends the phone call");
				} else {
					System.out.println(callName + "'s level is b, sup is busy, so call is transferred to a man.");
					System.out.println(callName + " is waiting for a man.");
					man.acquire();
					System.out.println(callName + " is connecting to a man.");
					Thread.sleep(TimeUnit.SECONDS.toMillis(duration));
					man.release();
					System.out.println(callName + " ends the phone call");
				}
			} else {
				while(em.availablePermits() == 0 && sup.availablePermits() == 0 && man.availablePermits() == 0);
				if(em.availablePermits() > 0) {
					em.acquire();
					System.out.println(callName + " is connecting to an employee.");
					Thread.sleep(TimeUnit.SECONDS.toMillis(duration));
					em.release();
					System.out.println(callName + " ends the phone call");
				}
				else if(sup.availablePermits() > 0) {
					System.out.println(callName + "'s level is a, em is busy, so call is transferred to a sup.");
					System.out.println(callName + " is waiting for a sup.");
					sup.acquire();
					System.out.println(callName + " is connecting to a sup.");
					Thread.sleep(TimeUnit.SECONDS.toMillis(duration));
					sup.release();
					System.out.println(callName + " ends the phone call");
				} else {
					System.out.println(callName + "'s level is a, em is busy, so call is transferred to a man.");
					System.out.println(callName + " is waiting for a man.");
					man.acquire();
					System.out.println(callName + " is connecting to a man.");
					Thread.sleep(TimeUnit.SECONDS.toMillis(duration));
					man.release();
					System.out.println(callName + " ends the phone call");
				}
			}
		} catch (InterruptedException e) {
//			System.out.println(e);
		}
	}

	public char getLevel() {
		return this.level;
	}
}
