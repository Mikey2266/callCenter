import java.util.concurrent.Semaphore;

public class CallCenter {
	// at CallCenter, initialize 3 employees(semaphore), 1 manager, 1 supervisor
	public static void main(String args[]) throws InterruptedException {
		// creating a Semaphore object Employee with number of permits 5
		Semaphore employee = new Semaphore(3);
		Semaphore supervisor = new Semaphore(1);
		Semaphore manager = new Semaphore(1);

		// creating calls that come at the same time
		Call call1 = new Call('a', "A", employee, supervisor, manager);
		Call call2 = new Call('c', "B", employee, supervisor, manager);
		Call call3 = new Call('c', "C", employee, supervisor, manager);
		Call call4 = new Call('b', "D", employee, supervisor, manager);
		Call call5 = new Call('a', "E", employee, supervisor, manager);
		Call call6 = new Call('a', "F", employee, supervisor, manager);

		// stating threads
		call1.start();
		call2.start();
		call3.start();
		call4.start();
		call5.start();
		call6.start();

		// waiting for threads
		call1.join();
		call2.join();
		call3.join();
		call4.join();
		call5.join();
		call6.join();

		// All threads will complete their execution
		System.out.println("All phone calls are ended");
	 }
}
	
