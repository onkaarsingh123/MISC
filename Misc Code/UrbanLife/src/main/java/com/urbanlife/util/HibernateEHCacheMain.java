package com.urbanlife.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;

import com.urbanlife.model.Merchant;

//import com.journaldev.hibernate.util.HibernateUtil;

public class HibernateEHCacheMain {

	public static void main(String[] args) {

		System.out.println("Tmerchant Dir:"
				+ System.getProperty("java.io.tmpdir"));
		System.out.println("Temp Dir:" + System.getProperty("java.io.tmpdir"));

		 SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
	        Statistics stats = sessionFactory.getStatistics();
	        System.out.println("Stats enabled="+stats.isStatisticsEnabled());
	        stats.setStatisticsEnabled(true);
	        System.out.println("Stats enabled="+stats.isStatisticsEnabled());
		Session session = sessionFactory.openSession();
		Session otherSession = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Transaction otherTransaction = otherSession.beginTransaction();

		printStats(stats, 0);

		Merchant emp = (Merchant) session.load(Merchant.class, 21);
		printData(emp, stats, 1);

		emp = (Merchant) session.load(Merchant.class, 21);
		printData(emp, stats, 2);

		// clear first level cache, so that second level cache is used
		session.evict(emp);
		emp = (Merchant) session.load(Merchant.class, 21);
		printData(emp, stats, 3);

		emp = (Merchant) session.load(Merchant.class, 22);
		printData(emp, stats, 4);

		emp = (Merchant) otherSession.load(Merchant.class, 21);
		printData(emp, stats, 5);

		// Release resources
		transaction.commit();
		otherTransaction.commit();
		sessionFactory.close();
		

	}

	private static void printStats(Statistics stats, int i) {
		System.out.println("***** " + i + " *****");
		System.out.println("Fetch Count=" + stats.getEntityFetchCount());
		System.out.println("Second Level Hit Count="
				+ stats.getSecondLevelCacheHitCount());
		System.out.println("Second Level Miss Count="
				+ stats.getSecondLevelCacheMissCount());
		System.out.println("Second Level Put Count="
				+ stats.getSecondLevelCachePutCount());
	}

	private static void printData(Merchant merchant, Statistics stats, int count) {
		System.out.println(count + ":: EmailId=" + merchant.getEmailId()
				+ ", AccountId=" + merchant.getId());
		printStats(stats, count);
	}

}